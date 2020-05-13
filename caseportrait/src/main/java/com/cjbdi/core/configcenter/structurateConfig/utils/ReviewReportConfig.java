package com.cjbdi.core.configcenter.structurateConfig.utils;

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ReviewReportConfig {
	private HashMap<Integer, HashMap<String, Object>> features = new HashMap<>();

	public ReviewReportConfig() {
		Yaml yaml = new Yaml();
		HashMap<String, HashMap<String, Object>>  hashMap = yaml.load(ReviewReportConfig.class.getResourceAsStream("/split/reviewreport.yml"));
		if (hashMap!=null) {
			for (String feature : hashMap.keySet()) {
				int order = Integer.parseInt(hashMap.get(feature).get("order").toString());
				features.put(order, hashMap.get(feature));
			}
		}
	}

	public HashMap<Integer, HashMap<String, Object>> getFeatures() {
		return features;
	}

	public void setFeatures(HashMap<Integer, HashMap<String, Object>> features) {
		this.features = features;
	}
}
