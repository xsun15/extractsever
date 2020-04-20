package com.cjbdi.core.extractcenter.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class NoprosModel extends CommonModel{
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
    // 本院认为段
    private String procuOpinion;
    // 被告人申诉
    private String defendantappeal;
    // 被害人申诉
    private String victimappeal;
    // 检察院
    private String procuratorate;
    // 审判日期
    private String judgeDate;
    // 附录
    private String append;

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

    public String getDefendantappeal() {
        return defendantappeal;
    }

    public void setDefendantappeal(String defendantappeal) {
        this.defendantappeal = defendantappeal;
    }

    public String getVictimappeal() {
        return victimappeal;
    }

    public void setVictimappeal(String victimappeal) {
        this.victimappeal = victimappeal;
    }

    public String getProcuratorate() {
        return procuratorate;
    }

    public void setProcuratorate(String procuratorate) {
        this.procuratorate = procuratorate;
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

    @Override
    public String toString() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("procuName", this.procuName);
        map.put("docType", this.docType);
        map.put("procuCaseId", this.procuCaseId);
        map.put("defendant", this.defendant);
        map.put("investigate", this.investigate);
        map.put("justice", this.justice);
        map.put("procuOpinion", this.procuOpinion);
        map.put("salute", this.defendantappeal);
        map.put("court", this.victimappeal);
        map.put("procurator",this.procuratorate);
        map.put("judgeDate", this.judgeDate);
        map.put("append", this.append);
        return map.toString();
    }
}
