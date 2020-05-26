import com.sun.deploy.uitoolkit.ToolkitStore;
import org.yaml.snakeyaml.Yaml;
import utils.Tools;

import java.util.*;

public class FdXSample {
	public HashMap<String, HashMap<Integer, String>> features;

	public FdXSample() {
		Yaml yaml = new Yaml();
		features = yaml.load(FdXSample.class.getResourceAsStream("/fdx.yml"));
	}

	public static void main(String [] args) {
		FdXSample fdXSample = new FdXSample();
		String casecause = "robbery";
		Map<Integer, Map<String, Integer>> config = new HashMap<>();
		for (Integer layer : fdXSample.features.get(casecause).keySet()) {
			String range = fdXSample.features.get(casecause).get(layer);
			String [] rangeArray = range.split("-");
			Map<String, Integer> map = new HashMap<>();
			map.put("最小值", Integer.parseInt(rangeArray[0]));
			map.put("最大值", Integer.parseInt(rangeArray[1]));
			config.put(layer, map);
		}
		List<String> contentList = Tools.getFileContextList("/app/develop/extractsever/gensample/src/main/resources/robbery");
		String result = "";
		int count = 0;
		for (String line : contentList) {
			List<String> lineList = Arrays.asList(line.split("@@"));
			int penalty = Integer.parseInt(lineList.get(2));
			for (Integer layer : config.keySet()) {
				int min_val = config.get(layer).get("最小值");
				int max_val = config.get(layer).get("最大值");
				if (penalty>=min_val&&penalty<max_val) {
					result += layer + "," + lineList.get(1) + "\n";
				}
			}
			count++;
			if (count%1000==0) {
				System.out.println(count);
				Tools.saveAsFileWriter("/app/develop/extractsever/gensample/src/main/resources/result/robbery", result);
				result = "";
			}
		}
	}
}
