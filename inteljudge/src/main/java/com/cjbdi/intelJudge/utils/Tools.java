package com.cjbdi.intelJudge.utils;

import com.cjbdi.intelJudge.configure.SampleModel;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tools {

	public static boolean isMatch(String text, List<String> ruleList) {
		if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
			Iterator var2 = ruleList.iterator();
			while(var2.hasNext()) {
				String rule = (String)var2.next();
				Pattern pattern = Pattern.compile(rule);
				Matcher matcher = pattern.matcher(text);
				if(matcher.find()) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isMatch(String text, String rule) {
		if(text != null && !text.isEmpty() && StringUtils.isNotEmpty(rule)) {
			Pattern pattern = Pattern.compile(rule);
			Matcher matcher = pattern.matcher(text);
			if(matcher.find()) {
				return true;
			}
		}
		return false;
	}

	public static <K extends Comparable, V extends Comparable> LinkedHashMap<K, V> sortMapByValues(Map<K, V> aMap) {
		LinkedHashMap<K, V> finalOut = new LinkedHashMap<>();
		aMap.entrySet()
				.stream()
				.sorted((p1, p2) -> p2.getValue().compareTo(p1.getValue()))
				.collect(Collectors.toList()).forEach(ele -> finalOut.put(ele.getKey(), ele.getValue()));
		return finalOut;
	}

	public static Map<String, String> getFileContext(String path)  {
		Map<String, String> map =new HashMap<>();
		try {
			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>2) {
					str = str.trim().replaceAll("\n", "");
					if (str!=null&&str.contains("@@")) {
						List<String> strList = Arrays.asList(str.split("@@"));
						map.put(strList.get(0), StringUtils.replace(strList.subList(1,strList.size()).toString(), "[|]", ""));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static List<String> getFileContextList(String path)  {
		List<String> list =new ArrayList<>();
		try {
			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>=2) {
					str = str.trim().replaceAll("\n", "");
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static LinkedHashMap<String, Double> getFileContextMap(String path)  {
		LinkedHashMap<String, Double> map =new LinkedHashMap<>();
		try {
			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>=2) {
					str = str.trim().replaceAll("\n", "");
					List<String> strList = Arrays.asList(str.split(" "));
					map.put(strList.get(0), Double.parseDouble(strList.get(1)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static List<SampleModel> getFileContextSampleModel(String path)  {
		List<SampleModel> list =new ArrayList<>();
		try {
			FileReader fileReader =new FileReader(path);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
			String str=null;
			while((str=bufferedReader.readLine())!=null) {
				if(str.trim().length()>=2) {
					str = str.trim().replaceAll("\n", "");
					List<String> strList = Arrays.asList(str.split("\t"));
					String id = strList.get(0);
					List<String> contentList = Arrays.asList(strList.get(1).split(" "));
					SampleModel sampleModel = new SampleModel(contentList, id);
					list.add(sampleModel);
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

	public static void saveAsFileWriter(String savepath, Map<String, List<String>> contentList) {
		FileWriter fwriter = null;
		if (contentList!=null) {
			try {
				// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
				fwriter = new FileWriter(savepath);
				for (String id : contentList.keySet()) {
					fwriter.write(id + "\t");
					for (String word : contentList.get(id)) {
						fwriter.write(word+" ");
					}
					fwriter.write("\n");
				}
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
	}

	public static void saveAsFileWriter(String savepath, List<String> contentList) {
		FileWriter fwriter = null;
		if (contentList!=null) {
			try {
				// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
				fwriter = new FileWriter(savepath);
				for (String word : contentList) {
					fwriter.write(word+" ");
				}
				fwriter.write("\n");
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
	}

	public static void saveAsFileWriter(String savepath, LinkedHashMap<String, Double> contentList) {
		FileWriter fwriter = null;
		if (contentList!=null) {
			try {
				// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
				fwriter = new FileWriter(savepath);
				for (String word : contentList.keySet()) {
					fwriter.write(word+" ");
					fwriter.write(contentList.get(word).toString() + "\n");
				}
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
	}

	public static boolean isStopChar(String string) {
		boolean flag = false;
		Pattern p = Pattern.compile("[a-zA-z0-9]");
		if(p.matcher(string).find()) {
			flag = true;
		}
		return flag;
	}
	public static String clean(String content) {
		content = content.replaceAll("＊", "某");
		content = content.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：:*×\\n《》（）Oo〇]+","");
		String[] contentList = content.split("\n");
		String result = "";
		for (String line : contentList) {
			if (! "".equals(line.trim().replaceAll("\\s+", ""))) {
				result += line + "\n";
			}
		}
		return result.trim();
	}

}
