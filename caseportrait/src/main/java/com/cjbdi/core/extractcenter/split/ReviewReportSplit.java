package com.cjbdi.core.extractcenter.split;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.extractcenter.model.ReviewReportModel;
import com.cjbdi.core.extractcenter.model.TrialReportModel;
import com.cjbdi.core.util.CommonTools;

import java.lang.reflect.Field;
import java.util.*;

public class ReviewReportSplit extends BasicSplit {

    private ReviewReportModel reviewReportModel = new ReviewReportModel();
    private HashMap<Integer, HashMap<String, Object>> reviewReportConfig = BeanConfigCenter.structurateConfig.getReviewReportConfig().getFeatures();

    public ReviewReportModel split(String content) {
        if (reviewReportConfig!=null) {
            int lastIndex = 0;
            for (int order = 1; order < reviewReportConfig.size(); order++) {
                Object object = reviewReportConfig.get(order).get("rule");
                if (object!=null) {
                    List<String> ruleList1 = (List<String>) object;
                    int start = CommonTools.matchOrderIndex(content, ruleList1);
                    int end = CommonTools.rowsNumber(content);
                    if (order <= 8) end = start;
                    else if (order == 9) start = lastIndex;
                    else if (order < reviewReportConfig.size() - 1 && order > 8) {
                        object = reviewReportConfig.get(order + 1).get("rule");
                        if (object != null) {
                            System.out.println(order);
                            List<String> ruleList2 = (List<String>) object;
                            end = CommonTools.matchOrderIndex(content, ruleList2);
                        }
                    }
                    String text = CommonTools.getRangeText(content, start, end);
                    reviewReportModel.setModel(order, text);
                    lastIndex = start;
                }
            }
            splitEvidence(reviewReportModel.getEvidence());
            extractDefendant(reviewReportModel.getDefendant());
            return reviewReportModel;
        }
        return null;
    }

    public void splitEvidence(String content) {
        JSONObject detailOne = new JSONObject((HashMap)reviewReportConfig.get(14).get("detailOne"));
        List<String> rules = detailOne.getObject("facts", JSONObject.class).getObject("rule", List.class);
        int start = CommonTools.matchOrderIndex(content, rules) + 1;
        int end = CommonTools.rowsNumber(content);
        String text = CommonTools.getRangeText(content, start, end);
        reviewReportModel.setFacts(text);
    }

    public void extractDefendant(String content) {
        content = content.replaceAll("犯罪嫌疑人", "");
        List<String> defendants = Arrays.asList(content.split("，|、|。|；"));
        if (defendants!=null) {
            Set<String> hashSet = new HashSet<>(defendants);
            reviewReportModel.setDefendantSet(hashSet);
        }
    }
}
