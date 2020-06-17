package com.cjbdi.core.extractcenter.utils.tracesource;

import org.apache.commons.lang.StringUtils;

public class MatchModel {
    private double value = 0.0;
    private String matchText;
    // 匹配到的长句子以句号或分号分割
    private String matchLongSentence;
    // 匹配到的短句子以逗号或顿号分割
    private String matchShortSentence;
    // 抽取的段落原文
    private String paraText;
    // 抽取的自然段落序号
    private int lineIndex;
    // 金额有效、无效
    private String type;
    // 无效金额能否转换
    private boolean isConvert = false;
    // 金额的描述是否是确切数字
    private String isAccurate = "精确";
    private String paraName;
    private Integer priorityLevel;
    private String usedRule;
    private Integer startPos;
    private Integer endPos;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getMatchText() {
        return matchText;
    }

    public void setMatchText(String matchText) {
        this.matchText = matchText;
    }

    public String getMatchLongSentence() {
        return matchLongSentence;
    }

    public void setMatchLongSentence(String matchLongSentence) {
        this.matchLongSentence = matchLongSentence;
    }

    public String getMatchShortSentence() {
        return matchShortSentence;
    }

    public void setMatchShortSentence(String matchShortSentence) {
        this.matchShortSentence = matchShortSentence;
    }

    public String getParaText() {
        return paraText;
    }

    public void setParaText(String paraText) {
        this.paraText = paraText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsConvert() {
        return isConvert;
    }

    public void setIsConvert(boolean convert) {
        isConvert = convert;
    }

    public String getIsAccurate() {
        return isAccurate;
    }

    public void setIsAccurate(String isAccurate) {
        this.isAccurate = isAccurate;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getUsedRule() {
        return usedRule;
    }

    public void setUsedRule(String usedRule) {
        this.usedRule = usedRule;
    }

    public Integer getStartPos() {
        return startPos;
    }

    public void setStartPos(Integer startPos) {
        this.startPos = startPos;
    }

    public Integer getEndPos() {
        return endPos;
    }

    public void setEndPos(Integer endPos) {
        this.endPos = endPos;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }
}
