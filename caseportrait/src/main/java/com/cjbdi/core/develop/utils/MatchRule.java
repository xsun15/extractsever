package com.cjbdi.core.develop.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRule {
    public static boolean IsMatch(String text, List<String> rules) {
        for (String rule : rules) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }
}
