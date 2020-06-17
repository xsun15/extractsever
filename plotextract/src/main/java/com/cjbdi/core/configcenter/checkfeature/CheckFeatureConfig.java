package com.cjbdi.core.configcenter.checkfeature;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.utils.CommonTools;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.HashMap;

public class CheckFeatureConfig {
	private JSONObject feature = new JSONObject();

	public CheckFeatureConfig() {
		Yaml yaml = new Yaml();
		for (String caseId : BeanFactoryConfig.configPlace.getCheckLabelConfigPlace().getFeature().keySet()) {
			try {
				String place = BeanFactoryConfig.configPlace.getCheckLabelConfigPlace().getFeature().getString(caseId);
				HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
				JSONObject oneCaseId = new JSONObject();
				for (String id : hashMap.keySet()) {
					CheckFeatureModel checkFeatureModel = new CheckFeatureModel();
					if (hashMap.get(id).containsKey("name") && hashMap.get(id).get("name")!=null)
						checkFeatureModel.setName(hashMap.get(id).get("name").toString());
					if (hashMap.get(id).containsKey("caseCause") && hashMap.get(id).get("caseCause")!=null )
						checkFeatureModel.setCaseCause(hashMap.get(id).get("caseCause").toString());
					boolean enDecrypt = BeanFactoryConfig.configPlace.getEnDecryptConfigPlace().getIsEncrypt();
					if (hashMap.get(id).containsKey("rule") && hashMap.get(id).get("rule")!=null )
						checkFeatureModel.setRule(CommonTools.object2Str(hashMap.get(id).get("rule"), enDecrypt));
					oneCaseId.put(id, checkFeatureModel);
				}
				feature.put(caseId, oneCaseId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
