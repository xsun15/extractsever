package com.cjbdi.core.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchRule {
    public static Map<String, String> matchRule(String text, List<String> pruleList, List<String> nruleList) {
        Map<String, String> map = new HashMap<>();
        for (String rule : nruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                text = text.replaceAll(matcher.group(), "");
            }
        }
        for (String rule : pruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                map.put("rule", rule);
                map.put("text", matcher.group());
                return map;
            }
        }
        return map;
    }

    public static Map<String, String> mathYearMonthDay(String text, List<String> pruleList, List<String> nruleList) {
        Map<String, String> map = new HashMap<>();
        for (String rule : nruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                text = text.replaceAll(matcher.group(), "");
            }
        }
        for (String rule : pruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                map.put("text", matcher.group());
                map.put("rule", rule);
                if (matcher.groupCount()==6) {
                    map.put("year", matcher.group(2));
                    map.put("month", matcher.group(4));
                    map.put("day", matcher.group(6));
                } else if (matcher.groupCount()==9) {
                    map.put("亿", matcher.group(1));
                    map.put("千万", matcher.group(2));
                    map.put("百万", matcher.group(3));
                    map.put("十万", matcher.group(4));
                    map.put("万", matcher.group(5));
                    map.put("千", matcher.group(6));
                    map.put("百", matcher.group(7));
                    map.put("十", matcher.group(8));
                    map.put("个", matcher.group(9));
                }
                return map;
            }
        }
        return map;
    }

    public static Map<String, String> matchRuleGroup1(String text, List<String> pruleList, List<String> nruleList) {
        Map<String, String> map = new HashMap<>();
        for (String rule : nruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return map;
            }
        }
        for (String rule : pruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                map.put("rule", rule);
                map.put("text", matcher.group(1));
                return map;
            }
        }
        return map;
    }

    public static Map<String, String> matchRule(String text, List<String> ruleList) {
        Map<String, String> map = new HashMap<>();
        for (String rule : ruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                map.put("rule", rule);
                map.put("text", matcher.group());
                return map;
            }
        }
        return map;
    }

    public static boolean matchRulesBool(String text, List<String> ruleList) {
        for (String rule : ruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static String matchText(String text, List<String> ruleList, int part) {
        for (String rule : ruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String ret = "";
                try {
                    ret = matcher.group(part);
                }catch (Exception e){
                    ret = matcher.group();
                }
                return ret;
            }
        }
        return null;
    }

    public static String matchText(String text, String rule, int part) {
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String ret = "";
            try {
                ret = matcher.group(part);
            }catch (Exception e){
                ret = matcher.group();
            }
            return ret;
        }
        return null;
    }

    public static Map<String, String> matchMoney(String text, LinkedHashMap<String, String> ruleList) {
        double sum=0.0;
        Map<String, String> result = new HashMap<>();
        List<String> targetList = new ArrayList<>();
        for (String rule : ruleList.keySet()) {
            double ratio = Double.parseDouble(ruleList.get(rule));
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                double value = Double.parseDouble(matcher.group(1));
                targetList.add(matcher.group(1));
                sum += value*ratio;
                text = text.replace(matcher.group(), "");
            }
        }
        if (sum > 0.001) {
            result.put("value", String.valueOf((new Double(sum)).intValue()));
            result.put("text", targetList.toString());
            return result;
        }
        return null;
    }


    public static boolean matchRule(String text, String rule) {
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

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

    public static boolean IsMatch(String text, String rule) {
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

    public static int countMatchText(String text, List<String> ruleList) {
        int count = 0;
        for (String rule : ruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String target = matcher.group();
                text = text.replace(target, "");
                count = count + 1;
            }
        }
        return count;
    }

    public static List<String> matchRules(String text, List<String> ruleList) {
        List<String> list = new ArrayList<>();
        if (ruleList!=null&&ruleList.size()>0) {
            for (String rule : ruleList) {
                Pattern pattern = Pattern.compile(rule);
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    list.add(rule);
                }
            }
        }
        return list;
    }


    public static List<String> matchText(String text, List<String> ruleList) {
        List<String> list = new ArrayList<>();
        if (ruleList!=null&&ruleList.size()>0) {
            for (String rule : ruleList) {
                Pattern pattern = Pattern.compile(rule);
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    String target = matcher.group();
                    text = text.replace(target, "");
                    list.add(target);
                }
            }
        }
        return list;
    }

    public static boolean matchBool(String text, List<String> ruleList) {
        if (ruleList!=null&&ruleList.size()>0) {
            for (String rule : ruleList) {
                Pattern pattern = Pattern.compile(rule);
                Matcher matcher = pattern.matcher(text);
                if (matcher.find()) {
                    String target = matcher.group();
                    return true;
                }
            }
        }
        return false;
    }

    public static String matchLastText(String text, List<String> ruleList) {
        String target = "";
        for (String rule : ruleList) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                target = matcher.group();
                text = text.replace(target, "");
            }
        }
        return target;
    }
}
