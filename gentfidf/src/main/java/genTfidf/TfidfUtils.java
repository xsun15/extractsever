package genTfidf;

import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

/**
 *
 * @author X.R.SUN
 * 计算文档的TF值 词频(TF)=某个词在文章中出现的次数/文章的总词数
 * 计算文档的IDF值 逆文档频率(IDF)=log(文档总数/包含改词的文档数)
 * 计算TF-IDF值：TF-IDF = 词频(TF)*逆文档频率(IDF)
 */
public class TfidfUtils {

	public static void main(String [] args) {
		Yaml yaml = new Yaml();
		HashMap<String, HashMap<String, Object>>  hashMap = yaml.load(TfidfUtils.class.getResourceAsStream("/configure.yml"));
		String stopPath = hashMap.get("train").get("stopword").toString();
		String casecause = "robbery";
		String rootpath = Class.class.getClass().getResource("/").getPath();
		JSONObject caseConfig = new JSONObject((Map)hashMap.get("train").get(casecause));
		String rawdata = caseConfig.getString("rawdata");
		String saveKeywordPath = caseConfig.getString("keyword");
		String configPath = caseConfig.getString("config");
		HashMap<String, HashMap<String, Object>>  config = yaml.load(TfidfUtils.class.getResourceAsStream(configPath));
		Map<String, String> contentList = Tools.getFileContext(rootpath+rawdata);
		List<String> stopwords = Tools.getFileContextList(rootpath+stopPath);
		Map<String, List<String>> wordList = cutWords(contentList, stopwords, config.get(casecause));
		Map<String, List<String>> keywords = gainKeywordTopK(wordList, wordList, 20000, 30, caseConfig);
		Map<String, List<String>> expandKeywords = gainKeywordExpandOrderTopK(keywords, wordList, config.get(casecause));
		Tools.saveAsFileWriter(rootpath+saveKeywordPath, expandKeywords);
	}

	public static Map<String, List<String>> gainKeywordExpandOrderTopK(Map<String, List<String>> keywords, Map<String, List<String>> wordList, Map<String, Object> config) {
		Map<String, List<String>> wordListExpand = new HashMap<>();
		if (keywords!=null&&wordList!=null&&keywords.size()==wordList.size()) {
			String retainWords = "";
			if (config!=null) retainWords = config.get("retainWord").toString();
			for (String id : wordList.keySet()) {
				List<String> oneList = wordList.get(id);
				List<String> oneSave = new ArrayList<>();
				for (String word : oneList) {
					if (keywords.get(id).contains(word) || retainWords.contains(word)) {
						oneSave.add(word);
					}
				}
				if (oneSave.size()>=15) wordListExpand.put(id, oneSave);
			}
		}
		return wordListExpand;
	}

	public static Map<String, List<String>> cutWords(Map<String, String> contentMap, List<String> stopwords, Map<String, Object> config) {
		Segment segment = HanLP.newSegment().enableNameRecognize(true);
		Map<String, List<String>> splitContent = new HashMap<>();
		String removeSyntext = config.get("removeSyntext").toString();
		String retainWord = config.get("retainWord").toString();
		String removeWord = config.get("removeWord").toString();
		int count = 1;
		for (String id : contentMap.keySet()) {
			String line = contentMap.get(id);
			List termList = segment.seg(line);
			Iterator var11 = termList.iterator();
			List<String> saveList = new ArrayList<>();
			while(var11.hasNext()) {
				Term term = (Term)var11.next();
				String word = term.word.trim();
				String nature = term.nature.toString();
				if((retainWord.contains(word) && word.length()>=2 && !Tools.isStopChar(word)) || (!removeSyntext.contains(nature) && !Tools.isStopChar(word) && word.length()>=2 && !stopwords.contains(word))) {
					if (!saveList.contains(word) && !Tools.isMatch(word,removeWord))
						saveList.add(term.word);
				}
			}
			splitContent.put(id, saveList);
			if (count%1000==0) System.out.println(1.0*count/contentMap.size()*100+"%");
			count++;
		}
		return splitContent;
	}

	public static Map<String, List<String>> gainKeywordTopK(Map<String, List<String>> targetList, Map<String, List<String>> textList, int max_nb_words, int topK,
	         JSONObject caseConfig) {
		Map<String, List<String>> result = new HashMap<>();
		Map<String, LinkedHashMap<String, Double>> keywordTfidf = gainKeywordTfidf(targetList, textList, max_nb_words, caseConfig.getString("bagword"), caseConfig.getString("idf"));
		if (keywordTfidf!=null) {
			for (String id : keywordTfidf.keySet()) {
				LinkedHashMap<String, Double> keywordMap = keywordTfidf.get(id);
				List<String> keywordList = new ArrayList<>();
				if (keywordMap != null) {
					int count = 0;
					for (String word : keywordMap.keySet()) {
						keywordList.add(word);
						count ++;
						if (count>=topK) break;
					}
				}
				result.put(id, keywordList);
			}
		}
		return result;
	}

