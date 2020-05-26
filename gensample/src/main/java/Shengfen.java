import com.alibaba.fastjson.JSONObject;
import utils.Tools;

import java.awt.image.ImageProducer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Shengfen {
	public static void main(String [] args) {
		List<String> contentList = Tools.getFileContextList("/app/develop/extractsever/gensample/src/main/resources/administrative_code.txt");
		Map<String, String> province = new HashMap<>();
		// 查找省份
		for (String content : contentList) {
			String [] wordList = content.split("\t");
			if (wordList[0].contains("0000")) province.put(wordList[1], wordList[0]);
		}
		// 找到省份相应的下级地区
		Map<String, List<String>> provinceSub = new HashMap<>();
		Map<String, Map<String, String>> provinceSub2 = new HashMap<>();
		for (String name : province.keySet()) {
			String code = province.get(name);
			List<String> tmp = new ArrayList<>();
			Map<String, String> tmp1 = new HashMap<>();
			for (String content : contentList) {
				String [] wordList = content.split("\t");
				if (wordList[0].substring(0, 3).equals(code.substring(0,3)) && wordList[0].substring(4,6).equals("00") && !wordList[0].equals(code)) {
					tmp.add(wordList[1]);
				} else if (wordList[0].substring(0, 2).equals(code.substring(0,2)) && !wordList[0].equals(code)){
					int count = 0;
					for (String tmp2 : contentList) {
						if (tmp2.contains(wordList[1])) {
							count++;
						}
					}
					if (count > 1) {
						tmp1.put(wordList[1], "1");
					} else {
						tmp1.put(wordList[1], "0");
					}
				}
			}
			provinceSub.put(name, tmp);
			provinceSub2.put(name, tmp1);
		}
		// 生成配置文件
		String filename = "/app/develop/extractsever/gensample/src/main/resources/province.yml";
		for (String proName : province.keySet()) {
			List<String> cityList = provinceSub.get(proName);
			Map<String, String> countyMap = provinceSub2.get(proName);
			Tools.saveAsFileWriter(filename, proName + ":\n");
			Tools.saveAsFileWriter(filename, "    province:\n");
			Tools.saveAsFileWriter(filename, "      - \"" + proName + "\"\n");
			Tools.saveAsFileWriter(filename, "    city:\n");
			for (String city : cityList) {
				Tools.saveAsFileWriter(filename, "      - " + "\"" + city + "\"\n");
			}
			Tools.saveAsFileWriter(filename, "    county:\n");
			for (String county : countyMap.keySet()) {
				Tools.saveAsFileWriter(filename, "        - " + "\"" + county + "\"\n");
			}
		}
	}
}
