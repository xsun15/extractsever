package com.cjbdi.core.configcenter.convertlabel;

import com.alibaba.fastjson.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

public class PurposeFeatureConfig {
	private JSONObject feature = new JSONObject();

	public PurposeFeatureConfig(JSONObject purpose) {
		Yaml yaml = new Yaml();
		for (String caseId : purpose.keySet()) {
			try {
				String place = purpose.getString(caseId);
				HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
				JSONObject oneCaseId = new JSONObject();
				for (String id : hashMap.keySet()) {
					ConvertFeatureModel convertFeatureModel = new ConvertFeatureModel();
					if (hashMap.get(id).containsKey("name") && hashMap.get(id).get("name")!=null)
						convertFeatureModel.setName(hashMap.get(id).get("name").toString());
					if (hashMap.get(id).containsKey("type") && hashMap.get(id).get("type")!=null)
						convertFeatureModel.setType(hashMap.get(id).get("type").toString());
					if (hashMap.get(id).containsKey("dataType") && hashMap.get(id).get("dataType")!=null)
						convertFeatureModel.setName(hashMap.get(id).get("dataType").toString());
					if (hashMap.get(id).containsKey("rule") && hashMap.get(id).get("rule")!=null)
						convertFeatureModel.setRule((List<String>) hashMap.get(id).get("rule"));
					oneCaseId.put(id, convertFeatureModel);
				}
				feature.put(caseId, oneCaseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
