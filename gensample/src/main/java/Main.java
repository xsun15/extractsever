import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import utils.HttpRequest;
import utils.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String [] args) {
		JSONObject jsonObject = new JSONObject();
		List<String> casecause = new ArrayList<String>();
		String readpath = "/app/tmpdata/test/";
		String savepath = "/app/tmpdata/justice";
		ArrayList<String> listFileName = new ArrayList<String>();
		Tools.getAllFileName(readpath,listFileName);
		String content = "";
		int count = 1;
		String sum = "";
		for(String name:listFileName) {
			content = Tools.readFile(name);
			jsonObject.put("casecause", casecause);
			jsonObject.put("fullText", content);
			try {
				String result = HttpRequest.sendPost("http://123.124.130.22:8762/document/split", jsonObject);
				if (StringUtils.isNotEmpty(result)) {
					JSONObject resultJson = JSONObject.parseObject(result);
					if (resultJson != null) {
						if (resultJson.containsKey("justice") && resultJson.containsKey("opinion")) {
							String opinion = resultJson.getString("opinion").replaceAll("\n", "")  + "\n";
							int end = 20;
							if (opinion.indexOf("刑法") > opinion.indexOf("判决")) end = opinion.indexOf("判决");
							else if (opinion.indexOf("刑法") < opinion.indexOf("判决")) end = opinion.indexOf("刑法");
							if (end < opinion.length()) {
								opinion = opinion.substring(0, end);
								String one = resultJson.getString("justice").replaceAll("\n", "") + opinion;
								sum += name.split("test/")[1] + "@@" + one + "\n";
							}
							if (count %1000 == 0) {
								saveAsFileWriter(savepath, sum);
								sum = "";
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(count);
			count++;
		}
	}

	private static void saveAsFileWriter(String savepath, String content) {
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
}
