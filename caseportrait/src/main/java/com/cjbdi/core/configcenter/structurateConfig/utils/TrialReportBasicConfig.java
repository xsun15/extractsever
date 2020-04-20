package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;

import java.util.List;

public class TrialReportBasicConfig {
	//案由
	private List<String> casecause;
	//案号
	private List<String> caseno;
	//控辩双方基本情况
	private List<String> bothDefendant;
	//案件由来
	private List<String> caseSource;
	//审理经过
	private List<String> caseTrial;
	//侦破揭发
	private List<String> caseDetected;
	//控辩事实内容
	private List<String> factFinding;
	//证据
	private List<String> evidence;
	//定性
	private List<String> qualitative;
	//说明问题
	private List<String> explain;
	//量刑
	private List<String> quantum;
	//结果
	private List<String> judicalResult;
	//承办人
	private List<String> agent;

	public TrialReportBasicConfig(String featureName, String sourceName) {
		this.casecause = YamlPropertySourceFactoryUser.loadConfig( featureName + ".casecause", sourceName);
		this.caseno = YamlPropertySourceFactoryUser.loadConfig(featureName + ".caseno", sourceName);
		this.bothDefendant = YamlPropertySourceFactoryUser.loadConfig( featureName + ".bothDefendant", sourceName);
		this.caseSource = YamlPropertySourceFactoryUser.loadConfig(featureName + ".caseSource", sourceName);
		this.caseTrial = YamlPropertySourceFactoryUser.loadConfig(featureName + ".caseTrial", sourceName);
		this.caseDetected = YamlPropertySourceFactoryUser.loadConfig( featureName + ".caseDetected", sourceName);
		this.factFinding = YamlPropertySourceFactoryUser.loadConfig(featureName + ".factFinding", sourceName);
		this.evidence = YamlPropertySourceFactoryUser.loadConfig(featureName + ".evidence", sourceName);
		this.qualitative = YamlPropertySourceFactoryUser.loadConfig(featureName + ".qualitative", sourceName);
		this.explain = YamlPropertySourceFactoryUser.loadConfig(featureName + ".explain", sourceName);
		this.quantum = YamlPropertySourceFactoryUser.loadConfig(featureName + ".quantum", sourceName);
		this.judicalResult = YamlPropertySourceFactoryUser.loadConfig(featureName + ".judicalResult", sourceName);
		this.agent = YamlPropertySourceFactoryUser.loadConfig(featureName + ".agent", sourceName);
	}

	public List<String> getCasecause() {
		return casecause;
	}

	public void setCasecause(List<String> casecause) {
		this.casecause = casecause;
	}

	public List<String> getCaseno() {
		return caseno;
	}

	public void setCaseno(List<String> caseno) {
		this.caseno = caseno;
	}

	public List<String> getBothDefendant() {
		return bothDefendant;
	}

	public void setBothDefendant(List<String> bothDefendant) {
		this.bothDefendant = bothDefendant;
	}

	public List<String> getCaseSource() {
		return caseSource;
	}

	public void setCaseSource(List<String> caseSource) {
		this.caseSource = caseSource;
	}

	public List<String> getCaseTrial() {
		return caseTrial;
	}

	public void setCaseTrial(List<String> caseTrial) {
		this.caseTrial = caseTrial;
	}

	public List<String> getCaseDetected() {
		return caseDetected;
	}

	public void setCaseDetected(List<String> caseDetected) {
		this.caseDetected = caseDetected;
	}

	public List<String> getFactFinding() {
		return factFinding;
	}

	public void setFactFinding(List<String> factFinding) {
		this.factFinding = factFinding;
	}

	public List<String> getEvidence() {
		return evidence;
	}

	public void setEvidence(List<String> evidence) {
		this.evidence = evidence;
	}

	public List<String> getQualitative() {
		return qualitative;
	}

	public void setQualitative(List<String> qualitative) {
		this.qualitative = qualitative;
	}

	public List<String> getExplain() {
		return explain;
	}

	public void setExplain(List<String> explain) {
		this.explain = explain;
	}

	public List<String> getQuantum() {
		return quantum;
	}

	public void setQuantum(List<String> quantum) {
		this.quantum = quantum;
	}

	public List<String> getJudicalResult() {
		return judicalResult;
	}

	public void setJudicalResult(List<String> judicalResult) {
		this.judicalResult = judicalResult;
	}

	public List<String> getAgent() {
		return agent;
	}

	public void setAgent(List<String> agent) {
		this.agent = agent;
	}
}
