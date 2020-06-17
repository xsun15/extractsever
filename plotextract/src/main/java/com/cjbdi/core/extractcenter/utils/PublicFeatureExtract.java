package com.cjbdi.core.extractcenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.extractcenter.utils.tracesource.MatchModel;
import com.cjbdi.core.utils.CommonTools;
import com.cjbdi.core.utils.HttpRequest;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

public class PublicFeatureExtract {

   public static List<Label> extractPublic(CasecauseModel casecauseModel) {
      String caseId = CommonTools.getCaseId(casecauseModel.getCasecause());
      if (StringUtils.isEmpty(caseId)) return null;
      // 获取案由情节树
      JSONObject featureList = BeanFactoryConfig.extractFeatureConfig.getFeature().getJSONObject(caseId);
      List<Label> labelList = new ArrayList<>();
      for (String featureId : featureList.keySet()) {
         ExtractFeatureModel extractFeatureModel = featureList.getObject(featureId, ExtractFeatureModel.class);
         Label label = extractPublicPureRule(extractFeatureModel, casecauseModel, caseId, featureId);
         if (label!=null) labelList.add(label);
         label = extractPublicModelRule(extractFeatureModel, casecauseModel, caseId, featureId);
         if (label!=null) labelList.add(label);
      }
      return labelList;
   }

   public static Label extractPublicModelRule( ExtractFeatureModel extractFeatureModel, CasecauseModel casecauseModel, String caseId, String featureId) {
      if (extractFeatureModel.getType().equals("共有模型正则")) {
         List<Pattern> positivePattern = extractFeatureModel.getPositiveModelRule();
         List<Pattern> negativePattern = extractFeatureModel.getNegativeModelRule();
         // 抽取本院认为段
         String paraName = "opinion";
         Integer priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
         Label label = extractRule(casecauseModel.getOpinion(), positivePattern, negativePattern, casecauseModel, extractFeatureModel, caseId, featureId, paraName, priorityLevel);
         if (label!=null) return label;
         // 抽取经审理查明段
         paraName = "justice";
         priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
         label = extractModel(casecauseModel.getJustice(), positivePattern, negativePattern, casecauseModel, extractFeatureModel, caseId, featureId, paraName, priorityLevel);
         if (label!=null) return label;
      }
      return null;
   }

   public static Label extractPublicPureRule( ExtractFeatureModel extractFeatureModel, CasecauseModel casecauseModel, String caseId, String featureId) {
      if (extractFeatureModel.getType().equals("共有纯正则")) {
         List<Pattern> positivePattern = extractFeatureModel.getPositivePureRule();
         List<Pattern> negativePattern = extractFeatureModel.getNegativePureRule();
         // 抽取本院认为段
         String paraName = "opinion";
         Integer priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
         Label label = extractRule(casecauseModel.getOpinion(), positivePattern, negativePattern, casecauseModel, extractFeatureModel, caseId, featureId, paraName, priorityLevel);
         if (label!=null) return label;
         // 抽取经审理查明段
         paraName = "justice";
         priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
         label = extractRule(casecauseModel.getJustice(), positivePattern, negativePattern, casecauseModel, extractFeatureModel, caseId, featureId, paraName, priorityLevel);
         if (label!=null) return label;
      }
      return null;
   }

   public static Label extractRule(String content, List<Pattern> positiveRule, List<Pattern> negativeRule, CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel, String caseId, String featureId,
                            String paraName, Integer priorityLevel) {
      MatchModel matchModel = CommonTools.matchModel(content, positiveRule, negativeRule);
      if (matchModel!=null) {
         Label label = new Label();
         label.setCaseCause(casecauseModel.getCasecause());
         label.setChiName(extractFeatureModel.getName());
         label.setCode(caseId + featureId);
         label.setValue("true");
         matchModel.setParaName(paraName);
         matchModel.setPriorityLevel(priorityLevel);
         matchModel.setParaText(content);
         label.addMatchModel(matchModel);
         return label;
      }
      return null;
   }

   public static Label extractModel(String content, List<Pattern> positiveRule, List<Pattern> negativeRule, CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel, String caseId, String featureId,
                             String paraName, Integer priorityLevel) {
      Boolean isExit = CommonTools.isMatchPattern(content, negativeRule);
      if (!isExit) {
         JSONObject reqPara = new JSONObject();
         reqPara.put("fullText", content);
         String modelUrl = BeanFactoryConfig.interfaceUrl.getServiceUrl().getString(extractFeatureModel.getModelUrl());
         String result = HttpRequest.sendPost(modelUrl, reqPara);
         if (result.equalsIgnoreCase("true")) {
            MatchModel matchModel = CommonTools.matchModel(content, positiveRule);
            Label label = new Label();
            label.setCaseCause(casecauseModel.getCasecause());
            label.setChiName(extractFeatureModel.getName());
            label.setCode(caseId + featureId);
            label.setValue("true");
            matchModel.setParaName(paraName);
            matchModel.setPriorityLevel(priorityLevel);
            label.addMatchModel(matchModel);
            return label;
         }
      }
      return null;
   }
}
