package com.cjbdi.core.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Tools {
	public static List<String> getFileContext(String path) throws Exception {
		FileReader fileReader =new FileReader(path);
		BufferedReader bufferedReader =new BufferedReader(fileReader);
		List<String> list =new ArrayList<String>();
		String str=null;
		while((str=bufferedReader.readLine())!=null) {
			if(str.trim().length()>2) {
				list.add(str);
			}
		}
		return list;
	}

	public static boolean isStopChar(String string) {
		boolean flag = false;
		Pattern p = Pattern.compile("[a-zA-z0-9]");
		if(p.matcher(string).find()) {
			flag = true;
		}
		return flag;
	}

	public static String list2str(List<Double> list) {
		String result = "";
		if (list!=null) {
			result =  list.toString().replaceAll("(?:\\[|null|\\]| +)", "");
		}
		return result;
	}
}
