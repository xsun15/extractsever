package com.cjbdi.core.developcenter.money;

public class FindExtractMoneyError {
    /*private static InvalidPriceValueChecker invalidPriceValueChecker = new InvalidPriceValueChecker();

    public static Map<String, String> extract(RawCrimeInfo rawCrimeInfo, String targetPart) {

        String casecause = rawCrimeInfo.defendant.casecause;
        String ecasecause = "";
        for (int i=0; i<BeanFactoryConfig.predCasecauseConfig.toJson().size(); i++) {
            if (BeanFactoryConfig.predCasecauseConfig.toJsonClass().getObject(i, BasicConfig.class).getName().equals(casecause)){
                ecasecause = BeanFactoryConfig.predCasecauseConfig.toJsonClass().getObject(i, BasicConfig.class).getEname();
                break;
            }
        }
        String defendant = rawCrimeInfo.defendant.name;
        Set<String> defendants = rawCrimeInfo.defendant.allDenfendant.get();
        List<String> ruleList = new ArrayList<>();
        ruleList.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("thirdrule"));
        ruleList.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("thirdrule"));
        List<String>secondRuleList = new ArrayList<>();
        List<String> list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        LinkedHashMap<String, String> moneyRules = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract").getRule();
        List<String> invalidMoneyRules = new ArrayList<>();
        invalidMoneyRules.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get(ecasecause).get("firstrule"));
        invalidMoneyRules.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get("default").get("firstrule"));

        if (targetPart.equals("经审理查明分段标注详细")) {
            Map<String, String> result = new HashMap<>();
            double sum=0.0;
            Segment segment = HanLP.newSegment().enableNameRecognize(true);
            String factText = rawCrimeInfo.defendant.factText.get();
            List<String> factList = Arrays.asList(factText.split("\n"));
            for (String eachfactText : factList) {
                String rawtext = eachfactText;
                eachfactText = eachfactText.replaceAll("\\s+", "");
                eachfactText = deleteCommaBetweenMoney(eachfactText);
                eachfactText = invalidPriceValueChecker.parseInvalidSumValueStr(casecause, eachfactText, defendant,defendants);
                List<MoneyConfig> allMoney = extractMoneyValuePosition(eachfactText, moneyRules);
                MoneyConfig totalMoney = extractTotalMoney(eachfactText, secondRuleList, moneyRules);
                if (totalMoney != null) {
                    double sumtmp = 0.0;
                    for (MoneyConfig moneyConfig1 : allMoney) {
                        if (moneyConfig1.start!=totalMoney.start) {
                            sumtmp += moneyConfig1.value;
                        }
                    }
                    if (Math.abs(totalMoney.value-sumtmp)>0.01) {
                        List<Term> termList = segment.seg(totalMoney.context);
                        String connectword = "";
                        for (Term term : termList) {
                            if (term.nature.toString().equals("cc")||term.nature.toString().equals("c")) {
                                connectword += term.word + "|";
                            }
                        }
                        if (StringUtils.isNotEmpty(connectword)) {
                            connectword=connectword.substring(0,connectword.length()-1);
                        }
                        boolean flagfront=false;
                        List<String> list1 = new ArrayList<>();
                        if (StringUtils.isNotEmpty(connectword)) {
                            list1 = Arrays.asList(totalMoney.context.split(connectword));
                        }
                        if (list1.isEmpty()) {
                            list1.add(totalMoney.context);
                        }
                        for (String str : list1) {
                            if (str.contains(totalMoney.target)) {
                                termList = segment.seg(str);
                                for (Term term : termList) {
                                    if (term.nature.toString().equals("n")&&!MatchRule.matchRule(term.word, "(元|人民币|美元|美金|卢布|澳元|澳门币|欧元|日元|韩币|价值)")) {
                                        flagfront=true;
                                    }
                                }
                            }
                        }
                        boolean flagtail=true;
                        if (eachfactText.indexOf("其中")>totalMoney.start) {
                            flagtail=false;
                        }
                        for (MoneyConfig moneyConfig1 : allMoney) {
                            if (moneyConfig1.start==totalMoney.start) {
                                sum += totalMoney.value;
                            }
                            if (flagfront&&moneyConfig1.start<totalMoney.start){
                                sum += moneyConfig1.value;
                            }
                            if (flagtail&&moneyConfig1.start>totalMoney.start) {
                                sum += moneyConfig1.value;
                            }
                        }
                    } else {
                        sum += totalMoney.value;
                    }
                    result.put("提取数额", String.valueOf(sum));
                    result.put("犯罪事实", rawtext);
                    return result;
                }
            }
        }
        return null;
    }

    public static MoneyConfig extractTotalMoney(String text, List<String> ruleList, LinkedHashMap<String, String> moneyRules) {
        String targetText = MatchRule.matchText(text, ruleList, 2);
        if (StringUtils.isNotEmpty(targetText)) {
            MoneyConfig moneyConfig = MatchRule.matchTotalMoney(text, targetText, moneyRules);
            if (moneyConfig != null) {
                return moneyConfig;
            }
        }
        return null;
    }

    public static List<MoneyConfig> extractMoneyValuePosition(String text, LinkedHashMap<String, String> moneyRules) {
        if (StringUtils.isNotEmpty(text)) {
            List<MoneyConfig> money = MatchRule.matchMoneyPosition(text, moneyRules);
            if (money != null && !money.isEmpty()) {
                return money;
            }
        }
        return null;
    }

    public static String deleteCommaBetweenMoney(String string){
        string = string.replaceAll(" ","");

        String regxExp = "\\d[，,]\\d";
        Pattern pattern = Pattern.compile(regxExp);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()){
            String matchedString = matcher.group();
            String matchedString1 = matchedString.replace("，","");
            matchedString1 = matchedString1.replace(",","");
            string = string.replaceAll(matchedString,matchedString1);
        }
        return string;

    }

    public static Map<String,String> oneTimeMoneyExpreion(String text, String casecause, String defendant, Set<String> defendants,
                                                          List<String> ruleList, LinkedHashMap<String, String> moneyRules) {
        text = invalidPriceValueChecker.parseInvalidSumValueStr(casecause, text, defendant,defendants);
        int matchNum = MatchRule.countMatchText(text, BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getMoney());
        if (matchNum == 1) {
            String targetText = MatchRule.matchText(text, ruleList, 1);
            Map<String, String> money = MatchRule.matchMoney(targetText, moneyRules);
            if (money != null && !money.isEmpty()) {
                return money;
            }
        }
        return null;
    }

    public static Map<String,String> extractSummaryMoney(String text, String casecause, String defendant, Set<String> defendants,
                                                         List<String> ruleList, LinkedHashMap<String, String> moneyRules) {
        text = invalidPriceValueChecker.parseInvalidSumValueStr(casecause, text, defendant,defendants);
        String targetText = MatchRule.matchText(text, ruleList, 2);
        if (StringUtils.isNotEmpty(targetText)) {
            Map<String, String> money = MatchRule.matchMoney(targetText, moneyRules);
            if (money != null && !money.isEmpty()) {
                return money;
            }
        }
        return null;
    }

    public static Map<String,String> extractSingleMoneyAndSum(String text, String casecause, String defendant, Set<String> defendants, LinkedHashMap<String, String> moneyRules) {
        text = invalidPriceValueChecker.parseInvalidSumValueStr(casecause, text, defendant,defendants);
        if (StringUtils.isNotEmpty(text)) {
            Map<String, String> money = MatchRule.matchMoney(text, moneyRules);
            if (money != null && !money.isEmpty()) {
                return money;
            }
        }
        return null;
    }


    public static Map<String, String> generateData(String text, List<String> moneyPosition, List<String> effectiveMoneyPosition, List<String> invalidMoneyPosition) {
        Map<String, String> result = new HashMap<>();
        if (effectiveMoneyPosition!=null&&!effectiveMoneyPosition.isEmpty()) {
            for (String targetpos : effectiveMoneyPosition) {
                StringBuilder line = new StringBuilder(text);
                String content = "";
                int flash = 0;
                for (String pos : moneyPosition) {
                    if (!pos.equals(targetpos)) {
                        int start = Integer.parseInt(pos.split("-")[0]) - flash;
                        int end = Integer.parseInt(pos.split("-")[1]) - flash;
                        line = line.replace(start, end, CommonTools.copystr("x", end - start));
                    } else {
                        int start = Integer.parseInt(pos.split("-")[0]);
                        int end = Integer.parseInt(pos.split("-")[1]);
                        flash += end - start - 3;
                        line = line.replace(start, end, "666");
                    }
                }
                content = "" + line;
                result.put(content, "有效");
            }
        }
        if (invalidMoneyPosition!=null&&!invalidMoneyPosition.isEmpty()) {
            for (String targetpos : invalidMoneyPosition) {
                StringBuilder line = new StringBuilder(text);
                String content = "";
                int flash = 0;
                for (String pos : moneyPosition) {
                    if (!pos.equals(targetpos)) {
                        int start = Integer.parseInt(pos.split("-")[0]) - flash;
                        int end = Integer.parseInt(pos.split("-")[1]) - flash;
                        line = line.replace(start, end, CommonTools.copystr("x", end - start));
                    } else {
                        int start = Integer.parseInt(pos.split("-")[0]);
                        int end = Integer.parseInt(pos.split("-")[1]);
                        flash += end - start - 3;
                        line = line.replace(start, end, "666");
                    }
                }
                content = "" + line;
                result.put(content, "无效");
            }
        }
        return result;
    }

    public static List<Integer> findEffectiveMoney(List<String> moneyvalue, String value) {
        int[] moneyarray = CommonTools.liststr2listint(moneyvalue);
        for (int i=moneyvalue.size(); i>=1; i--) {
            List<List<Integer>> selectList = Combination.combine(moneyarray, i);
            if (i==moneyvalue.size()) {
                selectList.remove(1);
            }
            for (List<Integer>  onecomb : selectList) {
                if (onecomb.size()>1) {
                    int value1 = CommonTools.listintsum(onecomb);
                    int value2 = (new Double(value)).intValue();
                    if (Math.abs(value1 - value2) < 0.01) {
                        return onecomb;
                    }
                }
            }
        }
        return  null;
    }
*/
}
