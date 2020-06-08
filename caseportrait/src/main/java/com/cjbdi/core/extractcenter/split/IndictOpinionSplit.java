package com.cjbdi.core.extractcenter.split;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.extractcenter.model.IndictOpinionModel;
import com.cjbdi.core.util.CommonTools;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * 起诉意见书
 * */
public class IndictOpinionSplit extends BasicSplit {
    private IndictOpinionModel indictOpinionModel = new IndictOpinionModel();
    private HashMap<Integer, HashMap<String, Object>> indicitOpinionReportConfig = BeanConfigCenter.structurateConfig.getIndicitOpinionConfig().getFeatures();

    public IndictOpinionModel split(String content) {
        if (indicitOpinionReportConfig!=null) {
            int lastIndex = 0;
            for (int order = 1; order < indicitOpinionReportConfig.size(); order++) {
                Object object = indicitOpinionReportConfig.get(order).get("rule");
                if (object!=null) {
                    List<String> ruleList1 = (List<String>) object;
                    int start = CommonTools.matchOrderIndex(content, ruleList1);
                    int end = CommonTools.rowsNumber(content);
                    if (order <= 2) end = start;
                    else if (order <= indicitOpinionReportConfig.size() - 1 && order > 2) {
                        object = indicitOpinionReportConfig.get(order + 1).get("rule");
                        if (object != null) {
                            System.out.println(order);
                            List<String> ruleList2 = (List<String>) object;
                            end = CommonTools.matchOrderIndex(content, ruleList2);
                        }
                    }
                    String text = CommonTools.getRangeText(content, start, end);
                    indictOpinionModel.setModel(order, text);
                }
            }
            if (StringUtils.isNotEmpty(indictOpinionModel.getInvestigate())) indictOpinionModel.setInvestigate(indictOpinionModel.getInvestigate().replaceAll("null", ""));
            splitEvidence(indictOpinionModel.getInvestigate());
            return indictOpinionModel;
        }
        return null;
    }

    public void splitEvidence(String content) {
        JSONObject evidenceConfig = new JSONObject((HashMap)indicitOpinionReportConfig.get(4).get("evidence"));
        List<String> rules = evidenceConfig.getObject("rule", List.class);
        int start = CommonTools.matchOrderIndex(content, rules);
        String text = CommonTools.getRangeText(content, 0, start-1);
        if (StringUtils.isNotEmpty(text)) indictOpinionModel.setInvestigate(text);
    }
}
