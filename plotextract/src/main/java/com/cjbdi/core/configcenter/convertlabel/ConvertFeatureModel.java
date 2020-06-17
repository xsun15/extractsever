package com.cjbdi.core.configcenter.convertlabel;

import java.util.List;

public class ConvertFeatureModel {
	private String name;
	private String type;
	private String dataType;
	private List<String> rule;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<String> getRule() {
		return rule;
	}

	public void setRule(List<String> rule) {
		this.rule = rule;
	}
}
