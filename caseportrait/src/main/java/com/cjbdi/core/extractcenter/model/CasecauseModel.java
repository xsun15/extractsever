package com.cjbdi.core.extractcenter.model;

public class CasecauseModel {
    private String casecause;
    // 被告人段
    private String defendant;
    // 起诉段
    private String transfer;
    // 指控段
    private String accuse;
    // 审理段
    private String justice;
    // 本院认为段
    private String opinion;
    // 审判时间
    private String judgeDate;
    // 省份
    private String province;

    public String getCasecause() {
        return casecause;
    }

    public void setCasecause(String casecause) {
        this.casecause = casecause;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public String getAccuse() {
        return accuse;
    }

    public void setAccuse(String accuse) {
        this.accuse = accuse;
    }

    public String getJustice() {
        return justice;
    }

    public void setJustice(String justice) {
        this.justice = justice;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
