package com.cjbdi.core.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveSpecialChar {
    public static String clean(String content) {
        if (StringUtils.isNotEmpty(content)) {
            content = content.replaceAll("＊", "某");
            content = content.replaceAll(",", "，");
            content = content.replaceAll("\\(", "（");
            content = content.replaceAll("\\)", "）");
            content = content.replaceAll("\\[", "【");
            content = content.replaceAll("]", "】");
            String cleanContent = "";
            for (String line : content.split("\n")) {
                String regx = "^[0-9一二三四五六七八九十]{1,2}[^元]";
                Pattern pattern = Pattern.compile(regx);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    line = line.replaceFirst("\\.", "、");
                }
                line = replaceDot(line);
                line = line.replaceAll("s+", "");
                line = line.replaceAll("\n", "");
                line = replaceBlank(line);
                if (StringUtils.isNotEmpty(line))
                    cleanContent += line + "\n";
            }
            cleanContent = cleanContent.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：:*×\\n《》（）Oo〇【】〔〕]+", "");
            cleanContent = deleteDigitDot(cleanContent);
            return cleanContent;
        }
        return content;
    }

    public static String deleteDigitDot(String string) {
        string = string.replaceAll(" ", "");
        String regxExp = "\\d[，,]\\d";
        Pattern pattern = Pattern.compile(regxExp);

        String matchedString;
        String matchedString1;
        for(Matcher matcher = pattern.matcher(string); matcher.find(); string = string.replaceAll(matchedString, matchedString1)) {
            matchedString = matcher.group();
            matchedString1 = matchedString.replace("，", "");
            matchedString1 = matchedString1.replace(",", "");
        }
        return string;
    }

    public static String replaceDot(String string) {
        if (StringUtils.isEmpty(string)) return string;
        string = string.replaceAll(" ", "");
        String regxExp = "\\d\\.\\d";
        Pattern pattern = Pattern.compile(regxExp);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String matchText = matcher.group();
            matchText = matchText.replaceAll("\\.", "@@@@");
            string = string.replaceFirst(matcher.group(), matchText);
        }
        string = string.replaceAll("\\.", "。");
        string = string.replaceAll("@@@@", ".");
        return string;
    }

    public static String replaceBlank(String str){
        String dest = null;
        if(str == null){
            return dest;
        }else{
            Pattern p = Pattern.compile("\\s*|\t|\r|\n|&nbsp;");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            return dest;
        }
    }

    public String cleanSomeEnglish(String content) {
        content = content.replaceAll("null","");
        return content;
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
