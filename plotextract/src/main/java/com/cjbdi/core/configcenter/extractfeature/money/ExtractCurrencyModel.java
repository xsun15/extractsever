package com.cjbdi.core.configcenter.extractfeature.money;

import java.util.List;
import java.util.regex.Pattern;

public class ExtractCurrencyModel {
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
