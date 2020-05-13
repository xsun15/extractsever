package com.cjbdi.core.extractcenter.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class IndictOpinionModel {
    // 公安机关
    private String policename;
    // 案号
    private String caseid;
    // 被告人段
    private String defendant;
    // 公安机关侦查段
    private String investigate;
    // 侦查机关认为段
    private String policeOpinion;
    // 敬礼段
    private String salute;
    // 移送检察院
    private String procu;
    // 移交起诉日期
    private String makedate;
    // 附录
    private String append;

    public String getPolicename() {
        return policename;
    }

    public void setPolicename(String policename) {
        this.policename = policename;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getInvestigate() {
        return investigate;
    }

    public void setInvestigate(String investigate) {
        this.investigate = investigate;
    }

    public String getPoliceOpinion() {
        return policeOpinion;
    }

    public void setPoliceOpinion(String policeOpinion) {
        this.policeOpinion = policeOpinion;
    }

    public String getSalute() {
        return salute;
    }

    public void setSalute(String salute) {
        this.salute = salute;
    }

    public String getProcu() {
        return procu;
    }

    public void setProcu(String procu) {
        this.procu = procu;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public void setModel(int order, String content) {
        switch (order) {
            case 1:
                this.policename = content;
                break;
            case 2:
                this.caseid = content;
                break;
            case 3:
                this.defendant = content;
                break;
            case 4:
                this.investigate = content;
                break;
            case 5:
                this.policeOpinion = content;
                break;
            case 6:
                this.salute = content;
                break;
            case 7:
                this.procu = content;
                break;
            case 8:
                this.makedate = content;
                break;
            case 9:
                this.append = content;
                break;
        }
    }
}
