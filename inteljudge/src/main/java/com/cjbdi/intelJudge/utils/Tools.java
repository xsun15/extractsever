package com.cjbdi.intelJudge.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tools {
	public static <K extends Comparable, V extends Comparable> LinkedHashMap<K, V> sortMapByValues(Map<K, V> aMap) {
		LinkedHashMap<K, V> finalOut = new LinkedHashMap<>();
		aMap.entrySet()
				.stream()
				.sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
				.collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
		return finalOut;
	}

	public static List<String> getFileContext(String path)  {
		List<String> list =new ArrayList<String>();
		try {
			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>2) {
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void saveAsFileWriter(String savepath, String content) {
		FileWriter fwriter = null;
		try {
			// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
			fwriter = new FileWriter(savepath, true);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static boolean isStopChar(String string) {
		boolean flag = false;
		Pattern p = Pattern.compile("[a-zA-z0-9]");
		if(p.matcher(string).find()) {
			flag = true;
		}
		return flag;
	}
}
