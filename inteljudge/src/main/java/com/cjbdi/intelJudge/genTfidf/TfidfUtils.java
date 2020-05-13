package com.cjbdi.intelJudge.genTfidf;

import com.cjbdi.intelJudge.utils.Tools;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 *
 * @author X.H.Yang
 * 计算文档的TF值 词频(TF)=某个词在文章中出现的次数/文章的总词数
 * 计算文档的IDF值 逆文档频率(IDF)=log(文档总数/包含改词的文档数)
 * 计算TF-IDF值：TF-IDF = 词频(TF)*逆文档频率(IDF)
 */
public class TfidfUtils {

	public static void main(String [] args) {
		List<String> contentList = Tools.getFileContext("/app/develop/extractsever/inteljudge/src/main/resources/test");
		List<Map.Entry<String, Double>> sortedMap = gainTfidf(contentList, 20000);
		for (Map.Entry<String, Double> map : sortedMap) {
			Tools.saveAsFileWriter("./stopword", map.toString() + "\n");
		}
		System.out.println(sortedMap);
		int a = 1;
	}

	public static List<List<String>> gainKeywordTopK(List<String> targetList, List<String> textList, int max_nb_words, int topK) {
		List<List<String>> result = new ArrayList<>();
		List<LinkedHashMap<String, Double>> keywordTfidf = gainKeywordTfidf(targetList, textList, max_nb_words);
		if (keywordTfidf!=null) {
			for (LinkedHashMap<String, Double> keywordMap : keywordTfidf) {
				List<String> keywordList = new ArrayList<>();
				if (keywordMap != null) {
					int count = 0;
					for (String word : keywordMap.keySet()) {
						keywordList.add(word);
						count ++;
						if (count>=topK) break;
					}
				}
				result.add(keywordList);
			}
		}
		return result;
	}

	public static List<LinkedHashMap<String, Double>> gainKeywordTfidf(List<String> targetList, List<String> textList, int max_nb_words) {
		List<String> bagwords = genBagWords(textList, max_nb_words);
		LinkedHashMap<String, Double> idf = idf(textList, bagwords);
		List<LinkedHashMap<String, Double>> result = new ArrayList<>();
		for (String target : targetList) {
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
			result.add(sortedMap);
		}
		return result;
	}

	public static List<Map.Entry<String, Double>> gainTfidf(List<String> textList, int max_nb_words) {
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

	public static List<String> genBagWords(List<String> textList, int max_nb_words) {
		List<String> bagwords = new ArrayList<>();
		for (String text : textList) {
			List<String> words = Arrays.asList(text.split(" "));
			for (String word : words){
				if (!bagwords.contains(word) && word.length()>=2 && !Tools.isStopChar(word)) bagwords.add(word);
				if (bagwords.size() >= max_nb_words) {
					return bagwords;
				}
			}
		}
		return bagwords;
	}

	public static LinkedHashMap<String, Double> wordFreq(List<String> textList, List<String> bagwords) {
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		if (bagwords!=null&&textList!=null) {
			double nd = textList.size();
			for (String word : bagwords) {
				double count = 0;
				if (map.keySet().contains(word)) count = map.get(word);
				for (String text : textList) {
					if (text.contains(word)) {
						count += 1;
					}
				}
				map.put(word, count);
			}
		}
		return map;
	}

	public static LinkedHashMap<String, Double> idf(List<String> textList, List<String> bagwords) {
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		if (bagwords!=null&&textList!=null) {
			double nd = textList.size();
			for (String word : bagwords) {
				double count = 0;
				if (map.keySet().contains(word)) count = map.get(word);
				for (String text : textList) {
					if (text.contains(word)) {
						count += 1;
					}
				}
				double idfword = Math.log((1+nd)/(1+count)) + 1;
				map.put(word, idfword);
			}
		}
		return map;
	}

	public static LinkedHashMap<String, Integer> tf(String text, List<String> bagwords) {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		if (StringUtils.isNotEmpty(text)&&bagwords!=null) {
			Set<String> words = new HashSet<>(Arrays.asList(text.split(" ")));
			words.retainAll(bagwords);
			if (words!=null) {
				for (String word : words) {
					int count = getCount(word, text);
					map.put(word, count);
				}
			}
		}
		return map;
	}

	public static int getCount(String target,String str){
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