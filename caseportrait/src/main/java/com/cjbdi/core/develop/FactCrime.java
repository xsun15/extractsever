package com.cjbdi.core.develop;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.util.CommonTools;

import java.util.Arrays;
import java.util.List;

public class FactCrime {
    public static JSONObject run(String text) {
        JSONObject jsonObject = new JSONObject();
        List<String> textList = Arrays.asList(text.split("\n"));
        boolean iscrime = false;
        if (textList!=null&&!textList.isEmpty()) {
            int headerStart = 0;
            int tailStart = textList.size();
            for (int i=0; i<textList.size(); i++) {
                if (CommonTools.ismatch(textList.get(i), BeanConfigCenter.structurateConfig.getFactSplitConfig().getFactSplit().getCansize()) && !iscrime) {
                    headerStart = i;
                    iscrime = true;
                }
                if (iscrime&&!CommonTools.ismatch(textList.get(i), BeanConfigCenter.structurateConfig.getFactSplitConfig().getFactSplit().getCansize())) {
                    tailStart = i;
                    break;
                }
            }
            if (iscrime&&headerStart<3) {
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
