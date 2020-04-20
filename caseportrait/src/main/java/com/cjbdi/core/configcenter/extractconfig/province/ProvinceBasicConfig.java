package com.cjbdi.core.configcenter.extractconfig.province;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ProvinceBasicConfig {
	private List<String> name;
	private List<String> code;
	private List<String> rule;

	public ProvinceBasicConfig(String featureName, String sourceName) {
		this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
		this.code = YamlPropertySourceFactoryUser.loadConfig(featureName + ".code", sourceName);
		this.rule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rule", sourceName);
	}

	public String getName() {
		return StringUtils.strip(name.toString(), "[]");
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public String getCode() {
		return StringUtils.strip(code.toString(), "[]");
	}

	public void setCode(List<String> code) {
		this.code = code;
	}

	public List<String> getRule() {
		return rule;
	}

	public void setRule(List<String> rule) {
		this.rule = rule;
	}
}
