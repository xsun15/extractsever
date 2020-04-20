package com.cjbdi.core.extractcenter.model;

import java.util.LinkedList;

public class JudgmentPaperModel {
    private String accusedName;
    private LinkedList<JudgmentPaperContentModel> contentList;

    public String getAccusedName() {
        return accusedName;
    }

    public void setAccusedName(String accusedName) {
        this.accusedName = accusedName;
    }

    public LinkedList<JudgmentPaperContentModel> getContentList() {
        return contentList == null ? new LinkedList<JudgmentPaperContentModel>() : contentList;
    }

    public void setContentList(LinkedList<JudgmentPaperContentModel> contentList) {
        this.contentList = contentList;
    }
}
