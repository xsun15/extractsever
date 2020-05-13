package com.cjbdi.core.extractcenter.split;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.extractcenter.model.TrialReportModel;
import com.cjbdi.core.util.CommonTools;

import java.util.*;

public class TrialReportSplit extends BasicSplit {

    private TrialReportModel trialReportModel = new TrialReportModel();
    private HashMap<Integer, HashMap<String, Object>> trialReportConfig = BeanConfigCenter.structurateConfig.getTrialReportConfig().getFeatures();

    public TrialReportModel split(String content) {
        if (trialReportConfig!=null) {
            for (int order = 1; order <= trialReportConfig.size(); order++) {
                Object object = trialReportConfig.get(order).get("rule");
                if (object!=null) {
                    List<String> ruleList1 = (List<String>) object;
                    int start = CommonTools.matchOrderIndex(content, ruleList1);
                    int end = CommonTools.rowsNumber(content);
                    if (order <=2 ) end = start;
                    else if (order < trialReportConfig.size() - 1 && order > 2) {
                        object = trialReportConfig.get(order + 1).get("rule");
                        if (object != null) {
                            List<String> ruleList2 = (List<String>) object;
                            end = CommonTools.matchOrderIndex(content, ruleList2);
                        }
                    }
                    String text = CommonTools.getRangeText(content, start, end);
                    trialReportModel.setModel(order, text);
                }
            }
            splitEvidence(trialReportModel.getJustice());
            return trialReportModel;
        }
        return null;
    }

    public void splitEvidence(String content) {
        JSONObject evidenceConfig = new JSONObject((HashMap)trialReportConfig.get(7).get("evidence"));
        List<String> rules = evidenceConfig.getObject("rule", List.class);
        int start = CommonTools.matchOrderIndex(content, rules);
        String text = CommonTools.getRangeText(content, 0, start-1);
        trialReportModel.setJustice(text);
    }
}
