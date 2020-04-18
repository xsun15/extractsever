package com.cjbdi.core.developcenter.money;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyRatioBasic;
import com.cjbdi.core.developcenter.utils.CommonTools;
import com.cjbdi.core.developcenter.utils.MoneyTools;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.*;
import com.cjbdi.core.servercenter.utils.Tools;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MoneyExtractorModel extends BasicSentenceFeatureClass {

    protected List<Pattern> mustPureRule;
    protected List<Pattern> mustModelRule;
    protected List<Pattern> firstPureRuleList;
    protected List<Pattern> secondPureRuleList;
    protected List<Pattern> thirdPureRuleList;
    protected List<Pattern> firstInvalidPureRuleList;
    protected List<Pattern> secondInvalidPureRuleList;
    protected List<Pattern> firstInvalidModelRuleList;
    protected List<Pattern> secondInvalidModelRuleList;
    protected List<Pattern> invalidPureRuleList;
    protected List<Pattern> validPureRuleList;
    protected List<Pattern> dockRuleList;
    protected List<Pattern> convertRuleList;
    protected List<Pattern> modelRuleList;
    protected List<MoneyRatioBasic> moneyRatioList;
    protected List<String> moneyRuleList;
    protected List<String> extractUrl;
    protected List<Pattern> invalidModelRuleList;

    public void init (String casecause, String extractUrl) {
        String ecasecause = BeanFactoryConfig.predCasecauseConfig.getCasecauseName().get(casecause);
        mustPureRule = new ArrayList<>();
        List<Pattern> list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurePattern().get(ecasecause).get("mustrule");
        if (list != null && !list.isEmpty()) {
            mustPureRule.addAll(list);
        }
        mustModelRule = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivemodelPattern().get(ecasecause).get("mustrule");
        if (list != null && !list.isEmpty()) {
            mustModelRule.addAll(list);
        }
        // 第一纯正则
        firstPureRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurePattern().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstPureRuleList.addAll(list);
        }
        // 第二正则
        secondPureRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurePattern().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondPureRuleList.addAll(list);
        }
        // 第三正则
        thirdPureRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivepurePattern().get(ecasecause).get("thirdrule");
        if (list != null && !list.isEmpty()) {
            thirdPureRuleList.addAll(list);
        }
        // 正向纯正则
        validPureRuleList = new ArrayList<>();
        validPureRuleList.addAll(firstPureRuleList);
        validPureRuleList.addAll(secondPureRuleList);
        validPureRuleList.addAll(thirdPureRuleList);
        // 第一无效正则
        firstInvalidPureRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurePattern().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstInvalidPureRuleList.addAll(list);
        }
        // 第二无效正则
        secondInvalidPureRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurePattern().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondInvalidPureRuleList.addAll(list);
        }
        // 反向纯正则
        invalidPureRuleList = new ArrayList<>();
        invalidPureRuleList.addAll(firstInvalidPureRuleList);
        invalidPureRuleList.addAll(secondInvalidPureRuleList);
        // 第一无效正则
        firstInvalidModelRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurePattern().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            firstInvalidModelRuleList.addAll(list);
        }
        // 第二无效正则
        secondInvalidModelRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurePattern().get(ecasecause).get("secondrule");
        if (list != null && !list.isEmpty()) {
            secondInvalidModelRuleList.addAll(list);
        }
        // 反向纯正则
        invalidModelRuleList = new ArrayList<>();
        invalidModelRuleList.addAll(firstInvalidModelRuleList);
        invalidModelRuleList.addAll(secondInvalidModelRuleList);
        // 反向金额正则
        dockRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getDockpurePattern().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            dockRuleList.addAll(list);
        }
        // 转换金额正则
        convertRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativemodelPattern().get(ecasecause).get("convertrule");
        if (list != null && !list.isEmpty()) {
            convertRuleList.addAll(list);
        }
        // 模型正则
        modelRuleList = new ArrayList<>();
        list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getPositivemodelPattern().get(ecasecause).get("firstrule");
        if (list != null && !list.isEmpty()) {
            modelRuleList.addAll(list);
        }
        // 金额汇率转换正则
        moneyRatioList = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract").getMoneyRatio();
        // 金额识别正则
        moneyRuleList = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getMoney();
        // 抽取地址
        this.extractUrl = new ArrayList<>();
        this.extractUrl.add(extractUrl);
    }

    public JSONObject run(DefendantModel defendantModel, CasecauseModel casecauseModel, String purpose) {
        String casecause = casecauseModel.getCasecause();
        String opinion = casecauseModel.getOpinion();
        String justice = casecauseModel.getJustice();
        ColorTextConfig colorTextConfig = new ColorTextConfig();
        JSONObject result = new JSONObject();
        init(casecause, BeanFactoryConfig.interfaceConfig.getInterfaceModel().getRecognizemoney());
        if (StringUtils.isNotEmpty(justice)) {
            justice = Tools.deleteDigitDot(justice);
            // 将经审理查明段进行切割
            FactTextConfig factTextConfig = FactTextSplit.run(justice);

            //从本院认为、经审理查明头、和经审理查明尾
            if (purpose.equals("自动标注")) {
                return extractMoneyFromOpinionHeaderTail(opinion,casecause,justice);
            }else if(purpose.equals("自动测试")){
                return extractMoneyFromBodytemp(casecause,justice);
            }else if (purpose.equals("排列组合方式生成数据")){
                return generateDataCombinationMode(opinion,justice);
            }
        }



        // 从经审理查明段主体部分抽取金额
//            if (StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
//                List<MoneyConfig> allmoney = extractMoneyUsedModel(factTextConfig.body, casecause, modelRuleList, firstInvalidModelRuleList, secondInvalidModelRuleList, convertRuleList, moneyRatioList);
//                result = extractMoneyFromJusticBody(allmoney, factTextConfig.body, justice, colorTextConfig);
//                System.out.println(result);
//                return result;
//            }
        return result;
    }

    public JSONObject generateDataCombinationMode(String opinion ,String justice){

        FactTextConfig factTextConfig = FactTextSplit.run(justice);
        if (StringUtils.isNotEmpty(factTextConfig.header)) return null;
        if (StringUtils.isNotEmpty(factTextConfig.tail)) return null;
        if (StringUtils.isNotEmpty(opinion)){

            MoneyConfig sum = onlyOneTime(opinion.split("判决如下")[0], firstPureRuleList, moneyRatioList);
            if (sum != null){
                justice=Tools.deleteDigitDot(justice);
                List<MoneyConfig> allmoney = MatchRule.matchMoney(justice,moneyRatioList);
                Map<String, List<MoneyConfig>> combineInvalidOrValid = CommonTools.combineInvalidMoney(sum,allmoney);
                if (combineInvalidOrValid != null && combineInvalidOrValid.size() > 0){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("原始文本",justice);
                    List<String> valid = new ArrayList<>();
                    for (MoneyConfig moneyConfig : combineInvalidOrValid.get("有效")){
                        valid.add(moneyConfig.sentence);
                    }
                    jsonObject.put("有效文本",valid);
                    List<String> invalid = new ArrayList<>();
                    for (MoneyConfig moneyConfig : combineInvalidOrValid.get("无效")){
                        invalid.add(moneyConfig.sentence);
                    }
                    jsonObject.put("有效文本",valid);
                    return jsonObject;

                }

            }

        }


        return null;
    }
    public JSONObject extractMoneyFromBodytemp(String casecause,String justice){
        JSONObject result = new JSONObject();
        if(StringUtils.isNotEmpty(justice)){
            FactTextConfig factTextConfig = FactTextSplit.run(justice);
            List<MoneyConfig> allmoney = MatchRule.matchMoney(factTextConfig.body, moneyRatioList);
            double value = 0.0;
            List<MoneyConfig> valid = new ArrayList<>();
            List<MoneyConfig> invalid = new ArrayList<>();
            if (allmoney != null && allmoney.size() >=1){
                for (MoneyConfig moneyConfig : allmoney){
                    if (MatchRule.matchPatternsBool(moneyConfig.sentence, firstPureRuleList)){
                        value += moneyConfig.value;
                        valid.add(moneyConfig);
                    }else{
                        invalid.add(moneyConfig);



                    }

                }
                result.put("extractContent", factTextConfig.body);
                result.put("extractMark", String.valueOf(value));
                result.put("effectText", CommonTools.moneyPosition(valid));
                result.put("invalidText", CommonTools.moneyPosition(invalid));
                result.put("extractDetail", packingMoneyTypeDetail(valid, invalid));
                result.put("dockText", "");
                return result;


            }

        }

        return result;


    }
    public JSONObject extractMoneyFromOpinionHeaderTail(String opinion, String casecause,String justice){


        JSONObject result = new JSONObject();
        FactTextConfig factTextConfig = FactTextSplit.run(justice);

        //从本院认为
        if (StringUtils.isNotEmpty(opinion)) {
            MoneyConfig effectMoney = onlyOneTime(opinion.split("判决如下")[0], firstPureRuleList, moneyRatioList);
            if (effectMoney != null && effectMoney.isaccurate.equals("精确")) {

                result.put("automark", String.valueOf(effectMoney.value));
                result.put("content", opinion);
                result.put("isaccurate", effectMoney.isaccurate);
                result.put("casecause", casecause);
                result.put("effectText", CommonTools.moneyPosition(effectMoney));
                result.put("isdelete", "0");
                if (opinion.contains("民事") && effectMoney.start > opinion.indexOf("民事")) {
                    result.put("isdelete", "1");
                    System.out.println("删除");
                }
                return result;
            }

        }
        //从经审理查明头
        if (StringUtils.isNotEmpty(factTextConfig.header)) {
            MoneyConfig effectMoney = onlyOneTime(factTextConfig.header, firstPureRuleList, moneyRatioList);
            if (effectMoney != null && effectMoney.isaccurate.equals("精确")) {
                result.put("automark", String.valueOf(effectMoney.value));
                result.put("content", justice);
                result.put("isaccurate", effectMoney.isaccurate);
                result.put("casecause", casecause);
                result.put("effectText", CommonTools.moneyPosition(effectMoney));
                result.put("isdelete", "0");
                if (opinion.contains("民事") && effectMoney.start > opinion.indexOf("民事")) {
                    result.put("isdelete", "1");
                    System.out.println("删除");
                }
                return result;
            }

        }
        //从经审理查明尾
        if (StringUtils.isNotEmpty(factTextConfig.tail)) {
            MoneyConfig effectMoney = onlyOneTime(factTextConfig.tail, firstPureRuleList, moneyRatioList);
            if (effectMoney != null && effectMoney.isaccurate.equals("精确")) {
                result.put("automark", String.valueOf(effectMoney.value));
                result.put("content", justice);
                result.put("isaccurate", effectMoney.isaccurate);
                result.put("casecause", casecause);
                result.put("effectText", CommonTools.moneyPosition(effectMoney));
                result.put("isdelete", "0");
                if (opinion.contains("民事") && effectMoney.start > opinion.indexOf("民事")) {
                    result.put("isdelete", "1");
                    System.out.println("删除");
                }
                return result;
            }

        }
        return result;
    }

    public JSONObject extractMoneyFromJusticBody(List<MoneyConfig> allmoney, String factBody, String justice, ColorTextConfig colorTextConfig) {
        JSONObject result = new JSONObject();
        // 如果金额个数大于等于３个,并且恰好排列组合的金额等于最大值，则认为最大金额为有效金额
        if (allmoney.size() >= 3) {
            if (combineEffectMoney(factBody, allmoney, colorTextConfig)) {
                double sum = 0.0;
                for (MoneyConfig moneyConfig : allmoney) {
                    sum += moneyConfig.value;
                }
                List<MoneyConfig> invalidMoney = new ArrayList<>();
                result.put("extractContent", factBody);
                result.put("extractMark", String.valueOf(sum));
                result.put("effectText", CommonTools.moneyPosition(allmoney));
                result.put("invalidText", CommonTools.moneyPosition(invalidMoney));
                result.put("extractDetail", packingMoneyTypeDetail(allmoney, invalidMoney));
                result.put("dockText", "");
                return result;
            }
        }
        // 按行抽取金额，并判断有效无效
        double sum = 0.0;
        List<MoneyConfig> effectMoney = new ArrayList<>();
        List<MoneyConfig> invalidMoney = new ArrayList<>();
        List<String> justiceList = Arrays.asList(factBody.split("\n"));
        for (int i = 0; i < justiceList.size(); i++) {
            String line = justiceList.get(i);
            List<MoneyConfig> effectMoneyConfigList = selectFixParaIndexMoneyConfigList(allmoney, i, "有效");
            List<MoneyConfig> invalidMoneyConfigList = selectFixParaIndexMoneyConfigList(allmoney, i, "无效");
            if (effectMoneyConfigList == null || effectMoneyConfigList.size() == 0) {
                sum += convertMoney(i, allmoney, colorTextConfig, effectMoney, invalidMoney);
            } else if (effectMoneyConfigList.size() == 1) {
                sum += effectMoneyConfigList.get(0).value;
                effectMoney.add(effectMoneyConfigList.get(0));
                for (MoneyConfig moneyConfig : invalidMoneyConfigList) {
                    invalidMoney.add(moneyConfig);
                }
            } else {
                sum += addContainTotalMoney(effectMoneyConfigList, line, colorTextConfig, effectMoney, invalidMoney);
                for (MoneyConfig moneyConfig : invalidMoneyConfigList) {
                    invalidMoney.add(moneyConfig);
                }
            }
        }
        result.put("extractContent", factBody);
        result.put("extractMark", String.valueOf(sum));
        result.put("effectText", CommonTools.moneyPosition(effectMoney));
        result.put("invalidText", CommonTools.moneyPosition(invalidMoney));
        result.put("extractDetail", packingMoneyTypeDetail(allmoney, invalidMoney));
        result.put("dockText", "");
        return result;
    }

    public double addContainTotalMoney(List<MoneyConfig> lineMoneyConfigList, String content, ColorTextConfig colorTextConfig,
                                       List<MoneyConfig> effectMoney, List<MoneyConfig> invalidMoney) {
        double sum = 0.0;
        // 找到一段中对该段内容进行总结的钱
        MoneyConfig totalMoney = extractTotalMoneyPattern(lineMoneyConfigList, secondPureRuleList, moneyRatioList);
        if (totalMoney == null) {
            sum += sumMoney(lineMoneyConfigList, colorTextConfig, effectMoney);
        } else if (isTotalMoney(totalMoney, lineMoneyConfigList)) {
            sum += totalMoney.value;
            colorTextConfig.effectText += totalMoney.startColor + "," + totalMoney.endColor+ ";";
            effectMoney.add(totalMoney);
            for (MoneyConfig moneyConfig : lineMoneyConfigList) {
                colorTextConfig.invalidText += moneyConfig.startColor + "," + moneyConfig.endColor+ ";";
                invalidMoney.add(moneyConfig);
            }
        } else {
            String sentence = totalMoney.sentence;
            Segment segment = HanLP.newSegment();
            List<Term> termList = segment.seg(sentence);
            String connectword = "";
            for (Term term : termList) {
                if (term.nature.toString().equals("cc") || term.nature.toString().equals("c")) {
                    connectword += term.word + "|";
                }
            }
            if (StringUtils.isNotEmpty(connectword)) {
                connectword = connectword.substring(0, connectword.length() - 1);
            }
            boolean flagfront = false;
            List<String> list1 = new ArrayList<>();
            if (StringUtils.isNotEmpty(connectword)) {
                list1 = Arrays.asList(sentence.split(connectword));
            }
            if (list1.isEmpty()) {
                list1.add(sentence);
            }
            for (String str : list1) {
                if (str.contains(totalMoney.context)) {
                    if (str.contains("款物") || str.contains("财物")) continue;
                    else if (str.contains("现金") && str.contains("物品")) continue;
                    else if (str.contains("赃款") && str.contains("赃物")) continue;
                    termList = segment.seg(str);
                    String pos = "n or nz";
                    for (Term term : termList) {
                        if (pos.contains(term.nature.name()) && !MatchRule.IsMatch(term.word, "(价值|人民币)")) {
                            flagfront = true;
                        }
                    }
                    break;
                }
            }
            boolean flagtail = true;
            if (content.lastIndexOf("其中") > totalMoney.start) {
                flagtail = false;
            }
            for (MoneyConfig moneyConfig : lineMoneyConfigList) {
                if (moneyConfig.start == totalMoney.start) {
                    sum += totalMoney.value;
                    colorTextConfig.effectText += moneyConfig.startColor + "," + moneyConfig.endColor+ ";";
                    effectMoney.add(moneyConfig);
                    continue;
                }
                if (flagfront && moneyConfig.start < totalMoney.start) {
                    sum += moneyConfig.value;
                    colorTextConfig.effectText += moneyConfig.startColor + "," + moneyConfig.endColor+ ";";
                    effectMoney.add(moneyConfig);
                    continue;
                }
                if (flagtail && moneyConfig.start > totalMoney.start) {
                    sum += moneyConfig.value;
                    colorTextConfig.effectText += moneyConfig.startColor + "," + moneyConfig.endColor+ ";";
                    effectMoney.add(moneyConfig);
                    continue;
                }
                colorTextConfig.invalidTextColor += moneyConfig.startColor + "," + moneyConfig.endColor+ ";";
                invalidMoney.add(moneyConfig);
                moneyConfig.moneyType = "无效";
            }
        }
        return sum;
    }

    public boolean isTotalMoney(MoneyConfig totalMoney, List<MoneyConfig> lineMoneyConfigList) {
        if (totalMoney != null) {
            double sumt = 0.0;
            for (MoneyConfig moneyConfig : lineMoneyConfigList) {
                if (moneyConfig.start != totalMoney.start) {
                    sumt += moneyConfig.value;
                }
            }
            if (Math.abs(totalMoney.value - sumt) < 0.01) {
                return true;
            }
        }
        return false;
    }

    public double convertMoney(int i, List<MoneyConfig> allmoney, ColorTextConfig colorTextConfig, List<MoneyConfig> effectMoney, List<MoneyConfig> invalidMoney) {
        double sum = 0.0;
        List<MoneyConfig> convertMoneyList = selectFixParaIndexMoneyConfigList(allmoney, i, "无效", true);
        if (convertMoneyList!=null&&!convertMoneyList.isEmpty()) {
            for (MoneyConfig moneyConfig : convertMoneyList) {
                sum += moneyConfig.value;
                colorTextConfig.effectText += moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                effectMoney.add(moneyConfig);
            }
        }
        List<MoneyConfig> unconvertMoneyList = selectFixParaIndexMoneyConfigList(allmoney, i, "无效", false);
        if (unconvertMoneyList!=null&&!unconvertMoneyList.isEmpty()) {
            for (MoneyConfig moneyConfig : unconvertMoneyList) {
                colorTextConfig.invalidText += moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                invalidMoney.add(moneyConfig);
            }
        }
        return sum;
    }

    public boolean isOneEffectMoney(List<MoneyConfig> allmoney, String content, ColorTextConfig colorTextConfig, JSONObject result) {
        List<MoneyConfig> selectMoneyList = selectMoneyConfigList(allmoney, "有效");
        if (selectMoneyList != null && selectMoneyList.size() == 1) {
            for (MoneyConfig moneyConfig : allmoney) {
                if (moneyConfig.moneyType.equals("有效")) {
                    colorTextConfig.effectText += moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                } else if (moneyConfig.moneyType.equals("无效")) {
                    colorTextConfig.invalidText += moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                }
            }
            colorTextConfig.text = content;
            packingResult(selectMoneyList.get(0), colorTextConfig, result);
            return true;
        }
        return false;
    }

    public boolean combineEffectMoney(String text, List<MoneyConfig> allmoney, ColorTextConfig colorTextConfig) {
        if (allmoney != null && allmoney.size() > 0) {
            List<MoneyConfig> effectMoney = selectMoneyConfigList(allmoney, "有效");
            List<MoneyConfig> invalidMoney = selectMoneyConfigList(allmoney, "无效");
            MoneyConfig maxMoneyConfig = CommonTools.max(effectMoney);
            if (maxMoneyConfig != null) {
                double sum = 0.0;
                for (MoneyConfig moneyConfig : effectMoney) {
                    if (moneyConfig.start != maxMoneyConfig.start) {
                        sum += moneyConfig.value;
                    }
                }
                if (Math.abs(maxMoneyConfig.value - sum) < 0.01) {
                    for (MoneyConfig moneyConfig : effectMoney) {
                        if (moneyConfig.start != maxMoneyConfig.start) {
                            invalidMoney.add(moneyConfig);
                        }
                    }
                    colorTextConfig.effectText += maxMoneyConfig.startColor + "," + maxMoneyConfig.endColor + ";";
                    for (MoneyConfig moneyConfig : invalidMoney)
                        colorTextConfig.invalidText += moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                    colorTextConfig.text = text;
                    return true;
                }
            }
        }
        return false;
    }

    public void packingResult(List<MoneyConfig> allmoney, ColorTextConfig colorTextConfig, JSONObject result) {
        if (allmoney != null && allmoney.size() > 0) {
            double sum = 0.0;
            for (MoneyConfig moneyConfig : allmoney) {
                if (moneyConfig.moneyType.equals("有效")) {
                    sum += moneyConfig.value;
                }
            }
            ColorText colorText = new ColorText();
            String text = colorText.run(colorTextConfig);
            result.put("text", text);
            result.put("value", sum);
        }
    }

    public static List<MoneyConfig> selectFixParaIndexMoneyConfigList(List<MoneyConfig> allmoney, int index, String moneyType, boolean isconvert) {
        List<MoneyConfig> moneyConfigList = new ArrayList<>();
        if (allmoney != null && allmoney.size() > 0) {
            for (MoneyConfig moneyConfig : allmoney) {
                if (moneyConfig.paraindex == index && moneyConfig.moneyType.equals(moneyType)) {
                    if (moneyType.equals("无效")) {
                        if (moneyConfig.isconvert == isconvert) moneyConfigList.add(moneyConfig);
                    } else {
                        moneyConfigList.add(moneyConfig);
                    }
                }
            }
        }
        return moneyConfigList;
    }

    public static List<MoneyConfig> selectFixParaIndexMoneyConfigList(List<MoneyConfig> allmoney, int index, String moneyType) {
        List<MoneyConfig> moneyConfigList = new ArrayList<>();
        if (allmoney != null && allmoney.size() > 0) {
            for (MoneyConfig moneyConfig : allmoney) {
                if (moneyConfig.paraindex == index && moneyConfig.moneyType.equals(moneyType)) {
                    if (moneyType.equals("无效")) {
                        moneyConfigList.add(moneyConfig);
                    } else {
                        moneyConfigList.add(moneyConfig);
                    }
                }
            }
        }
        return moneyConfigList;
    }

    public static List<MoneyConfig> selectMoneyConfigList(List<MoneyConfig> allmoney, String moneyType) {
        List<MoneyConfig> moneyConfigList = new ArrayList<>();
        if (allmoney != null && allmoney.size() > 0) {
            for (MoneyConfig moneyConfig : allmoney) {
                if (moneyConfig.moneyType.equals(moneyType)) {
                    moneyConfigList.add(moneyConfig);
                }
            }
        }
        return moneyConfigList;
    }


    public List<MoneyConfig> extractMoneyUsedModel(String text, String casecause, List<Pattern> modelRuleList, List<Pattern> invalidRuleList,
                                                   List<Pattern> secondInvalidRuleList, List<Pattern> convertRuleList, List<MoneyRatioBasic> moneyRules) {
        Segment segment = HanLP.newSegment();
        String extractUrl = this.extractUrl.get(0);
        List<MoneyConfig> allmoney = new ArrayList<>();
        if (StringUtils.isNotEmpty(text)) {
            text = Tools.normalizing(text);
            List<String> textList = Arrays.asList(text.split("\n"));
            String lastLine = "";
            for (int i = 0; i < textList.size(); i++) {
                String line = textList.get(i);
                List<MoneyConfig> moneyConfigList = extractMoney(line, moneyRules);
                moneyConfigList = addMoneyPositionOffset(moneyConfigList, lastLine.length());
                lastLine += line + "\n";
                if (moneyConfigList != null && !moneyConfigList.isEmpty()) {
                    for (MoneyConfig moneyConfig : moneyConfigList) {
                        moneyConfig.paraindex = i;
                    }
                    allmoney.addAll(moneyConfigList);
                }
            }
            if (allmoney!=null&&!allmoney.isEmpty()) {
                String content = "";
                for (MoneyConfig moneyConfig : allmoney) {
                    content += moneyConfig.sentence + "\n";
                }
                content =content.substring(0, content.length()-1);
                List<String> contentList = Arrays.asList(content.split("\n"));
                JSONObject param = new JSONObject();
                param.put("casecause", casecause);
                param.put("content", content);
                String resultModel = HttpRequest.sendPost(extractUrl, param);
                if (resultModel!=null&&!resultModel.isEmpty()&&!resultModel.contains("W")) {
                    resultModel = resultModel.replaceAll("\\]", "").replaceAll("\\[", "").replaceAll("'", "").replaceAll(" ", "");
                    List<String> resultModelList = Arrays.asList(resultModel.split(","));
                    for (int i=0; i<resultModelList.size(); i++) {
                        if (resultModelList.get(i).equals("1")) {
                            allmoney.get(i).moneyType = "有效";
                        } else {
                            allmoney.get(i).moneyType = "无效";
                        }
                    }
                    for (int i=0; i<allmoney.size(); i++) {
                        String shortSentence = allmoney.get(i).sentence;
                        String line = allmoney.get(i).longSentence;
                        // 肯定有效、无效金额
                        if (MatchRule.IsMatchPattern(line, mustModelRule) && !MatchRule.IsMatchPattern(shortSentence, mustModelRule)) {
                            allmoney.get(i).moneyType = "无效";
                            continue;
                        }
                        // 无效金额补充判断
                        String vsentence = "" ;
                        boolean vflag = false;
                        line = line.substring(0, line.indexOf(shortSentence));
                        List<Term> termList = segment.seg(line);
                        for (Term term : termList) {
                            if (term.nature.toString().contains("v")) {
                                vflag = true;
                                vsentence = "";
                            }
                            if (vflag) {
                                vsentence += term.word;
                            }
                        }
                        vsentence += shortSentence.substring(0, shortSentence.indexOf(allmoney.get(i).context) + allmoney.get(i).context.length());
                        if (MatchRule.IsMatchPattern(shortSentence, invalidPureRuleList) || MatchRule.IsMatchPattern(vsentence, invalidRuleList)) {
                            allmoney.get(i).moneyType = "无效";
                            continue;
                        }
                        // 有效金额补充判断
                        if (MatchRule.IsMatchPattern(vsentence, validPureRuleList)) {
                            allmoney.get(i).moneyType = "有效";
                        }
                    }
                }
            }
        }
        return allmoney;
    }

    public List<MoneyConfig> extractMoneyUsedPbModel(String text, String casecause, List<Pattern> modelRuleList, List<Pattern> invalidRuleList,
                                                     List<Pattern> secondInvalidRuleList, List<Pattern> convertRuleList, List<MoneyRatioBasic> moneyRules) {
        String extractUrl = this.extractUrl.get(0);
        List<MoneyConfig> allmoney = new ArrayList<>();
        if (StringUtils.isNotEmpty(text)) {
            List<String> textList = Arrays.asList(text.split("\n"));
            String lastLine = "";
            for (int i = 0; i < textList.size(); i++) {
                String line = textList.get(i);
                List<MoneyConfig> moneyConfigList = extractMoney(line, moneyRules);
                moneyConfigList = addMoneyPositionOffset(moneyConfigList, lastLine.length());
                lastLine += line + "\n";
                if (moneyConfigList != null && !moneyConfigList.isEmpty()) {
                    for (MoneyConfig moneyConfig : moneyConfigList) {
                        moneyConfig.para = MoneyTools.mosic(line, moneyConfig, moneyConfigList);
                    }
                    allmoney.addAll(moneyConfigList);
                }
            }
            if (allmoney!=null&&!allmoney.isEmpty()) {
                String content = "";
                for (MoneyConfig moneyConfig : allmoney) {
                    content += moneyConfig.para + "\n";
                }
                content =content.substring(0, content.length()-1);
                JSONObject param = new JSONObject();
                param.put("casecause", casecause);
                param.put("content", content);
                String resultModel = HttpRequest.sendPost(extractUrl, param);
                if (resultModel!=null&&!resultModel.isEmpty()&&!resultModel.contains("W")) {
                    resultModel = resultModel.replaceAll("\\]", "").replaceAll("\\[", "").replaceAll("'", "").replaceAll(" ", "");
                    List<String> resultModelList = Arrays.asList(resultModel.split(","));
                    for (int i=0; i<resultModelList.size(); i++) {
                        if (resultModelList.get(i).equals("1")) {
                            allmoney.get(i).moneyType = "有效";
                        } else {
                            allmoney.get(i).moneyType = "无效";
                        }
                    }
                }
            }
        }
        return allmoney;
    }

    public List<MoneyConfig> extractMoneyPureRule(String text, List<String> PatternList) {
        List<MoneyConfig> allmoney = new ArrayList<>();
        if (StringUtils.isNotEmpty(text)) {
            List<String> textList = Arrays.asList(text.split("\n"));
            String lastLine = "";
            for (int i = 0; i < textList.size(); i++) {
                String line = textList.get(i);
                List<MoneyConfig> moneyConfigList = extractMoney(line, moneyRatioList);
                moneyConfigList = addMoneyPositionOffset(moneyConfigList, lastLine.length());
                lastLine += line + "\n";
                if (moneyConfigList != null && moneyConfigList.size() > 0) {
                    for (MoneyConfig moneyConfig : moneyConfigList) {
                        String mosicText = MatchRule.cleanMatchTextPattern(moneyConfig.sentence, firstInvalidPureRuleList);
                        mosicText = MatchRule.cleanMatchTextPattern(mosicText, secondInvalidPureRuleList);
                        if (MatchRule.IsMatch(mosicText, PatternList)) {
                            moneyConfig.moneyType="有效";
                        } else {
                            moneyConfig.moneyType="无效";
                            if (MatchRule.IsMatchPattern(moneyConfig.sentence, convertRuleList) && !MatchRule.matchPatternsBool(moneyConfig.sentence, secondInvalidPureRuleList)) {
                                moneyConfig.isconvert=true;
                            }
                        }
                        moneyConfig.paraindex = i;
                        moneyConfig.startColor = moneyConfig.start;
                        moneyConfig.endColor = moneyConfig.end;
                    }

                    allmoney.addAll(moneyConfigList);
                }
            }
        }
        return allmoney;
    }

    public List<MoneyConfig> extractMoneyPurePattern(String text, String casecause, List<Pattern> modelRuleList, List<Pattern> invalidRuleList,
                                                     List<Pattern> secondInvalidRuleList, List<Pattern> convertRuleList, List<MoneyRatioBasic> moneyRules) {
        List<MoneyConfig> allmoney = new ArrayList<>();
        if (StringUtils.isNotEmpty(text)) {
            List<String> textList = Arrays.asList(text.split("\n"));
            String lastLine = "";
            for (int i = 0; i < textList.size(); i++) {
                String line = textList.get(i);
                List<MoneyConfig> moneyConfigList = extractMoney(line, moneyRatioList);
                moneyConfigList = addMoneyPositionOffset(moneyConfigList, lastLine.length());
                lastLine += line + "\n";
                if (moneyConfigList != null && moneyConfigList.size() > 0) {
                    for (MoneyConfig moneyConfig : moneyConfigList) {
                        String mosicText = MatchRule.cleanMatchTextPattern(moneyConfig.sentence, firstInvalidPureRuleList);
                        mosicText = MatchRule.cleanMatchTextPattern(mosicText, secondInvalidPureRuleList);
                        if (MatchRule.IsMatchPattern(mosicText, validPureRuleList)) {
                            moneyConfig.moneyType="有效";
                        } else {
                            moneyConfig.moneyType="无效";
                            if (MatchRule.IsMatchPattern(moneyConfig.sentence, convertRuleList) && !MatchRule.matchPatternsBool(moneyConfig.sentence, secondInvalidPureRuleList)) {
                                moneyConfig.isconvert=true;
                            }
                        }
                        moneyConfig.paraindex = i;
                        moneyConfig.startColor = moneyConfig.start;
                        moneyConfig.endColor = moneyConfig.end;
                    }

                    allmoney.addAll(moneyConfigList);
                }
            }
        }
        return allmoney;
    }

    public List<MoneyConfig> extractMoneyPureRule(String text) {
        List<MoneyConfig> allmoney = new ArrayList<>();
        if (StringUtils.isNotEmpty(text)) {
            List<String> textList = Arrays.asList(text.split("\n"));
            String lastLine = "";
            for (int i = 0; i < textList.size(); i++) {
                String line = textList.get(i);
                List<MoneyConfig> moneyConfigList = extractMoney(line, moneyRatioList);
                moneyConfigList = addMoneyPositionOffset(moneyConfigList, lastLine.length());
                lastLine += line + "\n";
                if (moneyConfigList != null && moneyConfigList.size() > 0) {
                    for (MoneyConfig moneyConfig : moneyConfigList) {
                        String mosicText = MatchRule.cleanMatchTextPattern(moneyConfig.sentence, firstInvalidPureRuleList);
                        mosicText = MatchRule.cleanMatchTextPattern(mosicText, secondInvalidPureRuleList);
                        if (MatchRule.IsMatchMoney(mosicText, moneyRatioList)) {
                            moneyConfig.moneyType="有效";
                        } else {
                            moneyConfig.moneyType="无效";
                            if (MatchRule.IsMatchPattern(moneyConfig.sentence, convertRuleList) && !MatchRule.matchPatternsBool(moneyConfig.sentence, secondInvalidPureRuleList)) {
                                moneyConfig.isconvert=true;
                            }
                        }
                        moneyConfig.paraindex = i;
                        moneyConfig.startColor = moneyConfig.start;
                        moneyConfig.endColor = moneyConfig.end;
                    }
                    allmoney.addAll(moneyConfigList);
                }
            }
        }
        return allmoney;
    }

    public static MoneyConfig extractTotalMoney(List<MoneyConfig> allmoney, List<String> PatternList, List<MoneyRatioBasic> moneyRules) {
        if (allmoney != null && allmoney.size() > 0) {
            MoneyConfig maxMoneyConfig = new MoneyConfig();
            for (MoneyConfig moneyConfig : allmoney) {
                if (MatchRule.matchRulesBool(moneyConfig.sentence, PatternList)) {
                    if (moneyConfig.value - maxMoneyConfig.value > 0.0001) {
                        maxMoneyConfig = moneyConfig;
                    }
                }
            }
            if (maxMoneyConfig != null && maxMoneyConfig.value > 0.0001) {
                return maxMoneyConfig;
            }
        }
        return null;
    }

    public static MoneyConfig extractTotalMoneyPattern(List<MoneyConfig> allmoney, List<Pattern> PatternList, List<MoneyRatioBasic> moneyRules) {
        if (allmoney != null && allmoney.size() > 0) {
            MoneyConfig maxMoneyConfig = new MoneyConfig();
            for (MoneyConfig moneyConfig : allmoney) {
                if (MatchRule.matchPatternsBool(moneyConfig.sentence, PatternList)) {
                    if (moneyConfig.value - maxMoneyConfig.value > 0.0001) {
                        maxMoneyConfig = moneyConfig;
                    }
                }
            }
            if (maxMoneyConfig != null && maxMoneyConfig.value > 0.0001) {
                return maxMoneyConfig;
            }
        }
        return null;
    }

    public static List<MoneyConfig> extractMoney(String text, List<MoneyRatioBasic> moneyRules) {
        List<MoneyConfig> moneyConfigs = new ArrayList<>();
        if (StringUtils.isNotEmpty(text)) {
            moneyConfigs = MatchRule.matchMoney(text, moneyRules);
        }
        return moneyConfigs;
    }

    public double sumMoney(List<MoneyConfig> moneyConfigList, ColorTextConfig colorTextConfig, List<MoneyConfig> effectMoney) {
        double sum = 0.0;
        if (moneyConfigList != null && !moneyConfigList.isEmpty()) {
            for (MoneyConfig moneyConfig : moneyConfigList) {
                sum += moneyConfig.value;
                colorTextConfig.effectText += moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                effectMoney.add(moneyConfig);
            }
        }
        return sum;
    }

    public void packingResult(MoneyConfig moneyConfig, ColorTextConfig colorTextConfig, JSONObject result) {
        result.put("value", String.format("%.2f", moneyConfig.value));
        ColorText colorText = new ColorText();
        String text = colorText.run(colorTextConfig);
        result.put("text", text);
    }

    public void packingResult(double sum, ColorTextConfig colorTextConfig, JSONObject result) {
        ColorText colorText = new ColorText();
        String text = colorText.run(colorTextConfig);
        result.put("value", String.format("%.2f", sum));
        result.put("text", text);
    }

    public List<MoneyConfig> addMoneyPositionOffset(List<MoneyConfig> money, int offset) {
        if (money != null) {
            for (int i = 0; i < money.size(); i++) {
                MoneyConfig moneyConfig = money.get(i);
                moneyConfig.startColor += offset;
                moneyConfig.endColor += offset;
                money.set(i, moneyConfig);
            }
        }
        return money;
    }

    public static JSONObject packingMoneyTypeDetail(List<MoneyConfig> effectMoney, List<MoneyConfig> invalidMoney) {
        JSONObject result = new JSONObject();
        if (effectMoney !=null&&effectMoney.size()>0) {
            List<String> stringList = new ArrayList<>();
            for (MoneyConfig moneyConfig : effectMoney) {
                stringList.add(moneyConfig.longSentence);
            }
            result.put("有效", stringList);
        }
        if (invalidMoney!=null&&invalidMoney.size()>0) {
            List<String> stringList = new ArrayList<>();
            for (MoneyConfig moneyConfig : invalidMoney) {
                stringList.add(moneyConfig.longSentence);
            }
            result.put("无效", stringList);
        }
        return result;
    }

    public static MoneyConfig onlyOneTimeEffectTotalMoneyExpreion(String text, List<String> Patterns, List<MoneyRatioBasic> moneyRules) {
        List<MoneyConfig> moneyConfigList = MatchRule.matchMoney(text, moneyRules);
        int i = 0;
        MoneyConfig res = null;
        if (moneyConfigList!=null&&moneyConfigList.size()>=1) {
            for (MoneyConfig moneyConfig : moneyConfigList) {
                if (MatchRule.matchRulesBool(moneyConfig.sentence, Patterns)) {
                    i += 1;
                    res = moneyConfig;
                }
            }
        }
        if (i ==1){
            System.out.println("找到");
            return res;
        }
        return null;
    }

    public static MoneyConfig onlyOneTime(String text, List<Pattern> Patterns, List<MoneyRatioBasic> moneyRules) {
        List<MoneyConfig> moneyConfigList = MatchRule.matchMoney(text, moneyRules);
        int i = 0;
        MoneyConfig res = null;
        if (moneyConfigList!=null&&moneyConfigList.size()>=1) {
            for (MoneyConfig moneyConfig : moneyConfigList) {
                if (MatchRule.matchPatternsBool(moneyConfig.sentence, Patterns)) {
                    i += 1;
                    res = moneyConfig;
                }
            }
        }
        if (i ==1){
            System.out.println("找到");
            return res;
        }
        return null;
    }


}
