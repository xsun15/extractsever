package com.cjbdi.core.configcenter.splitdoc;

import java.util.List;
import java.util.regex.Pattern;

public class BasicModel {
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
