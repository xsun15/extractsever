package com.cjbdi.core.developcenter.utils;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyRatioBasic;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonTools {
    public static MoneyConfig max(List<MoneyConfig> moneyConfigs) {
        if (moneyConfigs!=null&&moneyConfigs.size()>0) {
            MoneyConfig maxval = moneyConfigs.get(0);
            for (MoneyConfig moneyConfig : moneyConfigs) {
                if (moneyConfig.value >= maxval.value) {
                    maxval = moneyConfig;
                }
            }
            return maxval;
        }
        return null;
    }

    public static String copystr(String str, int num) {
        String result="";
        for (int i=0; i<num;i++) {
            result = result + str;
        }
        return result;
    }

    public static double listsum(List<Double> list) {
        if (list !=null&&!list.isEmpty()) {
            double sum=0.0;
            for (double value : list) {
                sum += value;
            }
            return sum;
        }
        return 0.0;
    }

    public static Map<String, List<MoneyConfig>> combineInvalidMoney(MoneyConfig maxMoney, List<MoneyConfig> allMoney){
        Map<String, List<MoneyConfig>> result = new HashMap<>();
        List<MoneyConfig> validMoneyConfig = new ArrayList<>();
        List<MoneyConfig> invalidMoneyConfig = new ArrayList<>();
        List<Double> moneyValueList = new ArrayList<>();
        for (MoneyConfig moneyConfig: allMoney){
            //只对比最大金额小的进行组合
            if ((maxMoney.value-moneyConfig.value)>0.001) {
                moneyValueList.add(moneyConfig.value);
            }
        }
        Double[] moneyValueArray = moneyValueList.toArray(new Double[moneyValueList.size()]);
        Set<Double> moneValueSet = new HashSet<>(moneyValueList);
        List<List<Double>> equalCombines = new ArrayList<>();
        //去重
        if (moneValueSet.size() == moneyValueList.size()){
            int equalCount = 0;
            for (int i=0;i<moneyValueList.size();i++){
                List<List<Double>> combines = Combination.combine(moneyValueArray,i);
                for (List<Double> combine : combines ){
                    if ( Math.abs(CommonTools.listsum(combine) - maxMoney.value) < 0.01){
                        equalCount += 1;
                        equalCombines.add(combine);
                    }
                }
            }
            if (equalCount ==1){
                for (MoneyConfig moneyConfig : allMoney){
//                    if (Math.abs(moneyConfig.value-maxMoney.value)<0.001) {
//                        List<MoneyConfig> tmp = new ArrayList<>();
//                        tmp.add(maxMoney);
//                        result.put("总金额", tmp);
//                        continue;
//                    }
                    boolean flag = false;

                    for (double value : equalCombines.get(0)){
                        //是否在有效金额列表中
                        if (Math.abs(moneyConfig.value - value) < 0.01){
                            flag = true;
                            break;
                        }
                    }
                    if (flag == true){
                        validMoneyConfig.add(moneyConfig);
                    }else {
                        invalidMoneyConfig.add(moneyConfig);
                    }
                }
//                validMoneyConfig.add(maxMoney); 为什么？
                result.put("有效",validMoneyConfig);
                result.put("无效",invalidMoneyConfig);
                return  result;
            }
        }
        return null;
    }

    public static boolean contains(List<MoneyConfig> moneyConfigList, MoneyConfig moneyConfig) {
        if (moneyConfigList!=null&&moneyConfigList.size()>0) {
            for (MoneyConfig moneyConfig1 : moneyConfigList) {
                if (moneyConfig.compare(moneyConfig1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String moneySetence(List<MoneyConfig> list) {
        String text = "";
        if (list !=null&&!list.isEmpty()) {
            for (MoneyConfig moneyConfig : list) {
                text += moneyConfig.sentence + "\n";
            }
        }
        return text;
    }

    public static String moneyPosition(List<MoneyConfig> list) {
        String text = "";
        if (list !=null&&!list.isEmpty()) {
            for (MoneyConfig moneyConfig : list) {
                text += moneyConfig.startColor + "," + moneyConfig.endColor +  ";";
            }
        }
        return text;
    }

    public static String moneyPosition(MoneyConfig moneyConfig) {
        String text = "";
        if (moneyConfig !=null) {
            text += moneyConfig.startColor + "," + moneyConfig.endColor +  ";";
        }
        return text;
    }

    public static String jointAccuseJustice(String accuse, String justice) {
        // 如果经审理查明段为空则将公诉机关指控意见给经审理查明
        if (StringUtils.isNotEmpty(accuse)) {
            if (StringUtils.isEmpty(justice)) {
                return accuse;
            } else {
                // 如果经审理查明只有另查明则，也将公诉机关指控的内容添加给经审理查明；否则判断经审理查明的另查明之前是不是有实质内容
                String regx = "^另查明";
                Pattern pattern = Pattern.compile(regx);
                Matcher matcher = pattern.matcher(justice);
                if (matcher.find()) {
                    return accuse + justice;
                } else {
                    String justiceCopySue = "";
                    String appendFactText = "";
                    boolean appendFlag = false;
                    List<String> lineList = Arrays.asList(justice.split("\n"));
                    for (String line : lineList) {
                        matcher = pattern.matcher(line);
                        if (matcher.find() || appendFlag) {
                            appendFactText += line + "\n";
                            appendFlag = true;
                        } else {
                            justiceCopySue += line + "\n";
                        }
                    }
                    if (StringUtils.isNotEmpty(justiceCopySue)) {
                        List<MoneyRatioBasic> moneyRules = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract").getMoneyRatio();
                        // 有问题
                        List<MoneyConfig> allMoney = new ArrayList<>();
                        if (allMoney!=null&&allMoney.size()>0) {
                            return justice;
                        } else {
                            return accuse + "\n" + appendFactText;
                        }
                    }
                }
            }
        }
        return justice;
    }
}
