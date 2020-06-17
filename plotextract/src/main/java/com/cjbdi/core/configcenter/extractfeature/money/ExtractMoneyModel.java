package com.cjbdi.core.configcenter.extractfeature.money;

import java.util.List;
import java.util.regex.Pattern;

public class ExtractMoneyModel {
	private String name;
	private List<Pattern> rule;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Pattern> getRule() {
		return rule;
	}

	public void setRule(List<Pattern> rule) {
		this.rule = rule;
	}
}
