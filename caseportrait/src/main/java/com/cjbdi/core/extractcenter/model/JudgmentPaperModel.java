package com.cjbdi.core.extractcenter.model;

import java.util.List;

public class JudgmentPaperModel {
    private String accusedName;
    private List<JudgmentPaperContentModel> contentList;

    public String getAccusedName() {
        return accusedName;
    }

    public void setAccusedName(String accusedName) {
        this.accusedName = accusedName;
    }

    public List<JudgmentPaperContentModel> getContentList() {
        return contentList;
    }

    public void setContentList(List<JudgmentPaperContentModel> contentList) {
        this.contentList = contentList;
    }
}
