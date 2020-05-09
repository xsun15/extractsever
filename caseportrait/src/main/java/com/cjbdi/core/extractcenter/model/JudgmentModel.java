package com.cjbdi.core.extractcenter.model;

import java.util.LinkedList;
import java.util.List;

public class JudgmentModel {
    private String docType;
    private String caseID;
    private String caseTitle;
    private String prosecutionOrgan;
    private List<JudgmentPaperModel> paper;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    public String getCaseTitle() {
        return caseTitle;
    }

    public void setCaseTitle(String caseTitle) {
        this.caseTitle = caseTitle;
    }

    public String getProsecutionOrgan() {
        return prosecutionOrgan;
    }

    public void setProsecutionOrgan(String prosecutionOrgan) {
        this.prosecutionOrgan = prosecutionOrgan;
    }

    public List<JudgmentPaperModel> getPaper() {
        return paper;
    }

    public void setPaper(List<JudgmentPaperModel> paper) {
        this.paper = paper;
    }
}
