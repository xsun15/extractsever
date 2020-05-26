package com.cjbdi.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.extractcenter.model.CasecauseModel;
import com.cjbdi.core.extractcenter.model.DefendantModel;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTools {

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

    public static JSONObject casePortraitToJusticePortrait(List<DefendantModel> defendantModelList) {
        JSONObject result = new JSONObject();
        if (defendantModelList!=null) {
            JSONArray caseJusticePortrait = new JSONArray();
            for (DefendantModel defendantModel : defendantModelList) {
                Map<String, CasecauseModel> casecauseModelMap = defendantModel.getCasecauseModelMap();
                JSONObject oneDefendant = new JSONObject();
                JSONArray casecauseJustice = new JSONArray();
                for (String casecause : casecauseModelMap.keySet()) {
                    CasecauseModel casecauseModel = casecauseModelMap.get(casecause);
                    JSONObject oneCase = new JSONObject();
                    oneCase.put("caseCause", casecause);
                    oneCase.put("basicContent", casecauseModel.getJustice());
                    casecauseJustice.add(oneCase);
                }
                oneDefendant.put("accusedName", defendantModel.getName());
                oneDefendant.put("contentList", casecauseJustice);
                caseJusticePortrait.add(oneDefendant);
            }
            result.put("data", caseJusticePortrait);
            result.put("status", 200);
            result.put("desc", "success");
        }
        return result;
    }

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
                    if (index<start) index=start;
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
        if (StringUtils.isNotEmpty(line)) {
            for (String exp : expPatternList) {
                Pattern pattern = Pattern.compile(exp);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return true;
                }
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

    public static Integer matchOrderIndex(String line, List<String> expPatternList) {
        if (line!=null&&!line.isEmpty() && expPatternList!=null) {
            List<String> list = Arrays.asList(line.split("\n"));
            int count = 0;
            for (String str : list) {
                str = str.trim().replaceAll("\\s+", "");
                for (String exp : expPatternList) {
                    Pattern pattern = Pattern.compile(exp);
                    Matcher matcher = pattern.matcher(str);
                    if (matcher.find()) {
                        return count;
                    }
                }
                count++;
            }
        }
        return 0;
    }

    public static Integer rowsNumber(String content) {
        if (content!=null&&!content.isEmpty()) {
            List<String> list = Arrays.asList(content.split("\n"));
            return list.size();
        }
        return 0;
    }

    public static String getRangeText(String content, int start, int end) {
        if (content!=null&& end >= start) {
            String result = "";
            List<String> list = Arrays.asList(content.split("\n"));
            if (end>=list.size()) end = list.size()-1;
            if (start<0) start = 0;
            if (end>start) end = end - 1;
            for (int i=start; i<=end; i++) {
                result += list.get(i).trim().replaceAll("\\s+", "") + "\n";
            }
            return result;
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
            rules.add("审结报告");
            rules.add("审查报告");
            rules.add("刑事判决书");
            rules.add("起诉书");
            rules.add("起诉意见书");
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

    public static Optional extractDate(String text, boolean isFirst) {
        String rule = "\\d{4}年(\\d{1,2}月)?(\\d{1,2}日)?";
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(text);
        Optional localDate = Optional.empty();

        while(matcher.find()) {
            String matchText = matcher.group();
            Optional ldt = DateTimeExtractor.extract(matchText);
            if(ldt.isPresent() && isFirst) {
                return Optional.of(((LocalDateTime)ldt.get()).toLocalDate());
            }

            if(ldt.isPresent()) {
                localDate = Optional.of(((LocalDateTime)ldt.get()).toLocalDate());
            }
        }

        return localDate;
    }

    public static boolean isJoinJusticeAccuse(String justice, String accuse) {
        if (justice!=null&&!justice.isEmpty()&&accuse!=null&&!accuse.isEmpty()) {
            // 判断justice是否包含时间，如果不包含则合并
            Optional<LocalDate> localDate= extractDate(justice, true);
            if (!localDate.isPresent()) return true;
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
            if (Math.abs(unionSet.size() - interSet.size()) > Math.ceil(unionSet.size() * 0.6)) {
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
