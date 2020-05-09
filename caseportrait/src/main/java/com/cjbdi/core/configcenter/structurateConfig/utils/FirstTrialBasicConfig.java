package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;

import java.util.List;

public class FirstTrialBasicConfig {
	private List<String> defendant;
	private List<String> sue;
	private List<String> accuse;
	private List<String> justice;
	private List<String> evidence;
	private List<String> courtOpinion;
	private List<String> chief;
	private List<String> judge;
	private List<String> recorder;
	private List<String> judgeDate;
	private List<String> append;

	public FirstTrialBasicConfig(String featureName, String sourceName) {
		this.defendant = YamlPropertySourceFactoryUser.loadConfig( featureName + ".defendant", sourceName);
		this.sue = YamlPropertySourceFactoryUser.loadConfig(featureName + ".sue", sourceName);
		this.accuse = YamlPropertySourceFactoryUser.loadConfig(featureName + ".accuse", sourceName);
		this.justice = YamlPropertySourceFactoryUser.loadConfig( featureName + ".justice", sourceName);
		this.evidence = YamlPropertySourceFactoryUser.loadConfig(featureName + ".evidence", sourceName);
		this.courtOpinion = YamlPropertySourceFactoryUser.loadConfig(featureName + ".courtopinion", sourceName);
		this.chief = YamlPropertySourceFactoryUser.loadConfig( featureName + ".chief", sourceName);
		this.judge = YamlPropertySourceFactoryUser.loadConfig(featureName + ".judge", sourceName);
		this.recorder = YamlPropertySourceFactoryUser.loadConfig(featureName + ".recorder", sourceName);
		this.judgeDate = YamlPropertySourceFactoryUser.loadConfig(featureName + ".judgeDate", sourceName);
		this.append = YamlPropertySourceFactoryUser.loadConfig(featureName + ".append", sourceName);
	}

	public List<String> getDefendant() {
		return defendant;
	}

	public void setDefendant(List<String> defendant) {
		this.defendant = defendant;
	}

	public List<String> getSue() {
		return sue;
	}

	public void setSue(List<String> sue) {
		this.sue = sue;
	}

	public List<String> getAccuse() {
		return accuse;
	}

	public void setAccuse(List<String> accuse) {
		this.accuse = accuse;
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

	public List<String> getCourtOpinion() {
		return courtOpinion;
	}

	public void setCourtOpinion(List<String> courtOpinion) {
		this.courtOpinion = courtOpinion;
	}

	public List<String> getChief() {
		return chief;
	}

	public void setChief(List<String> chief) {
		this.chief = chief;
	}

	public List<String> getJudge() {
		return judge;
	}

	public void setJudge(List<String> judge) {
		this.judge = judge;
	}

	public List<String> getRecorder() {
		return recorder;
	}

	public void setRecorder(List<String> recorder) {
		this.recorder = recorder;
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
