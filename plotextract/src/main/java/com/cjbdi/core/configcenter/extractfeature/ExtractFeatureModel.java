package com.cjbdi.core.configcenter.extractfeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExtractFeatureModel {
	private String name;
	private String type;
	private String ptype;
	private String commonCode;
	private String keyWord;
	private String noiseWord;
	private String usedClass;
	private String mosicDelete;
	private Map<Pattern, Double> ratio;
	private List<Pattern> sumPositiveRule;
	private List<Pattern> positivePureRule;
	private List<Pattern> positiveModelRule;
	private List<Pattern> negativePureRule;
	private List<Pattern> negativeModelRule;
	private List<Pattern> rule;
	private String modelUrl;

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

	public String getUsedClass() {
		return usedClass;
	}

	public void setUsedClass(String usedClass) {
		this.usedClass = usedClass;
	}

	public String getMosicDelete() {
		return mosicDelete;
	}

	public void setMosicDelete(String mosicDelete) {
		this.mosicDelete = mosicDelete;
	}

	public List<Pattern> getPositivePureRule() {
		return positivePureRule;
	}

	public void setPositivePureRule(List<Pattern> positivePureRule) {
		this.positivePureRule = positivePureRule;
	}

	public List<Pattern> getPositiveModelRule() {
		return positiveModelRule;
	}

	public void setPositiveModelRule(List<Pattern> positiveModelRule) {
		this.positiveModelRule = positiveModelRule;
	}

	public List<Pattern> getNegativePureRule() {
		return negativePureRule;
	}

	public void setNegativePureRule(List<Pattern> negativePureRule) {
		this.negativePureRule = negativePureRule;
	}

	public List<Pattern> getNegativeModelRule() {
		return negativeModelRule;
	}

	public void setNegativeModelRule(List<Pattern> negativeModelRule) {
		this.negativeModelRule = negativeModelRule;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getNoiseWord() {
		return noiseWord;
	}

	public void setNoiseWord(String noiseWord) {
		this.noiseWord = noiseWord;
	}

	public List<Pattern> getRule() {
		return rule;
	}

	public void setRule(List<Pattern> rule) {
		this.rule = rule;
	}

	public Map<Pattern, Double> getRatio() {
		return ratio;
	}

	public void setRatio(Map<Pattern, Double> ratio) {
		this.ratio = ratio;
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public List<Pattern> getSumPositiveRule() {
		return sumPositiveRule;
	}

	public void setSumPositiveRule(List<Pattern> sumPositiveRule) {
		this.sumPositiveRule = sumPositiveRule;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getCommonCode() {
		return commonCode;
	}

	public void setCommonCode(String commonCode) {
		this.commonCode = commonCode;
	}

	public ExtractFeatureModel getExtractFeatureModelNew() {
		ExtractFeatureModel extractFeatureModel = new ExtractFeatureModel();
		extractFeatureModel.setName(this.name);
		extractFeatureModel.setType(this.type);
		extractFeatureModel.setPtype(this.ptype);
		extractFeatureModel.setKeyWord(this.keyWord);
		extractFeatureModel.setNoiseWord(this.noiseWord);
		extractFeatureModel.setUsedClass(this.usedClass);
		extractFeatureModel.setMosicDelete(this.mosicDelete);
		extractFeatureModel.setRatio(new HashMap<>(this.ratio));
		extractFeatureModel.setPositivePureRule(new ArrayList<>(this.positivePureRule));
		extractFeatureModel.setPositiveModelRule(new ArrayList<>(this.positiveModelRule));
		extractFeatureModel.setNegativePureRule(new ArrayList<>(this.negativePureRule));
		extractFeatureModel.setNegativeModelRule(new ArrayList<>(this.negativeModelRule));
		extractFeatureModel.setRule(new ArrayList<>(this.rule));
		extractFeatureModel.setModelUrl(this.modelUrl);
		return extractFeatureModel;
	}
}
