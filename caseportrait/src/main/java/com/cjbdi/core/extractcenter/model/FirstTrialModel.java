package com.cjbdi.core.extractcenter.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FirstTrialModel extends CommonModel{
    // 文书名称
    private String title;
    // 法院名称
    private String courtName;
    // 文书种类
    private String docType;
    // 案号
    private String courtCaseId;
    // 检察院名称
    private String procuName;
    // 被告人段
    private String defendant;
    // 起诉段
    private String sue;
    // 指控段
    private String accuse;
    // 审理段
    private String justice;
    // 证据段
    private String evidence;
    // 本院认为段
    private String courtOpinion;
    // 审判长
    private String chief;
    // 审判员
    private String judge;
    // 书记员
    private String recoder;
    // 审判日期
    private String judgeDate;
    // 附录
    private String append;
    // 省份
    private String province;

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getCourtCaseId() {
        return courtCaseId;
    }

    public void setCourtCaseId(String courtCaseId) {
        this.courtCaseId = courtCaseId;
    }

    public String getProcuName() {
        return procuName;
    }

    public void setProcuName(String procuName) {
        this.procuName = procuName;
    }

    public String getDefendant() {
        return defendant;
    }

    public void setDefendant(String defendant) {
        this.defendant = defendant;
    }

    public String getSue() {
        return sue;
    }

    public void setSue(String sue) {
        this.sue = sue;
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

    public String getCourtOpinion() {
        return courtOpinion;
    }

    public void setCourtOpinion(String courtOpinion) {
        this.courtOpinion = courtOpinion;
    }

    public String getChief() {
        return chief;
    }

    public void setChief(String chief) {
        this.chief = chief;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getRecoder() {
        return recoder;
    }

    public void setRecoder(String recoder) {
        this.recoder = recoder;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("courtName", this.courtName);
        map.put("docType", this.docType);
        map.put("courtCaseId", this.courtCaseId);
        map.put("procuName", this.procuName);
        map.put("defendant", this.defendant);
        map.put("sue", this.sue);
        map.put("accuse", this.accuse);
        map.put("justice", this.justice);
        map.put("courtOpinion", this.courtOpinion);
        map.put("chief", this.chief);
        map.put("judge", this.judge);
        map.put("judgeDate", this.judgeDate);
        map.put("recoder", this.recoder);
        map.put("append", this.append);
        map.put("extract/province", this.province);
        return map.toString();
    }
}
