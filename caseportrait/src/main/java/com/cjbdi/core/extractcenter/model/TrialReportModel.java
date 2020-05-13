package com.cjbdi.core.extractcenter.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrialReportModel {

    //案由
    private String casecause;
    //案号
    private String caseid;
    //控辩双方基本情况
    private String bothsides;
    //案件的由来和审理经过
    private String originproc;
    //案件的侦破、揭发情况
    private String invest;
    //控辩双方的主要内容
    private String accusDefendDetail;
    //审理查明的事实和证据
    private String justice;
    //需要说明的问题
    private String explain;
    //处理意见和理由
    private String courtopinion;
    //承办人
    private String judge;
    //文书制作日期
    private String makedate;

    public String getCasecause() {
        return casecause;
    }

    public void setCasecause(String casecause) {
        this.casecause = casecause;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getBothsides() {
        return bothsides;
    }

    public void setBothsides(String bothsides) {
        this.bothsides = bothsides;
    }

    public String getOriginproc() {
        return originproc;
    }

    public void setOriginproc(String originproc) {
        this.originproc = originproc;
    }

    public String getInvest() {
        return invest;
    }

    public void setInvest(String invest) {
        this.invest = invest;
    }

    public String getAccusDefendDetail() {
        return accusDefendDetail;
    }

    public void setAccusDefendDetail(String accusDefendDetail) {
        this.accusDefendDetail = accusDefendDetail;
    }

    public String getJustice() {
        return justice;
    }

    public void setJustice(String justice) {
        this.justice = justice;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getCourtopinion() {
        return courtopinion;
    }

    public void setCourtopinion(String courtopinion) {
        this.courtopinion = courtopinion;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public void setModel(int order, String content) {
        switch (order) {
            case 1:
                this.casecause = content;
                break;
            case 2:
                this.caseid = content;
                break;
            case 3:
                this.bothsides = content;
                break;
            case 4:
                this.originproc = content;
                break;
            case 5:
                this.invest = content;
                break;
            case 6:
                this.accusDefendDetail = content;
                break;
            case 7:
                this.justice = content;
                break;
            case 8:
                this.explain = content;
                break;
            case 9:
                this.courtopinion = content;
                break;
            case 10:
                this.judge = content;
                break;
            case 11:
                this.makedate = content;
                break;
        }
    }
}
