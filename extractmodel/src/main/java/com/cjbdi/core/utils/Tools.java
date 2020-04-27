package com.cjbdi.core.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
}
