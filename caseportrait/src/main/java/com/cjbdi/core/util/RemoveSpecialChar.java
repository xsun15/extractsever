package com.cjbdi.core.util;

public class RemoveSpecialChar {
    public String clean(String content) {
        content = content.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：:*×\\n《》（）Oo〇]+","");
        String[] contentList = content.split("\n");
        String result = "";
        for (String line : contentList) {
            if (! "".equals(line.trim().replaceAll("\\s+", ""))) {
                result += line + "\n";
            }
        }
        return result.trim();
    }
    public String cleanTrialReportModel(String content) {
        content = content.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”:：*×\\n《》Oo〇（）()\\s]+","");
        String[] contentList = content.split("\n");
        String result = "";
        for (String line : contentList) {
            if (! "".equals(line.trim().replaceAll("\\s+", ""))) {
                result += line + "\n";
            }
        }
        return result.trim();
    }
}
