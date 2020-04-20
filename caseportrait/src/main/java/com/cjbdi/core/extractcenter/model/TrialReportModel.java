package com.cjbdi.core.extractcenter.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrialReportModel {

    //案由
    private String casecause;
    //案号
    private String caseno;
    //控辩双方基本情况
    private String bothDefendant;
    //案件由来
    private String caseSource;
    //审理经过
    private String caseTrial;
    //侦破揭发
    private String caseDetected;
    //控辩事实内容
    private String factFinding;
    //证据
    private String evidence;
    //定性
    private String qualitative;
    //说明问题
    private String explain;
    //量刑
    private String quantum;
    //结果
    private String judicalResult;
    //承办人
    private String agent;

    public String getCasecause() {
        return casecause;
    }

    public void setCasecause(String casecause) {
        this.casecause = casecause;
    }

    public String getCaseno() {
        return caseno;
    }

    public void setCaseno(String caseno) {
        this.caseno = caseno;
    }

    public String getBothDefendant() {
        return bothDefendant;
    }

    public void setBothDefendant(String bothDefendant) {
        this.bothDefendant = bothDefendant;
    }

    public String getCaseSource() {
        return caseSource;
    }

    public void setCaseSource(String caseSource) {
        this.caseSource = caseSource;
    }

    public String getCaseTrial() {
        return caseTrial;
    }

    public void setCaseTrial(String caseTrial) {
        this.caseTrial = caseTrial;
    }

    public String getFactFinding() {
        return factFinding;
    }

    public void setFactFinding(String factFinding) {
        this.factFinding = factFinding;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getQualitative() {
        return qualitative;
    }

    public void setQualitative(String qualitative) {
        this.qualitative = qualitative;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getQuantum() {
        return quantum;
    }

    public void setQuantum(String quantum) {
        this.quantum = quantum;
    }

    public String getJudicalResult() {
        return judicalResult;
    }

    public void setJudicalResult(String judicalResult) {
        this.judicalResult = judicalResult;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getCaseDetected() {
        return caseDetected;
    }

    public void setCaseDetected(String caseDetected) {
        this.caseDetected = caseDetected;
    }

    @Override
    public String toString() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("extract/casecause", this.casecause);
        map.put("caseno", this.caseno);
        map.put("bothDefendant", this.bothDefendant);
        map.put("caseSource", this.caseSource);
        map.put("caseTrial", this.caseTrial);
        map.put("caseDetected", this.caseDetected);
        map.put("factFinding", this.factFinding);
        map.put("evidence", this.evidence);
        map.put("qualitative", this.qualitative);
        map.put("explain", this.explain);
        map.put("quantum", this.quantum);
        map.put("judicalResult", this.judicalResult);
        map.put("agent", this.agent);
        return map.toString();
    }
}
