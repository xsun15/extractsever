package com.cjbdi.core.extractcenter.model;

import java.util.LinkedList;

public class JudgmentModel {
    private String docType;
    private String caseID;
    private String caseTitle;
    private String prosecutionOrgan;
    private LinkedList<JudgmentPaperModel> paper;

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

    public LinkedList<JudgmentPaperModel> getPaper() {
        return paper == null ? new LinkedList<>() : paper;
    }

    public void setPaper(LinkedList<JudgmentPaperModel> paper) {
        this.paper = paper;
    }
}
