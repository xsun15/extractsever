package com.cjbdi.intelJudge.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.intelJudge.configure.BeanConfigCenter;
import com.cjbdi.intelJudge.configure.SampleModel;
import com.cjbdi.intelJudge.utils.ExtractCallable;
import com.cjbdi.intelJudge.utils.Tools;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Similarity {
	public static List<String> assignTask(String content, String casecause, int topK) {
		Set<String> targetKeyword = gainKeywordExpandOrderTopK(content, casecause);
		List<SampleModel> sampleSet = BeanConfigCenter.bagwords.getKeywordCase().get(casecause);
		Map<String, Integer> retainWordWieght = BeanConfigCenter.bagwords.getRetainWordCase().get(casecause);
		int threadNum = BeanConfigCenter.bagwords.getThread();
		int batchSize = sampleSet.size() / threadNum;
		ExecutorService var22 = Executors.newFixedThreadPool(batchSize);
		int start = 0;
		List<Future> futureList = new ArrayList<>();
		for (int i=1; i<=threadNum; i++) {
			int end = start + batchSize;
			if (i==threadNum) end = sampleSet.size();
			futureList.add(var22.submit(new ExtractCallable(targetKeyword, sampleSet.subList(start, end), retainWordWieght)));
			start = end;
		}
		// 回收相似分析结果，并排序
		HashMap<String, Double> result = new HashMap<>();
		for (Future future : futureList) {
			try {
				result.putAll((Map<? extends String, ? extends Double>) future.get());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		LinkedHashMap<String, Double> sortResult = Tools.sortMapByValues(result);
		// 选择最相似的topK篇文书
		List<String> mostSimilarCase = new ArrayList<>();
		int count = 1;
		for (String filename : sortResult.keySet()) {

		}
		var22.shutdown();
		return mostSimilarCase;
	}

	// 内部测试用
	public static Map<String, Double> assignTask(List<String> content, String casecause, int topK, int min_penalty, int max_penalty, TransportClient client) {
		Set<String> targetKeyword = new HashSet<>(content);
		List<SampleModel> sampleSet = BeanConfigCenter.bagwords.getKeywordCase().get(casecause);
		Map<String, Integer> retainWordWieght = BeanConfigCenter.bagwords.getRetainWordCase().get(casecause);
		int threadNum = BeanConfigCenter.bagwords.getThread();
		int batchSize = sampleSet.size() / threadNum;
		ExecutorService var22 = Executors.newFixedThreadPool(batchSize);
		int start = 0;
		List<Future> futureList = new ArrayList<>();
		for (int i=1; i<=threadNum; i++) {
			int end = start + batchSize;
			if (i==threadNum) end = sampleSet.size();
			futureList.add(var22.submit(new ExtractCallable(targetKeyword, sampleSet.subList(start, end), retainWordWieght)));
			start = end;
		}
		// 回收相似分析结果，并排序
		HashMap<String, Double> result = new HashMap<>();
		for (Future future : futureList) {
			try {
				result.putAll((Map<? extends String, ? extends Double>) future.get());
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		LinkedHashMap<String, Double> sortResult = Tools.sortMapByValues(result);
		// 选择最相似的topK篇文书
		Map<String, Double> mostSimilarCase = new HashMap<>();
		int count = 0;
		for (String filename : sortResult.keySet()) {
			double similarity = sortResult.get(filename);
			GetResponse response = client.prepareGet(BeanConfigCenter.bagwords.getEsindex(), BeanConfigCenter.bagwords.getEstype(), filename).get();
			if (StringUtils.isNotEmpty(response.getSourceAsString())) {
				JSONObject responseJson = JSONObject.parseObject(response.getSourceAsString());
				int penalty = responseJson.getInteger("principalPenalty");
				if (penalty >= min_penalty && penalty <= max_penalty) {
					mostSimilarCase.put(filename, similarity);
					count++;
					if (count>=topK) break;
				}
			}
		}
		var22.shutdown();
		return mostSimilarCase;
	}

	public static Set<String> gainKeywordExpandOrderTopK(String content, String casecause) {
		Set<String> keywordExpand = new HashSet<>();
		if (StringUtils.isNotEmpty(content)) {
			List<String> wordList = cutWords(content, casecause);
			String retainWords = BeanConfigCenter.bagwords.getDetailConfigCase().get(casecause).get("retainWord").toString();
			List<String> keyword = gainKeywordTopK(wordList, casecause);
			for (String word : wordList) {
				if (keyword.contains(word) || retainWords.contains(word)) {
					keywordExpand.add(word);
				}
			}
		}
		return keywordExpand;
	}

	public static List<String> gainKeywordTopK(List<String> wordList, String casecause) {
		int topK = BeanConfigCenter.bagwords.getTopKCase().get(casecause);
		List<String> result = new ArrayList<>();
		LinkedHashMap<String, Double> keywordTfidf = gainKeywordTfidf(wordList, casecause);
		if (keywordTfidf!=null) {
			if (keywordTfidf != null) {
				int count = 0;
				for (String word : keywordTfidf.keySet()) {
					result.add(word);
					count ++;
					if (count>=topK) break;
				}
			}
		}
		return result;
	}

	public static LinkedHashMap<String, Double> gainKeywordTfidf(List<String> wordList, String casecause) {
		LinkedHashMap<String, Integer> tf = tf(wordList, BeanConfigCenter.bagwords.getBagwordCase().get(casecause));
		LinkedHashMap<String, Double> idf = BeanConfigCenter.bagwords.getIdfCase().get(casecause);
		double tf_idf = 0.0;
		Map<String, Double> map = new HashMap<>();
		for (String word : idf.keySet()) {
			if (tf.keySet().contains(word)) {
				tf_idf = idf.get(word) * tf.get(word);
				map.put(word, tf_idf);
			}
		}
		LinkedHashMap<String, Double> sortedMap = Tools.sortMapByValues(map);
		return  sortedMap;
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

	public static List<String> cutWords(String content, String casecause) {
		List<String> saveList = new ArrayList<>();
		if (StringUtils.isNotEmpty(content) && BeanConfigCenter.bagwords.getDetailConfigCase().keySet().contains(casecause)) {
			// 加载知识
			String removeSyntext = BeanConfigCenter.bagwords.getDetailConfigCase().get(casecause).get("removeSyntext").toString();
			String retainWord = BeanConfigCenter.bagwords.getDetailConfigCase().get(casecause).get("retainWord").toString();
			String removeWord = BeanConfigCenter.bagwords.getDetailConfigCase().get(casecause).get("removeWord").toString();
			List<String> stopword = BeanConfigCenter.bagwords.getStopwords();
			// 切词
			Segment segment = HanLP.newSegment().enableNameRecognize(true);
			List termList = segment.seg(content);
			Iterator var11 = termList.iterator();
			while (var11.hasNext()) {
				Term term = (Term) var11.next();
				String word = term.word.trim();
				String nature = term.nature.toString();
				if ((retainWord.contains(word) && word.length() >= 2 && !Tools.isStopChar(word)) || (!removeSyntext.contains(nature) && !Tools.isStopChar(word) && word.length() >= 2 && !stopword.contains(word))) {
					if (!saveList.contains(word) && !Tools.isMatch(word, removeWord))
						saveList.add(term.word);
				}
			}
		}
		return saveList;
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
