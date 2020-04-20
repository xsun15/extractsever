package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;

import java.util.List;

public class NoprosBasicConfig {
	private List<String> defendant;
	private List<String> investigate;
	private List<String> justice;
	private List<String> evidence;
	private List<String> procuopinion;
	private List<String> defendantappeal;
	private List<String> victimappeal;
	private List<String> procuratorate;
	private List<String> judgeDate;
	private List<String> append;

	public NoprosBasicConfig(String featureName, String sourceName) {
		this.defendant = YamlPropertySourceFactoryUser.loadConfig( featureName + ".defendant", sourceName);
		this.investigate = YamlPropertySourceFactoryUser.loadConfig(featureName + ".investigate", sourceName);
		this.justice = YamlPropertySourceFactoryUser.loadConfig( featureName + ".justice", sourceName);
		this.evidence = YamlPropertySourceFactoryUser.loadConfig(featureName + ".evidence", sourceName);
		this.procuopinion = YamlPropertySourceFactoryUser.loadConfig(featureName + ".procuopinion", sourceName);
		this.defendantappeal = YamlPropertySourceFactoryUser.loadConfig( featureName + ".defendantappeal", sourceName);
		this.victimappeal = YamlPropertySourceFactoryUser.loadConfig(featureName + ".victimappeal", sourceName);
		this.procuratorate = YamlPropertySourceFactoryUser.loadConfig(featureName + ".procuratorate", sourceName);
		this.judgeDate = YamlPropertySourceFactoryUser.loadConfig(featureName + ".judgeDate", sourceName);
		this.append = YamlPropertySourceFactoryUser.loadConfig(featureName + ".append", sourceName);
	}

	public List<String> getDefendant() {
		return defendant;
	}

	public void setDefendant(List<String> defendant) {
		this.defendant = defendant;
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

	public List<String> getDefendantappeal() {
		return defendantappeal;
	}

	public void setDefendantappeal(List<String> defendantappeal) {
		this.defendantappeal = defendantappeal;
	}

	public List<String> getVictimappeal() {
		return victimappeal;
	}

	public void setVictimappeal(List<String> victimappeal) {
		this.victimappeal = victimappeal;
	}

	public List<String> getProcuratorate() {
		return procuratorate;
	}

	public void setProcuratorate(List<String> procuratorate) {
		this.procuratorate = procuratorate;
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
