package com.cjbdi.core.configcenter.extractfeature.gram;

import java.util.List;

public class ExtractRatioModel {
	private String placeHolderNum;
	private String placeHolderUnit;
	private List<String> rule;

	public String getPlaceHolderNum() {
		return placeHolderNum;
	}

	public void setPlaceHolderNum(String placeHolderNum) {
		this.placeHolderNum = placeHolderNum;
	}

	public String getPlaceHolderUnit() {
		return placeHolderUnit;
	}

	public void setPlaceHolderUnit(String placeHolderUnit) {
		this.placeHolderUnit = placeHolderUnit;
	}

	public List<String> getRule() {
		return rule;
	}

	public void setRule(List<String> rule) {
		this.rule = rule;
	}
}
