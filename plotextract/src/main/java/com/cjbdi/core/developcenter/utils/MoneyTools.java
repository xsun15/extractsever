package com.cjbdi.core.developcenter.utils;

import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;

import java.util.List;

public class MoneyTools {
    public static String mosic(String text, List<MoneyConfig> moneyConfigList, MoneyConfig retainMoney) {
        if (text!=null&&!text.isEmpty()&&moneyConfigList!=null&&!moneyConfigList.isEmpty()) {
            for (MoneyConfig moneyConfig : moneyConfigList) {
                if (Math.abs(moneyConfig.value-retainMoney.value) > 0.1) {
                    text = text.replaceFirst(moneyConfig.context, "333333占位符元");
                } else {
                    text = text.replaceFirst(moneyConfig.context, "666666占位符元");
                }
            }
        }
        return text;
    }


    public static String mosic(String text, MoneyConfig retainMoney, List<MoneyConfig> moneyConfigList) {
        if (text!=null&&!text.isEmpty()&&moneyConfigList!=null&&!moneyConfigList.isEmpty()) {
            String result = "";
            int offset = 0 ;
            for (int i=0; i<moneyConfigList.size(); i++) {
                MoneyConfig moneyConfig = moneyConfigList.get(i);
                if (moneyConfig.start==retainMoney.start) result += text.substring(0, moneyConfig.startRegx-offset) + CommonTools.copystr("6", 6) + "元";
                else result += text.substring(0, moneyConfig.startRegx-offset) + CommonTools.copystr("3", 6) + "元";
                text = text.substring(moneyConfig.endRegx-offset);
                offset = moneyConfig.endRegx;
            }
            result += text;
            return result;
        }
        return text;
    }
}
