package com.cjbdi.core.developcenter.money;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyRatioBasic;
import com.cjbdi.core.developcenter.utils.Combination;
import com.cjbdi.core.developcenter.utils.CommonTools;
import com.cjbdi.core.developcenter.utils.MoneyTools;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.sentence.common.money.utils.InvalidPriceValueChecker;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.cjbdi.core.servercenter.utils.Tools;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.apache.commons.lang.StringUtils;

import java.util.*;

import static com.cjbdi.core.developcenter.money.MoneyExtractorModel.extractTotalMoney;
import static com.cjbdi.core.developcenter.money.MoneyExtractorModel.onlyOneTimeEffectTotalMoneyExpreion;

public class MoneyExtractor {
    private static InvalidPriceValueChecker invalidPriceValueChecker = new InvalidPriceValueChecker();

    public static JSONObject combineValidInvalidAllPaper (DefendantModel defendantModel, CasecauseModel casecauseModel) {
        String defendant = defendantModel.getName();
        String casecause = casecauseModel.getCasecause();
        String ecasecause = BeanFactoryConfig.predCasecauseConfig.getCasecauseName().get(casecause);
        Set<String> defendants = defendantModel.getDefendantNameSet();
        List<String> thirdruleList = new ArrayList<>();
        thirdruleList.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("thirdrule"));
        List<String>firstRuleList = new ArrayList<>();
        List<String> list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstRuleList.addAll(list);
        }
        List<String>secondRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        List<String> invalidRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            invalidRuleList.addAll(list);
        }
        List<MoneyRatioBasic> moneyRules = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract").getMoneyRatio();
        FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
        String factHeader= factTextConfig.header;
        String factHail=factTextConfig.tail;
        String factText=factTextConfig.body;
        String conclusion=casecauseModel.getOpinion();
        List<MoneyConfig> allmoney = new ArrayList<>();
        if (factHeader!=null&&!factHeader.isEmpty()){
            factHeader= Tools.deleteDigitDot(factHeader);
        //    factHeader=invalidPriceValueChecker.parseInvalidSumValueStr(casecause,factHeader,defendant,defendants, invalidRuleList);
        }
        if (factHail!=null&&!factHail.isEmpty()){
            factHail=Tools.deleteDigitDot(factHail);
        //    factHail=invalidPriceValueChecker.parseInvalidSumValueStr(casecause,factHail,defendant,defendants, invalidRuleList);
        }
        if(conclusion!=null&&!conclusion.isEmpty()) {
            conclusion=Tools.deleteDigitDot(conclusion);
            conclusion=invalidPriceValueChecker.parseInvalidSumValueStr(casecause,conclusion,defendant,defendants, invalidRuleList);
        }
        if (factText!=null&&!factText.isEmpty()) {
            factText=Tools.deleteDigitDot(factText);
            allmoney = extractMoneyCombine(factText, moneyRules);
        }
        firstRuleList.addAll(secondRuleList);
        firstRuleList.addAll(thirdruleList);
        if (allmoney !=null && allmoney.size() >= 1) {
            List<String> rules = new ArrayList<>();
            rules.addAll(firstRuleList);
            JSONObject result = new JSONObject();
            // 本院认为段只有一次与金额相关的描述
            if (conclusion.contains("华人民共和国刑法"))
                conclusion = conclusion.substring(0, conclusion.indexOf("华人民共和国刑法"));

            MoneyConfig totalMoney = onlyOneTimeEffectTotalMoneyExpreion(conclusion, rules, moneyRules);
            if (totalMoney!=null) {
                Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(totalMoney,allmoney);
                if(combineInvalidOrValid != null && combineInvalidOrValid.size() > 0){
                    List<MoneyConfig> valids  = combineInvalidOrValid.get("有效");
                    for(MoneyConfig valid : valids){
                        if (StringUtils.isNotEmpty(valid.sentence)) {
                            result.put(MoneyTools.mosic(factText, allmoney, valid), "有效");
                        }
                    }
                    List<MoneyConfig> invalids  = combineInvalidOrValid.get("无效");
                    for(MoneyConfig invalid : invalids){
                        if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                            if (StringUtils.isNotEmpty(invalid.sentence)) {
                                result.put(MoneyTools.mosic(factText, allmoney, invalid), "无效已识别");
                            }
                        } else {
                            if (StringUtils.isNotEmpty(invalid.sentence)) {
                                result.put(MoneyTools.mosic(factText, allmoney, invalid), "无效未识别");
                            }
                        }
                    }
                    result.put("总结性金额描述", totalMoney.context);
                    result.put("段落", "本院认为");
                    return result;
                }
            }
            // 抽取经审理查明段头概括段
            if (StringUtils.isNotEmpty(factHeader)) {
                totalMoney = onlyOneTimeEffectTotalMoneyExpreion(factHeader, rules, moneyRules);
                if (totalMoney != null) {
                    Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(totalMoney,allmoney);
                    if(combineInvalidOrValid != null && combineInvalidOrValid.size() > 0){
                        List<MoneyConfig> valids  = combineInvalidOrValid.get("有效");
                        for(MoneyConfig valid : valids){
                            if (StringUtils.isNotEmpty(valid.sentence))
                                result.put(MoneyTools.mosic(factText, allmoney, valid), "有效");
                        }
                        List<MoneyConfig> invalids  = combineInvalidOrValid.get("无效");
                        for(MoneyConfig invalid : invalids){
                            if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(MoneyTools.mosic(factText, allmoney, invalid), "无效已识别");
                            } else {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(MoneyTools.mosic(factText, allmoney, invalid), "无效未识别");
                            }
                        }
                        result.put("总结性金额描述", totalMoney.context);
                        result.put("段落", "经审理查明头端");
                        return result;
                    }
                }
            }
            // 抽取经审理查明段尾概括段
            if (StringUtils.isNotEmpty(factHail)) {
                totalMoney = onlyOneTimeEffectTotalMoneyExpreion(factHail, rules, moneyRules);
                if (totalMoney != null) {
                    Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(totalMoney,allmoney);
                    if(combineInvalidOrValid != null && combineInvalidOrValid.size() > 0){
                        List<MoneyConfig> valids  = combineInvalidOrValid.get("有效");
                        for(MoneyConfig valid : valids) {
                            if (StringUtils.isNotEmpty(valid.sentence))
                                result.put(MoneyTools.mosic(factText, allmoney, valid), "有效");
                        }
                        List<MoneyConfig> invalids  = combineInvalidOrValid.get("无效");
                        for(MoneyConfig invalid : invalids){
                            if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(MoneyTools.mosic(factText, allmoney, invalid), "无效已识别");
                            }else {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(MoneyTools.mosic(factText, allmoney, invalid), "无效未识别");
                            }
                        }
                        result.put("总结性金额描述", totalMoney.context);
                        result.put("段落", "经审理查明尾端");
                        return result;
                    }
                }
            }

            if (StringUtils.isNotEmpty(factText)) {
                List<String> factList = Arrays.asList(factText.split("\n"));
                for (String line : factList) {
                    allmoney = extractMoneyCombine(line, moneyRules);
                    if (allmoney.size()>=2) {
                        MoneyConfig maxMoney = CommonTools.max(allmoney);
                        Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(maxMoney, allmoney);
                        if (combineInvalidOrValid != null && combineInvalidOrValid.size() > 0) {
                            List<MoneyConfig> valids = combineInvalidOrValid.get("有效");
                            for (MoneyConfig valid : valids) {
                                if (StringUtils.isNotEmpty(valid.sentence))
                                    result.put(valid.sentence, "有效");
                            }
                            List<MoneyConfig> invalids = combineInvalidOrValid.get("无效");
                            for (MoneyConfig invalid : invalids) {
                                if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                                    if (StringUtils.isNotEmpty(invalid.sentence))
                                        result.put(invalid.sentence, "无效已识别");
                                } else {
                                    if (StringUtils.isNotEmpty(invalid.sentence))
                                        result.put(invalid.sentence, "无效未识别");
                                }
                            }
                        }
                    }
                }
                if (result!=null&&result.size()>0) {
                    result.put("段落", "经审理查明段");
                }
                return result;
            }
        }
        return null;
    }

    public static JSONObject combineValidInvalidAll(DefendantModel defendantModel, CasecauseModel casecauseModel) {
        String defendant = defendantModel.getName();
        String casecause = casecauseModel.getCasecause();
        String ecasecause = BeanFactoryConfig.predCasecauseConfig.getCasecauseName().get(casecause);
        Set<String> defendants = defendantModel.getDefendantNameSet();
        List<String> thirdruleList = new ArrayList<>();
        thirdruleList.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("thirdrule"));
        List<String>firstRuleList = new ArrayList<>();
        List<String> list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstRuleList.addAll(list);
        }
        List<String>secondRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        List<String> invalidRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            invalidRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get("default").get("firstrule");
        if (list != null && !list.isEmpty()) {
            invalidRuleList.addAll(list);
        }
        List<MoneyRatioBasic> moneyRules = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract").getMoneyRatio();
        FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
        String factHeader= factTextConfig.header;
        String factHail=factTextConfig.tail;
        String factText=factTextConfig.body;
        String conclusion=casecauseModel.getOpinion();
        List<MoneyConfig> allmoney = new ArrayList<>();
        if (factHeader!=null&&!factHeader.isEmpty()){
            factHeader=Tools.deleteDigitDot(factHeader);
            factHeader=invalidPriceValueChecker.parseInvalidSumValueStr(casecause,factHeader,defendant,defendants, invalidRuleList);
        }
        if (factHail!=null&&!factHail.isEmpty()){
            factHail=Tools.deleteDigitDot(factHail);
            factHail=invalidPriceValueChecker.parseInvalidSumValueStr(casecause,factHail,defendant,defendants, invalidRuleList);
        }
        if(conclusion!=null&&!conclusion.isEmpty()) {
            conclusion=Tools.deleteDigitDot(conclusion);
            conclusion=invalidPriceValueChecker.parseInvalidSumValueStr(casecause,conclusion,defendant,defendants, invalidRuleList);
        }
        if (factText!=null&&!factText.isEmpty()) {
            factText=Tools.deleteDigitDot(factText);
            allmoney = extractMoneyCombine(factText, moneyRules);
        }
        List<String> rules = new ArrayList<>();
        rules.addAll(firstRuleList);
        rules.addAll(secondRuleList);
        rules.addAll(thirdruleList);
        if (allmoney !=null && allmoney.size() >= 1) {
            JSONObject result = new JSONObject();
            // 本院认为段只有一次与金额相关的描述
            if (conclusion.contains("华人民共和国刑法"))
                conclusion = conclusion.substring(0, conclusion.indexOf("华人民共和国刑法"));
            MoneyConfig totalMoney = onlyOneTimeEffectTotalMoneyExpreion(conclusion, rules, moneyRules);
            if (totalMoney!=null) {
                Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(totalMoney,allmoney);
                if(combineInvalidOrValid != null && combineInvalidOrValid.size() > 0){
                    List<MoneyConfig> valids  = combineInvalidOrValid.get("有效");
                    for(MoneyConfig valid : valids){
                        if (StringUtils.isNotEmpty(valid.sentence))
                            result.put(valid.sentence, "有效");
                    }
                    List<MoneyConfig> invalids  = combineInvalidOrValid.get("无效");
                    for(MoneyConfig invalid : invalids){
                        if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                            if (StringUtils.isNotEmpty(invalid.sentence))
                                result.put(invalid.sentence, "无效已识别");
                        } else {
                            if (StringUtils.isNotEmpty(invalid.sentence))
                                result.put(invalid.sentence, "无效未识别");
                        }
                    }
                    result.put("总结性金额描述", totalMoney.context);
                    result.put("段落", "本院认为");
                    return result;
                }
            }
            // 抽取经审理查明段头概括段
            if (StringUtils.isNotEmpty(factHeader)) {
                totalMoney = onlyOneTimeEffectTotalMoneyExpreion(factHeader, rules, moneyRules);
                if (totalMoney != null) {
                    Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(totalMoney,allmoney);
                    if(combineInvalidOrValid != null && combineInvalidOrValid.size() > 0){
                        List<MoneyConfig> valids  = combineInvalidOrValid.get("有效");
                        for(MoneyConfig valid : valids){
                            if (StringUtils.isNotEmpty(valid.sentence))
                                result.put(valid.sentence, "有效");
                        }
                        List<MoneyConfig> invalids  = combineInvalidOrValid.get("无效");
                        for(MoneyConfig invalid : invalids){
                            if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(invalid.sentence, "无效已识别");
                            } else {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(invalid.sentence, "无效未识别");
                            }
                        }
                        result.put("总结性金额描述", totalMoney.context);
                        result.put("段落", "经审理查明头端");
                        return result;
                    }
                }
            }
            // 抽取经审理查明段尾概括段
            if (StringUtils.isNotEmpty(factHail)) {
                totalMoney = onlyOneTimeEffectTotalMoneyExpreion(factHail, rules, moneyRules);
                if (totalMoney != null) {
                    Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(totalMoney,allmoney);
                    if(combineInvalidOrValid != null && combineInvalidOrValid.size() > 0){
                        List<MoneyConfig> valids  = combineInvalidOrValid.get("有效");
                        for(MoneyConfig valid : valids) {
                            if (StringUtils.isNotEmpty(valid.sentence))
                                result.put(valid.sentence, "有效");
                        }
                        List<MoneyConfig> invalids  = combineInvalidOrValid.get("无效");
                        for(MoneyConfig invalid : invalids){
                            if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(invalid.sentence, "无效已识别");
                            }else {
                                if (StringUtils.isNotEmpty(invalid.sentence))
                                    result.put(invalid.sentence, "无效未识别");
                            }
                        }
                        result.put("总结性金额描述", totalMoney.context);
                        result.put("段落", "经审理查明尾端");
                        return result;
                    }
                }
            }
            /*if (StringUtils.isNotEmpty(factText)) {
                List<String> factList = Arrays.asList(factText.split("\n"));
                for (String line : factList) {
                    allmoney = extractMoneyCombine(line, moneyRules);
                    if (allmoney.size()>=2) {
                        MoneyConfig maxMoney = CommonTools.max(allmoney);
                        Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(maxMoney, allmoney);
                        if (combineInvalidOrValid != null && combineInvalidOrValid.size() > 0) {
                            List<MoneyConfig> valids = combineInvalidOrValid.get("有效");
                            for (MoneyConfig valid : valids) {
                                if (StringUtils.isNotEmpty(valid.sentence))
                                    result.put(valid.sentence, "有效");
                            }
                            List<MoneyConfig> invalids = combineInvalidOrValid.get("无效");
                            for (MoneyConfig invalid : invalids) {
                                if (MatchRule.matchRulesBool(invalid.sentence, invalidRuleList)) {
                                    if (StringUtils.isNotEmpty(invalid.sentence))
                                        result.put(invalid.sentence, "无效已识别");
                                } else {
                                    if (StringUtils.isNotEmpty(invalid.sentence))
                                        result.put(invalid.sentence, "无效未识别");
                                }
                            }
                        }
                    }
                }
                if (result!=null&&result.size()>0) {
                    result.put("段落", "经审理查明段");
                }
                return result;
            }*/
        }
        return null;
    }

    public static JSONObject combineValidInvalidPure(DefendantModel defendantModel, CasecauseModel casecauseModel) {
        String defendant = defendantModel.getName();
        String casecause = casecauseModel.getCasecause();
        String ecasecause = BeanFactoryConfig.predCasecauseConfig.getCasecauseName().get(casecause);
        Set<String> defendants = defendantModel.getDefendantNameSet();
        List<String> thirdruleList = new ArrayList<>();
        thirdruleList.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("thirdrule"));
        thirdruleList.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("thirdrule"));
        List<String>firstRuleList = new ArrayList<>();
        List<String> list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstRuleList.addAll(list);
        }
        List<String>secondRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        List<String> invalidRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            invalidRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get("default").get("firstrule");
        if (list != null && !list.isEmpty()) {
            invalidRuleList.addAll(list);
        }
        List<MoneyRatioBasic> moneyRules = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract").getMoneyRatio();
        FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
        String factText=factTextConfig.body;
        List<MoneyConfig> allmoney = new ArrayList<>();
        JSONObject result = new JSONObject();
        if (factText!=null&&!factText.isEmpty()) {
            factText=Tools.deleteDigitDot(factText);
            List<String> factList = Arrays.asList(factText.split("\n"));
            for (String line : factList) {
                line = invalidPriceValueChecker.parseInvalidSumValueStr(casecause,line,defendant,defendants, invalidRuleList);
                allmoney = extractMoneyCombine(line, moneyRules);
                if (allmoney!=null&&allmoney.size()>=2) {
                    MoneyConfig maxMoneyConfig = extractTotalMoney(allmoney, firstRuleList, moneyRules);
                    if (maxMoneyConfig!=null) {
                        if (!MatchRule.matchRulesBool(maxMoneyConfig.sentence, invalidRuleList)) {
                            Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(maxMoneyConfig, allmoney);
                            if (combineInvalidOrValid != null && combineInvalidOrValid.size() > 0) {
                                List<MoneyConfig> invalids = combineInvalidOrValid.get("有效");
                                for (MoneyConfig invalid : invalids) {
                                    result.put(invalid.sentence, "有效");
                                }
                                List<MoneyConfig> valids = combineInvalidOrValid.get("无效");
                                for (MoneyConfig valid : valids) {
                                    result.put(valid.sentence, "无效");
                                }
                                result.put(maxMoneyConfig.sentence, "总金额");
                            }
                        }
                    }
                }
            }
            return result;
        }
        return null;
    }


    public static JSONObject extractSummaryMoney(DefendantModel defendantModel, CasecauseModel casecauseModel) {
        String defendant = defendantModel.getName();
        String casecause = casecauseModel.getCasecause();
        String ecasecause = BeanFactoryConfig.predCasecauseConfig.getCasecauseName().get(casecause);
        Set<String> defendants = defendantModel.getDefendantNameSet();
        List<String> thirdruleList = new ArrayList<>();
        thirdruleList.addAll(BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("thirdrule"));
        List<String>firstRuleList = new ArrayList<>();
        List<String> list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstRuleList.addAll(list);
        }
        List<String>secondRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurerule().get("default").get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondRuleList.addAll(list);
        }
        List<String> invalidRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            invalidRuleList.addAll(list);
        }
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get("default").get("firstrule");
        if (list != null && !list.isEmpty()) {
            invalidRuleList.addAll(list);
        }
        List<MoneyRatioBasic> moneyRules = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract").getMoneyRatio();
        FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
        String factHeader= factTextConfig.header;
        String factHail=factTextConfig.tail;
        String factText=factTextConfig.body;
        String conclusion=casecauseModel.getOpinion();
        String factTextTotal = "";
        if (factHeader!=null&&!factText.isEmpty()){
            factHeader=Tools.deleteDigitDot(factHeader);
            factTextTotal += factHeader;
        }
        if (factText!=null&&!factText.isEmpty()){
            factText=Tools.deleteDigitDot(factText);
            factTextTotal += factText;
        }
        if (factHail!=null&&!factHail.isEmpty()){
            factHail=Tools.deleteDigitDot(factHail);
            factTextTotal += factHail;
        }
        if(conclusion!=null&&!conclusion.isEmpty()) {
            conclusion=conclusion.split("判决如下")[0];
            conclusion=Tools.deleteDigitDot(conclusion);
            conclusion = invalidPriceValueChecker.parseInvalidSumValueStr(casecause,conclusion,defendant,defendants, invalidRuleList);
        }
        if (defendants.size() ==1) {
            List<String> rules = new ArrayList<>();
            rules.addAll(firstRuleList);
//            rules.addAll(secondRuleList);
            JSONObject result = new JSONObject();
            result.put("案由", casecause);
            // 本院认为段只有一次与金额相关的描述
            MoneyConfig  effectMoney = onlyOneTimeEffectTotalMoneyExpreion(conclusion, rules, moneyRules);
            if (effectMoney != null && effectMoney.isaccurate.equals("精确")) {
                result.put("automark", String.valueOf(effectMoney.value));
                result.put("content", conclusion);
                result.put("isaccurate", effectMoney.isaccurate);
                result.put("casecause", casecause);
                result.put("effectText", CommonTools.moneyPosition(effectMoney));
                result.put("isdelete", "0");
                if (conclusion.contains("民事") && effectMoney.start>conclusion.indexOf("民事")) {
                    result.put("isdelete", "1");
                    System.out.println("删除");
                }
                return result;
            }
            // 抽取经审理查明段头概括段
            effectMoney = onlyOneTimeEffectTotalMoneyExpreion(factHeader, rules, moneyRules);
            if (effectMoney != null && effectMoney.isaccurate.equals("精确")) {
                effectMoney.startColor = factTextTotal.indexOf(effectMoney.sentence);
                effectMoney.endColor = effectMoney.startColor + effectMoney.sentence.length();
                result.put("automark", String.valueOf(effectMoney.value));
                result.put("content", factTextTotal);
                result.put("isaccurate", effectMoney.isaccurate);
                result.put("casecause", casecause);
                result.put("effectText", CommonTools.moneyPosition(effectMoney));
                result.put("isdelete", "0");
                if (factHeader.contains("民事") && effectMoney.start>factHeader.indexOf("民事")) {
                    result.put("isdelete", "1");
                    System.out.println("删除");
                }
                return result;
            }
            // 抽取经审理查明段尾概括段
            effectMoney = onlyOneTimeEffectTotalMoneyExpreion(factHail, rules, moneyRules);
            if (effectMoney != null && effectMoney.isaccurate.equals("精确")) {
                result.put("automark", String.valueOf(effectMoney.value));
                result.put("content", factTextTotal);
                result.put("isaccurate", effectMoney.isaccurate);
                result.put("casecause", casecause);
                result.put("effectText", CommonTools.moneyPosition(effectMoney));
                result.put("isdelete", "0");
                if (factHail.contains("民事") && effectMoney.start>factHail.indexOf("民事")) {
                    result.put("isdelete", "1");
                    System.out.println("删除");
                }
                return result;
            }
        }
        return null;
    }


    public static String hideMoney(String content, List<MoneyConfig> allmoney, MoneyConfig moneyConfig) {
        if (StringUtils.isNotBlank(content)) {
            StringBuilder sb = new StringBuilder(content);
            for (MoneyConfig moneyConfig1 : allmoney) {
                if (moneyConfig1.start != moneyConfig.start) {
                    sb.replace(moneyConfig1.start, moneyConfig1.end, CommonTools.copystr("x", moneyConfig1.target.length()));
                }
            }
            sb.replace(moneyConfig.start, moneyConfig.end, "666666");
            return sb.toString();
        }
        return content;
    }

    public static List<MoneyConfig> addMoneyPositionOffset(List<MoneyConfig> money, int offset) {
        if (money!=null) {
            for(int i=0; i<money.size(); i++) {
                MoneyConfig moneyConfig = money.get(i);
                moneyConfig.startColor += offset;
                moneyConfig.endColor += offset;
                money.set(i, moneyConfig);
            }
        }
        return money;
    }

    public static MoneyConfig addMoneyPositionOffset(MoneyConfig money, int offset) {
        if (money!=null) {
            money.startColor += offset;
            money.endColor += offset;
        }
        return money;
    }

    public static List<MoneyConfig> extractMoneyCombine(String text, List<MoneyRatioBasic> moneyRules) {
        List<MoneyConfig> moneyConfigs = new ArrayList<>();
        if (StringUtils.isNotEmpty(text)) {
            moneyConfigs = MatchRule.matchMoney(text, moneyRules);
        }
        return moneyConfigs;
    }

    public static List<Double> findEffectiveMoney(List<Double> moneyvalue, double value) {
        Double[] moneyarray = new Double[moneyvalue.size()];
        moneyvalue.toArray(moneyarray);
        for (int i=moneyvalue.size(); i>=1; i--) {
            List<List<Double>> selectList = Combination.combine(moneyarray, i);
            if (i==moneyvalue.size()) {
                selectList.remove(1);
            }
            for (List<Double>  onecomb : selectList) {
                if (onecomb.size()>1) {
                    double value1 = CommonTools.listsum(onecomb);
                    if (Math.abs(value1 - value) < 0.01) {
                        return onecomb;
                    }
                }
            }
        }
        return  null;
    }

    public static List<Double> findInvalidMoney(List<Double> allmoney, List<Double>effectiveMoney) {
        List<Double> invalidMoney = new ArrayList<>();
        if (effectiveMoney!=null&&effectiveMoney.size()>0) {
            for (Double money : allmoney) {
                boolean iscontain = false;
                for (Double money1 : effectiveMoney) {
                    if (Math.abs(money - money1) < 0.01) {
                        iscontain = true;
                    }
                }
                if (!iscontain) {
                    invalidMoney.add(money);
                }
            }
        }
        return invalidMoney;
    }

    public static MoneyConfig findMaxMoney(List<MoneyConfig> allmoney) {
        if (allmoney!=null&&allmoney.size()>0) {
            MoneyConfig maxMoney = allmoney.get(0);
            for (MoneyConfig moneyConfig : allmoney) {
                if (moneyConfig.value>maxMoney.value) {
                    maxMoney=moneyConfig;
                }
            }
            return maxMoney;
        }
        return null;
    }

    public static JSONObject packingMoneyTypeAutomarkDetail(List<MoneyConfig> effectMoney, List<MoneyConfig> invalidMoney) {
        JSONObject result = new JSONObject();
        if (effectMoney !=null&&effectMoney.size()>0) {
            List<String> stringList = new ArrayList<>();
            for (MoneyConfig moneyConfig : effectMoney) {
                stringList.add(moneyConfig.sentence);
            }
            result.put("有效", stringList);
        }
        if (invalidMoney!=null&&invalidMoney.size()>0) {
            List<String> stringList = new ArrayList<>();
            for (MoneyConfig moneyConfig : effectMoney) {
                stringList.add(moneyConfig.sentence);
            }
            result.put("无效", stringList);
        }
        return result;
    }

    public static List<MoneyConfig> selectEffectMoney(List<MoneyConfig> allmoney, List<MoneyConfig> invalidMoney) {
        List<MoneyConfig> moneyConfigList = new ArrayList<>();
        if (invalidMoney==null||invalidMoney.size()==0) {
            return allmoney;
        } else {
            if (allmoney != null && allmoney.size() > 0) {
                for (MoneyConfig moneyConfig : allmoney) {
                    boolean iseffect = true;
                    for (MoneyConfig moneyConfig1 : invalidMoney) {
                        if (moneyConfig.start==moneyConfig1.start) {
                            iseffect=false;
                            break;
                        }
                    }
                    if (iseffect) moneyConfigList.add(moneyConfig);
                }
            } else {
                return moneyConfigList;
            }
        }
        return moneyConfigList;
    }

    public static List<MoneyConfig> findInvalidMoney(List<MoneyConfig> allMoney, String casecause) {
        List<MoneyConfig> invalidMoney = new ArrayList<>();
        if (allMoney != null && allMoney.size() > 0) {
            for (MoneyConfig moneyConfig : allMoney) {
                MultivaluedMapImpl param = new MultivaluedMapImpl();
                param.add("casecause", casecause);
                param.add("content", moneyConfig.sentence);
                String resultModel = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfaceModel().getRecognizemoney(), param);
                if (StringUtils.isNotEmpty(resultModel)) {
                    JSONObject resultModelJson = JSONObject.parseObject(resultModel);
                    String moneyType = resultModelJson.getString("简单无效金额");
                    if (moneyType.equals("0")) {
                        invalidMoney.add(moneyConfig);
                    }
                }
            }
        }
        return invalidMoney;
    }

    public static List<MoneyConfig> findInvalidMoneyUsedRule(List<MoneyConfig> allMoney, List<String> invalidRule) {
        List<MoneyConfig> invalidMoney = new ArrayList<>();
        if (allMoney != null && allMoney.size() > 0) {
            for (MoneyConfig moneyConfig : allMoney) {
                if (MatchRule.matchRulesBool(moneyConfig.longSentence,invalidRule )) {
                    moneyConfig.moneyType="无效";
                    invalidMoney.add(moneyConfig);
                }
            }
        }
        return invalidMoney;
    }

    public static String mosaicText(String text, List<MoneyConfig> invalidMoney) {
        if (invalidMoney!=null&&invalidMoney.size()>0) {
            for (MoneyConfig moneyConfig : invalidMoney) {
                text = text.substring(0, moneyConfig.startColor) + CommonTools.copystr("x", moneyConfig.sentence.length()) +
                        text.substring(moneyConfig.endColor);
            }
        }
        return text;
    }

}
