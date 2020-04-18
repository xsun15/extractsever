package com.cjbdi.core.developcenter.factcrime;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.ldet.configurecentent.BeanFactoryConfig;
import com.cjbdi.ldet.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.ldet.extractcenter.utils.MatchRule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FactCrime {
    public static JSONObject run(String text) {
        JSONObject jsonObject = new JSONObject();
        List<String> textList = Arrays.asList(text.split("\n"));
        boolean iscrime = false;
        if (textList!=null&&!textList.isEmpty()) {
            int headerStart = 0;
            int tailStart = textList.size();
            int count = 0;
            if (textList.size()==1) {
                return null;
            }
            for (int i=0; i<textList.size(); i++) {
                if (MatchRule.IsMatch(textList.get(i), BeanFactoryConfig.factTextConfig.getCansize()) && count <2 && !iscrime) {
                    headerStart = i;
                    iscrime = true;
                }
                if (iscrime&&!MatchRule.IsMatch(textList.get(i), BeanFactoryConfig.factTextConfig.getCansize()) && count>textList.size()-3) {
                    tailStart = i;
                    break;
                }
                count++;
            }
            if (iscrime&&headerStart<3&&headerStart>=0&&tailStart<textList.size()) {
                for (int i = 0; i < headerStart; i++) {
                    jsonObject.put(textList.get(i), "0");
                }
                for (int i = headerStart; i < tailStart; i++) {
                    jsonObject.put(textList.get(i), "1");
                }
                for (int i = tailStart; i < textList.size(); i++) {
                    jsonObject.put(textList.get(i), "0");
                }
            }
        }
        return jsonObject;
    }
}
