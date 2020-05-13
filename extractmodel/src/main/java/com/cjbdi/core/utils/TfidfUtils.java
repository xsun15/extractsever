package com.cjbdi.core.utils;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 *
 * @author X.H.Yang
 * 计算文档的TF值 词频(TF)=某个词在文章中出现的次数/文章的总词数
 * 计算文档的IDF值 逆文档频率(IDF)=log(文档总数/包含改词的文档数)
 * 计算TF-IDF值：TF-IDF = 词频(TF)*逆文档频率(IDF)
 */
public class TfidfUtils {

	public static List<Double> run(String target, List<String> bagwords, LinkedHashMap<String, Double> idf) {
		LinkedHashMap<String, Integer> tf = tf(target, bagwords);
		List<Double> matrix = new ArrayList<>();
		for (String word : idf.keySet()) {
			double tf_idf= 0.0;
			if (tf.keySet().contains(word)) tf_idf = idf.get(word) * tf.get(word);
			matrix.add(tf_idf);
		}
		L2Regular(matrix);
		return matrix;
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