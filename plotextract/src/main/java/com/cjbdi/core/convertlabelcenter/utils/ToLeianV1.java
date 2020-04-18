package com.cjbdi.core.convertlabelcenter.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.converlabel.leianv1.utils.LeianBasicClass;
import com.cjbdi.core.configurecentent.converlabel.utils.CalculateExpression;
import com.cjbdi.core.extractcenter.utils.IsDigit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ToLeianV1 {

   public static String run(JSONArray extractResult) {
      JSONArray caseDeepPortraitTarget = new JSONArray();
      JSONObject leian = new JSONObject();
      JSONArray features = new JSONArray();
      JSONArray courtDecisions = new JSONArray();
      String casecause = "";
      Iterator var6 = extractResult.iterator();

      while(var6.hasNext()) {
         Object obj = var6.next();
         JSONObject defendantDeepPortrait = JSONObject.parseObject(obj.toString());
         String accusedName = defendantDeepPortrait.getString("accusedName");
         JSONObject defendantDeepPortraitTarget = new JSONObject();
         JSONArray completeFeatures = defendantDeepPortrait.getJSONArray("completeList");
         JSONArray completeFeaturesTarget = new JSONArray();
         Iterator var13 = completeFeatures.iterator();

         while(var13.hasNext()) {
            Object obj1 = var13.next();
            JSONObject percaseFetaures = JSONObject.parseObject(obj1.toString());
            JSONObject percaseFetauresTarget = new JSONObject();
            casecause = percaseFetaures.getString("caseCause");
            JSONArray factLists = percaseFetaures.getJSONArray("factsList");
            JSONArray factListsTarget = convert(casecause, factLists);
            JSONArray plotList = percaseFetaures.getJSONArray("plotList");
            JSONArray plotListTarget = convert("总则", plotList);
            JSONArray courtDecisionTarget = percaseFetaures.getJSONArray("courtDecision");
            percaseFetauresTarget.put("caseCause", casecause);
            percaseFetauresTarget.put("factsList", factListsTarget);
            percaseFetauresTarget.put("plotList", plotListTarget);
            completeFeaturesTarget.add(percaseFetauresTarget);
            int i;
            JSONObject jsonObject;
            if(factListsTarget != null && factListsTarget.size() > 0) {
               for(i = 0; i < factListsTarget.size(); ++i) {
                  jsonObject = new JSONObject();
                  jsonObject.put("name", factListsTarget.getJSONObject(i).getString("name"));
                  jsonObject.put("value", factListsTarget.getJSONObject(i).getString("value"));
                  jsonObject.put("text", factListsTarget.getJSONObject(i).getString("text"));
                  jsonObject.put("rule", factListsTarget.getJSONObject(i).getString("rule"));
                  jsonObject.put("type", "犯罪事实");
                  features.add(jsonObject);
               }
            }

            if(plotListTarget != null && plotListTarget.size() > 0) {
               for(i = 0; i < plotListTarget.size(); ++i) {
                  jsonObject = new JSONObject();
                  jsonObject.put("name", plotListTarget.getJSONObject(i).getString("name"));
                  jsonObject.put("value", plotListTarget.getJSONObject(i).getString("value"));
                  jsonObject.put("text", plotListTarget.getJSONObject(i).getString("text"));
                  jsonObject.put("rule", plotListTarget.getJSONObject(i).getString("rule"));
                  jsonObject.put("type", "从重情节");
                  features.add(jsonObject);
               }
            }

            if(courtDecisionTarget != null && courtDecisionTarget.size() > 0) {
               for(i = 0; i < courtDecisionTarget.size(); ++i) {
                  jsonObject = new JSONObject();
                  jsonObject.put("name", courtDecisionTarget.getJSONObject(i).getString("chiname"));
                  jsonObject.put("value", courtDecisionTarget.getJSONObject(i).getString("value"));
                  jsonObject.put("text", courtDecisionTarget.getJSONObject(i).getString("text"));
                  jsonObject.put("rule", courtDecisionTarget.getJSONObject(i).getString("usedRegx"));
                  courtDecisions.add(jsonObject);
               }
            }
         }

         defendantDeepPortraitTarget.put("accusedName", accusedName);
         defendantDeepPortraitTarget.put("completeList", completeFeaturesTarget);
         caseDeepPortraitTarget.add(defendantDeepPortraitTarget);
      }

      leian.put("casecause", casecause);
      leian.put("features", features);
      leian.put("courtDecisons", courtDecisions);
      return leian.toJSONString();
   }

   public static JSONArray convert(String casecause, JSONArray extractList) {
      JSONArray targetList = new JSONArray();

      for(int i = 0; i < BeanFactoryConfig.convertLabelConfig.getLeianV1().toJson().size(); ++i) {
         JSONObject targetConfig = BeanFactoryConfig.convertLabelConfig.getLeianV1().toJson().getJSONObject(i);
         JSONArray features = ((LeianBasicClass)targetConfig.getObject(casecause, LeianBasicClass.class)).toJson();

         for(int j = 0; j < features.size(); ++j) {
            JSONObject targetFeature = features.getJSONObject(j);
            Iterator var8 = targetFeature.keySet().iterator();

            while(var8.hasNext()) {
               String featureName = (String)var8.next();
               String name = targetFeature.getJSONObject(featureName).getString("name");
               String rule = targetFeature.getJSONObject(featureName).getString("rule");
               String usedRegx = "";
               ArrayList matchText = new ArrayList();
               String targetValue = "";

               String value;
               for(int ruleList = 0; ruleList < extractList.size(); ++ruleList) {
                  JSONObject extractFeature = extractList.getJSONObject(ruleList);
                  value = extractFeature.getString("flag");
                  String text = extractFeature.getString("text");
                  String value1 = extractFeature.getString("value");
                  String regx = extractFeature.getString("usedRegx");
                  if(StringUtils.isNotEmpty(rule) && StringUtils.isNotEmpty(value1) && rule.contains(value) && value1.equals("true")) {
                     rule = rule.replaceAll(value, "true");
                     matchText.add(text);
                     targetValue = "true";
                     usedRegx = usedRegx + regx;
                  } else if(StringUtils.isNotEmpty(rule) && rule.contains(value)) {
                     rule = rule.replaceAll(value, "true");
                     matchText.add(text);
                     targetValue = value1;
                     usedRegx = usedRegx + regx;
                  }
               }

               rule = rule.replaceAll("or", "||").replaceAll("and", "&&");
               List var21 = Arrays.asList(rule.split(" "));
               rule = "";
               Iterator var22 = var21.iterator();

               while(var22.hasNext()) {
                  value = (String)var22.next();
                  if(IsDigit.isInteger(value)) {
                     rule = rule + "false\t";
                  } else {
                     rule = rule + value;
                  }
               }

               if(CalculateExpression.run(rule)) {
                  targetList.add(convert(matchText, targetValue, name, usedRegx));
               }
            }
         }
      }

      return targetList;
   }

   private static JSONObject convert(List matchText, String targetValue, String name, String usedRegx) {
      JSONObject targetJson = new JSONObject();
      targetJson.put("text", matchText);
      targetJson.put("value", targetValue);
      targetJson.put("name", name);
      targetJson.put("rule", usedRegx);
      return targetJson;
   }
}
