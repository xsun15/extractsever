package com.cjbdi.core.extractcenter.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class IndicitmentModel extends CommonModel{
    // 检察院
    private String procuName;
    // 文书种类
    private String docType;
    // 案号
    private String procuCaseId;
    // 被告人段
    private String defendant;
    // 公安机关侦查段
    private String investigate;
    // 审理段
    private String justice;
    // 证据段
    private String evidence;
    // 本院认为段
    private String procuOpinion;
    // 敬礼段
    private String salute;
    // 移送法院
    private String court;
    // 检察员
    private String procurator;
    // 审判日期
    private String judgeDate;
    // 附录
    private String append;
    // 省份
    private String province;

    public String getProcuName() {
        return procuName;
    }

    public void setProcuName(String procuName) {
        this.procuName = procuName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getProcuCaseId() {
        return procuCaseId;
    }

    public void setProcuCaseId(String procuCaseId) {
        this.procuCaseId = procuCaseId;
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

    public String getJustice() {
        return justice;
    }

    public void setJustice(String justice) {
        this.justice = justice;
    }

    public String getProcuOpinion() {
        return procuOpinion;
    }

    public void setProcuOpinion(String procuOpinion) {
        this.procuOpinion = procuOpinion;
    }

    public String getSalute() {
        return salute;
    }

    public void setSalute(String salute) {
        this.salute = salute;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getProcurator() {
        return procurator;
    }

    public void setProcurator(String procurator) {
        this.procurator = procurator;
    }

    public String getJudgeDate() {
        return judgeDate;
    }

    public void setJudgeDate(String judgeDate) {
        this.judgeDate = judgeDate;
    }

    public String getAppend() {
        return append;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("procuName", this.procuName);
        map.put("docType", this.docType);
        map.put("procuCaseId", this.procuCaseId);
        map.put("defendant", this.defendant);
        map.put("investigate", this.investigate);
        map.put("justice", this.justice);
        map.put("evidence", this.evidence);
        map.put("procuOpinion", this.procuOpinion);
        map.put("salute", this.salute);
        map.put("court", this.court);
        map.put("procurator",this.procurator);
        map.put("judgeDate", this.judgeDate);
        map.put("append", this.append);
        map.put("extract/province", this.province);
        return map.toString();
    }
}
