package com.cjbdi.core.configcenter.splitdoc;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.utils.CommonTools;
import org.apache.commons.lang.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;

public class SplitDocConfig {
	private FactModel factModel;
	private JSONObject priorityLevel;

	public SplitDocConfig() {
		this.factModel = new FactModel(BeanFactoryConfig.configPlace.getOthersConfigPlace().getOthers().getString("factSplit"));
		setPriorityLevel(BeanFactoryConfig.configPlace.getOthersConfigPlace().getOthers().getString("priorityLevel"));
	}

	public void setPriorityLevel(String place) {
		Yaml yaml = new Yaml();
		try {
			priorityLevel = new JSONObject();
			HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
			for (String id : hashMap.keySet()) {
				if (hashMap.get(id).containsKey("level") && hashMap.get(id).get("level") != null)
					priorityLevel.put(id, hashMap.get(id).get("level").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPriorityLevel(JSONObject priorityLevel) {
		this.priorityLevel = priorityLevel;
	}

	public JSONObject getPriorityLevel() {
		return priorityLevel;
	}

	public FactModel getFactModel() {
		return factModel;
	}

	public void setFactModel(FactModel factModel) {
		this.factModel = factModel;
	}
}
