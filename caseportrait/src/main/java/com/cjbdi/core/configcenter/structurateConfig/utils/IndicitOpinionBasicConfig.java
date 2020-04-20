package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;

import java.util.List;

public class IndicitOpinionBasicConfig {
	private List<String> defendant;
	private List<String> experience;
	private List<String> investigate;
	private List<String> justice;
	private List<String> evidence;
	private List<String> procuopinion;
	private List<String> salute;
	private List<String> court;
	private List<String> procurator;
	private List<String> judgeDate;
	private List<String> append;

	public IndicitOpinionBasicConfig(String featureName, String sourceName) {
		this.defendant = YamlPropertySourceFactoryUser.loadConfig( featureName + ".defendant", sourceName);
		this.experience = YamlPropertySourceFactoryUser.loadConfig(featureName + ".experience", sourceName);
		this.investigate = YamlPropertySourceFactoryUser.loadConfig(featureName + ".investigate", sourceName);
		this.justice = YamlPropertySourceFactoryUser.loadConfig( featureName + ".justice", sourceName);
		this.evidence = YamlPropertySourceFactoryUser.loadConfig(featureName + ".evidence", sourceName);
		this.procuopinion = YamlPropertySourceFactoryUser.loadConfig(featureName + ".procuopinion", sourceName);
		this.salute = YamlPropertySourceFactoryUser.loadConfig( featureName + ".salute", sourceName);
		this.court = YamlPropertySourceFactoryUser.loadConfig(featureName + ".court", sourceName);
		this.procurator = YamlPropertySourceFactoryUser.loadConfig(featureName + ".procurator", sourceName);
		this.judgeDate = YamlPropertySourceFactoryUser.loadConfig(featureName + ".judgeDate", sourceName);
		this.append = YamlPropertySourceFactoryUser.loadConfig(featureName + ".append", sourceName);
	}

	public List<String> getDefendant() {
		return defendant;
	}

	public void setDefendant(List<String> defendant) {
		this.defendant = defendant;
	}

	public List<String> getExperience() {
		return experience;
	}

	public void setExperience(List<String> experience) {
		this.experience = experience;
	}

	public List<String> getInvestigate() {
		return investigate;
	}

	public void setInvestigate(List<String> investigate) {
		this.investigate = investigate;
	}

	public List<String> getProcuopinion() {
		return procuopinion;
	}

	public void setProcuopinion(List<String> procuopinion) {
		this.procuopinion = procuopinion;
	}

	public List<String> getSalute() {
		return salute;
	}

	public void setSalute(List<String> salute) {
		this.salute = salute;
	}

	public List<String> getCourt() {
		return court;
	}

	public void setCourt(List<String> court) {
		this.court = court;
	}

	public List<String> getProcurator() {
		return procurator;
	}

	public void setProcurator(List<String> procurator) {
		this.procurator = procurator;
	}

	public List<String> getJustice() {
		return justice;
	}

	public void setJustice(List<String> justice) {
		this.justice = justice;
	}

	public List<String> getEvidence() {
		return evidence;
	}

	public void setEvidence(List<String> evidence) {
		this.evidence = evidence;
	}

	public List<String> getJudgeDate() {
		return judgeDate;
	}

	public void setJudgeDate(List<String> judgeDate) {
		this.judgeDate = judgeDate;
	}

	public List<String> getAppend() {
		return append;
	}

	public void setAppend(List<String> append) {
		this.append = append;
	}
}