	public static Map<String, LinkedHashMap<String, Double>> gainKeywordTfidf(Map<String, List<String>> targetList, Map<String, List<String>> textList,
			int max_nb_words, String bagword_savepath, String idf_savepath) {
		List<String> bagwords = genBagWords(textList, max_nb_words);
		String rootpath = Class.class.getClass().getResource("/").getPath();
		Tools.saveAsFileWriter(rootpath + bagword_savepath, bagwords);
		LinkedHashMap<String, Double> idf = idf(textList, bagwords);
		Tools.saveAsFileWriter(rootpath + idf_savepath, idf);
		Map<String, LinkedHashMap<String, Double>> result = new HashMap<>();
		for (String id : targetList.keySet()) {
			List<String> target = targetList.get(id);
			LinkedHashMap<String, Integer> tf = tf(target, bagwords);
			double tf_idf = 0.0;
			Map<String, Double> map = new HashMap<>();
			for (String word : idf.keySet()) {
				if (tf.keySet().contains(word)) {
					tf_idf = idf.get(word) * tf.get(word);
					map.put(word, tf_idf);
				}
			}
			LinkedHashMap<String, Double> sortedMap = Tools.sortMapByValues(map);
			result.put(id, sortedMap);
		}
		return result;
	}

	public static List<Map.Entry<String, Double>> gainTfidf(Map<String, List<String>> textList, int max_nb_words) {
		List<String> bagwords = genBagWords(textList, max_nb_words);
		HashMap<String, Double> idf = wordFreq(textList, bagwords);
		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(idf.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
			@Override
			public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		return list;
	}

	public static List<Double> L2Regular(List<Double> matrix) {
		if (matrix!=null) {
			double sum = 0.0;
			for (Double value : matrix) {
				sum += value*value;
			}
			if (sum > 1e-5) {
				int count = 0;
				for (Double value : matrix) {
					matrix.set(count, value/Math.sqrt(sum));
					count++;
				}
			}
		}
		return matrix;
	}

	public static List<String> genBagWords(Map<String, List<String>> textList, int max_nb_words) {
		List<String> bagwords = new ArrayList<>();
		for (String id: textList.keySet()) {
			for (String word : textList.get(id)){
				if (!bagwords.contains(word)) bagwords.add(word);
				if (bagwords.size() >= max_nb_words) {
					return bagwords;
				}
			}
		}
		return bagwords;
	}

	public static LinkedHashMap<String, Double> wordFreq(Map<String, List<String>> textList, List<String> bagwords) {
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		if (bagwords!=null&&textList!=null) {
			double nd = textList.size();
			for (String word : bagwords) {
				double count = 0;
				if (map.keySet().contains(word)) count = map.get(word);
				for (String id : textList.keySet()) {
					if (textList.get(id).contains(word)) {
						count += 1;
					}
				}
				map.put(word, count);
			}
		}
		return map;
	}

	public static LinkedHashMap<String, Double> idf(Map<String, List<String>> textList, List<String> bagwords) {
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		if (bagwords!=null&&textList!=null) {
			double nd = textList.size();
			int countNu = 1;
			for (String word : bagwords) {
				double count = 0;
				if (map.keySet().contains(word)) count = map.get(word);
				for (String id : textList.keySet()) {
					if (textList.get(id).contains(word)) {
						count += 1;
					}
				}
				double idfword = Math.log((1+nd)/(1+count)) + 1;
				map.put(word, idfword);
				if (countNu%100==0) System.out.println(1.0*countNu/bagwords.size()*100+"%");
				countNu++;
			}
		}
		return map;
	}

	public static LinkedHashMap<String, Integer> tf(List<String> text, List<String> bagwords) {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		if (text!=null&&bagwords!=null) {
			Set<String> words = new HashSet<>(text);
			words.retainAll(bagwords);
			if (words!=null) {
				for (String word : words) {
					int count = getCount(word, text.toString());
					map.put(word, count);
				}
			}
		}
		return map;
	}

	public static int getCount(String target, String str){
		int count=0;	// 定义计数器
		int index=0;	// 定义角标

		while((index=str.indexOf(target))!=-1){
			// sp("str"+str);
			str=str.substring(index+target.length());	// 截取未被查找到目标字符串的子串
			count++;
		}
		return count;
	}
}