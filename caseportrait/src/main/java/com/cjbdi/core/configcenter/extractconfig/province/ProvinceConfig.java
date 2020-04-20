package com.cjbdi.core.configcenter.extractconfig.province;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ProvinceConfig {

	protected LinkedHashMap<String, ProvinceBasicConfig> province = new LinkedHashMap<>();

	public ProvinceConfig() {
		String place = BeanConfigCenter.configPlace.getExtractionConfigPlace().getProvince();
		ArrayList<String> featureNameList = GetFeatureName.run(place);
		for (String feature : featureNameList) {
			ProvinceBasicConfig provinceBasicConfig = new ProvinceBasicConfig(feature, place);
			String code = provinceBasicConfig.getCode();
			province.put(code, provinceBasicConfig);
		}
	}

	public LinkedHashMap<String, ProvinceBasicConfig> getProvince() {
		return province;
	}

	public void setProvince(LinkedHashMap<String, ProvinceBasicConfig> province) {
		this.province = province;
	}
}
