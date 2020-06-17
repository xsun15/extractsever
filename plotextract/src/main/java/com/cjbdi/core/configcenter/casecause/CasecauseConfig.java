package com.cjbdi.core.configcenter.casecause;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;

public class CasecauseConfig {
	private JSONObject casecause = new JSONObject();

	public CasecauseConfig() {
		String place = BeanFactoryConfig.configPlace.getOthersConfigPlace().getOthers().getString("caseCause");
		try {
			Yaml yaml = new Yaml();
			HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
			for (String id : hashMap.keySet()) {
				String name = hashMap.get(id).get("name").toString();
				casecause.put(id, name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject getCasecause() {
		return casecause;
	}

	public void setCasecause(JSONObject casecause) {
		this.casecause = casecause;
	}
}
