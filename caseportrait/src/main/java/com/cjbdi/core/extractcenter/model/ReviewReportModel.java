package com.cjbdi.core.extractcenter.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class ReviewReportModel {

    //收案日期
    private String receiveTime;
    //侦查机关
    private String investOrgan;
    //移送单位
    private String procOrgan;
    //移送案由
    private String casecause;
    //犯罪嫌疑人
    private String defendant;
    //侦查机关承办人
    private String investor;
    //公诉机关承办人
    private String procor;
    //承办人意见
    private String procopinion;
    //案情摘要
    private String abstractor;
    //犯罪嫌疑人及其他诉讼参与人的基本情况
    private String basiclitigant;
    //发、破案经过
    private String investproc;
    //侦查机关认定的犯罪事实与意见
    private String investopinion;
    //相关当事人、诉讼参与人的意见
    private String litigantopinion;
    //审查认定的事实、证据及分析
    private String evidence;
    //承办人意见
    private String procopiniondetail;
    //文书制作日期
    private String makedate;

    // 二次结构化
    private String facts;
    private Set<String> defendantSet = new HashSet<>();


    public String getReceiveTime() {
        return receiveTime;
    }

    public String getInvestOrgan() {
        return investOrgan;
    }

    public String getProcOrgan() {
        return procOrgan;
    }

    public String getCasecause() {
        return casecause;
    }

    public String getDefendant() {
        return defendant;
    }

    public String getInvestor() {
        return investor;
    }

    public String getProcor() {
        return procor;
    }

    public String getProcopinion() {
        return procopinion;
    }

    public String getAbstractor() {
        return abstractor;
    }

    public String getBasiclitigant() {
        return basiclitigant;
    }

    public String getInvestproc() {
        return investproc;
    }

    public String getInvestopinion() {
        return investopinion;
    }

    public String getLitigantopinion() {
        return litigantopinion;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getProcopiniondetail() {
        return procopiniondetail;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public void setInvestOrgan(String investOrgan) {
        this.investOrgan = investOrgan;
    }

    public void setProcOrgan(String procOrgan) {
        this.procOrgan = procOrgan;
    }

    public void setCasecause(String casecause) {
        this.casecause = casecause;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public void setProcor(String procor) {
        this.procor = procor;
    }

    public void setProcopinion(String procopinion) {
        this.procopinion = procopinion;
    }

    public void setAbstractor(String abstractor) {
        this.abstractor = abstractor;
    }

    public void setBasiclitigant(String basiclitigant) {
        this.basiclitigant = basiclitigant;
    }

    public void setInvestproc(String investproc) {
        this.investproc = investproc;
    }

    public void setInvestopinion(String investopinion) {
        this.investopinion = investopinion;
    }

    public void setLitigantopinion(String litigantopinion) {
        this.litigantopinion = litigantopinion;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public void setProcopiniondetail(String procopiniondetail) {
        this.procopiniondetail = procopiniondetail;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public Set<String> getDefendantSet() {
        return defendantSet;
    }

    public void setDefendantSet(Set<String> defendantSet) {
        this.defendantSet = defendantSet;
    }

    public void setModel(int order, String content) {
        switch (order) {
            case 1:
                this.receiveTime = content;
                break;
            case 2:
                this.investOrgan = content;
                break;
            case 3:
                this.procOrgan = content;
                break;
            case 4:
                this.casecause = content;
                break;
            case 5:
                this.defendant = content;
                break;
            case 6:
                this.investor = content;
                break;
            case 7:
                this.procor = content;
                break;
            case 8:
                this.procopinion = content;
                break;
            case 9:
                this.abstractor = content;
                break;
            case 10:
                this.basiclitigant = content;
                break;
            case 11:
                this.investproc = content;
                break;
            case 12:
                this.investopinion = content;
                break;
            case 13:
                this.litigantopinion = content;
                break;
            case 14:
                this.evidence = content;
                break;
            case 15:
                this.procopiniondetail = content;
                break;
            case 16:
                this.makedate = content;
                break;
        }
    }

}
