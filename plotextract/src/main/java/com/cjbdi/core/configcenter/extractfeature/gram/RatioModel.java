package com.cjbdi.core.configcenter.extractfeature.gram;

import java.util.List;
import java.util.regex.Pattern;

public class RatioModel {
	private String name;
	private Double ratio;
	private List<Pattern> rule;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRatio() {
		return ratio;
	}

	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

	public List<Pattern> getRule() {
		return rule;
	}

	public void setRule(List<Pattern> rule) {
		this.rule = rule;
	}
}
