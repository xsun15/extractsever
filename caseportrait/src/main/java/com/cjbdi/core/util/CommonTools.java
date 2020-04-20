package com.cjbdi.core.util;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.extractconfig.province.ProvinceBasicConfig;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTools {
    public static LinkedHashMap<String, Integer> splitByCasecause(String line , List<String> casecauseList) {
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        if (StringUtils.isNotEmpty(line)&&!casecauseList.isEmpty()) {
            for (String casecause : casecauseList) {
                String placeholder = casecause;
                if (casecause.contains("掩饰")) placeholder = "掩饰";
                if (casecause.contains("走私、贩卖、运输、制造毒品罪")) placeholder = "毒品";
                if (line.contains(placeholder)) {
                    result.put(casecause, line.indexOf(placeholder));
                }
            }
        }
        return result;
    }

    public static boolean findDefendant(String line , int index, String defendant , List<Integer> indexList, Set<String> defendantSet) {
        if (StringUtils.isNotEmpty(line)&&!indexList.isEmpty()) {
            for (int i=indexList.size()-1; i>=0; i--) {
                if (index>=indexList.get(i)) {
                    int start = 0;
                    if (i-1>=0) start = indexList.get(i-1);
                    String target = line.substring(start, index);
                    if (target.contains(defendant)) {
                        return true;
                    } else if (isContain(target, defendantSet)) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public static boolean ismatch(String line, List<String> expPatternList) {
        for (String exp : expPatternList) {
            Pattern pattern = Pattern.compile(exp);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static boolean ismatch(String line, String rule) {
        if (line!=null&&!line.isEmpty()) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static String matchText(String line, List<String> expPatternList) {
        if (line!=null&&!line.isEmpty()) {
            List<String> list = Arrays.asList(line.split("\n"));
            for (String str : list) {
                for (String exp : expPatternList) {
                    Pattern pattern = Pattern.compile(exp);
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        return str;
                    }
                }
            }
        }
        return "";
    }
    public static String matchText(String text, String rule, int part) {
        if(text != null && !text.isEmpty() && rule != null && !rule.isEmpty()) {
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
                String ret = "";

                try {
                    ret = matcher.group(part);
                } catch (Exception var7) {
                    ret = matcher.group();
                }

                return ret;
            }
        }

        return null;
    }


    public static String extractProvince(String line) {
        if (line!=null&&!line.isEmpty()) {
            LinkedHashMap<String, ProvinceBasicConfig> features = BeanConfigCenter.extractConfig.getProvinceConfig().getProvince();
            for (String code : features.keySet()) {
                List<String> ruleList = features.get(code).getRule();
                String name = features.get(code).getName();
                if (ruleList!=null&&!ruleList.isEmpty()) {
                    for (String rule : ruleList) {
                        Pattern pattern = Pattern.compile(rule);
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            return name;
                        }
                    }
                }
            }
        }
        return "";
    }

    public static boolean isContain(String line, List<String> list) {
        for (String word : list) {
            if (line.contains(word)) return true;
        }
        return false;
    }

    public static String containCasecause(String line, List<String> list) {
        for (String word : list) {
            if (line.contains(word.replaceAll("罪", "")))
                return word;
        }
        return "";
    }


    public static boolean isContain(String line, Set<String> list) {
        for (String word : list) {
            if (line.contains(word)) return true;
        }
        return false;
    }

    public static List<String> replaceHolder(List<String> list, String placeholder) {
        if (list!=null) {
            for (int i = 0; i < list.size(); i++) {
                list.set(i, list.get(i).replaceAll("placeholder", placeholder));
            }
        }
        return list;
    }

    public static String extractDocType(String fullText) {
        if (StringUtils.isNotEmpty(fullText)) {
            List<String> rules = new ArrayList<>();
            rules.add("起诉书");
            rules.add("刑事判决书");
            rules.add("审理报告");
            List<String> factTextList = Arrays.asList(fullText.split("\n"));
            for (String line : factTextList) {
                for (String rule : rules) {
                    if (MatchRule.matchRule(line, rule)) {
                        return rule;
                    }
                }
            }
        }
        return "";
    }

    public static Map<String, String> reviseFactEvidence(String factText, String evidence) {
        // 探测证据段开头是不是以(一)、一等开头的，如果是并且不包含证实、陈述等字样则认为是一个犯罪事实
        List<String> regxList = new ArrayList<>();
        regxList.add("^(经)?审理查明[（，：:]{1,2}[0-9一二三四五六七八九十]{1,2}[^年月日0-9一二三四五六七八九十].{60,3000}");
        regxList.add("^(经)?审查查明[（，：:]{1,2}[0-9一二三四五六七八九十]{1,2}[^年月日0-9一二三四五六七八九十].{60,3000}");
        regxList.add("^另查(明)?[（，：:]{1,2}[0-9一二三四五六七八九十]{1,2}[^年月日0-9一二三四五六七八九十].{60,3000}");
        regxList.add("^（[0-9一二三四五六七八九十]{1,2}）、?[^年月日].{60,3000}");
        regxList.add("^（[0-9一二三四五六七八九十]{1,2}）、?[^年月日0-9一二三四五六七八九十].{0,10}(交通肇事|故意伤害|强奸|非法拘禁|抢劫|盗窃|诈骗|抢夺|职务侵占|敲诈勒索|妨害公务|聚众斗殴|寻衅滋事|掩饰|隐瞒|毒品|招摇撞骗).{0,10}$");
        regxList.add("^[0-9一二三四五六七八九十]{1,2}、?[^年月日0-9一二三四五六七八九十].{60,3000}");
        regxList.add("^[0-9一二三四五六七八九十]{1,2}、?[^年月日0-9一二三四五六七八九十].{0,10}(交通肇事|故意伤害|强奸|非法拘禁|抢劫|盗窃|诈骗|抢夺|职务侵占|敲诈勒索|妨害公务|聚众斗殴|寻衅滋事|掩饰|隐瞒|毒品|招摇撞骗).{0,10}$");
        regxList.add("^.{0,15}公诉机关指控[（，：:]{1,2}[0-9一二三四五六七八九十]{1,2}[^年月日0-9一二三四五六七八九十].{60,3000}");
        regxList.add("^.{0,15}检察院指控[（，：:]{1,2}[0-9一二三四五六七八九十]{1,2}[^年月日0-9一二三四五六七八九十].{60,3000}");
        regxList.add("^\\d{1,4}年.{60,3000}");
        String negativeWords = "证实,陈述,庭审,笔录,辩解,证人,证言,供述,鉴定意见,登记表,决定书,证明,供认,辩称,答辩,辩护人,";
        List<String> negativeWordList = Arrays.asList(negativeWords.split(","));
        if (StringUtils.isNotEmpty(evidence)) {
            String evidenceSlim = "";
            List<String> evidenceList = Arrays.asList(evidence.split("\n"));
            for (String line : evidenceList) {
                boolean flag = false;
                for (String rule : regxList) {
                    Pattern pattern = Pattern.compile(rule);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find() && !CommonTools.isContain(line, negativeWordList)) {
                        factText += line + "\n";
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    evidenceSlim += line + "\n";
                }
            }
            Map<String, String> result = new HashMap<>();
            result.put("factText", factText);
            result.put("evidence", evidenceSlim);
            return result;
        }
        return null;
    }

    public static boolean isJoinJusticeAccuse(String justice, String accuse) {
        if (justice!=null&&!justice.isEmpty()&&accuse!=null&&!accuse.isEmpty()) {
            Set<String> justiceSet = new HashSet<>();
            justiceSet.addAll(Arrays.asList(justice.trim().split("")));
            Set<String> accuseSet = new HashSet<>();
            if (accuse.contains("认为"))
                accuse = accuse.substring(0, accuse.indexOf("认为"));
            accuseSet.addAll(Arrays.asList(accuse.trim().split("")));
            // 交集
            Set<String> interSet = new HashSet<>();
            interSet.addAll(justiceSet);
            interSet.retainAll(accuseSet);
            // 并集
            Set<String> unionSet = new HashSet<>();
            unionSet.addAll(justiceSet);
            unionSet.addAll(accuseSet);
            if (Math.abs(unionSet.size() - interSet.size()) > Math.ceil(unionSet.size() * 0.7)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

	public static String list2Str(List<String> list) {
		if (list!=null&&!list.isEmpty()) {
			return list.toString().replaceAll("\\[", "").replaceAll("\\]", "");
		}
		return "";
	}
}
