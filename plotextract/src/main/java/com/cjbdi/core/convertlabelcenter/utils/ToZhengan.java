package com.cjbdi.core.convertlabelcenter.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.converlabel.sentence.utils.BasicClass;
import com.cjbdi.core.configurecentent.converlabel.utils.CalculateExpression;
import com.cjbdi.core.extractcenter.utils.IsDigit;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ToZhengan {

   public static JSONArray run(JSONArray extractResult) {
      JSONArray caseDeepPortraitTarget = new JSONArray();
      if(extractResult != null && !extractResult.isEmpty()) {
         Iterator var2 = extractResult.iterator();

         while(var2.hasNext()) {
            Object obj = var2.next();
            JSONObject defendantDeepPortrait = JSONObject.parseObject(obj.toString());
            String accusedName = defendantDeepPortrait.getString("accusedName");
            JSONObject defendantDeepPortraitTarget = new JSONObject();
            JSONArray completeFeatures = defendantDeepPortrait.getJSONArray("completeList");
            JSONArray completeFeaturesTarget = new JSONArray();
            Iterator var9 = completeFeatures.iterator();

            while(var9.hasNext()) {
               Object obj1 = var9.next();
               JSONObject percaseFetaures = JSONObject.parseObject(obj1.toString());
               JSONObject percaseFetauresTarget = new JSONObject();
               String casecause = percaseFetaures.getString("caseCause");
               String order = (String) BeanFactoryConfig.predCasecauseConfig.getCasecauseOrder().get(casecause);
               JSONArray factLists = percaseFetaures.getJSONArray("factsList");
               JSONArray factListsTarget = convert(casecause, factLists, order);
               JSONArray plotList = percaseFetaures.getJSONArray("plotList");
               JSONArray plotListTarget = convert("总则", plotList, "25");
               percaseFetauresTarget.put("caseCause", casecause);
               percaseFetauresTarget.put("factsList", factListsTarget);
               percaseFetauresTarget.put("plotList", plotListTarget);
               completeFeaturesTarget.add(percaseFetauresTarget);
            }

            defendantDeepPortraitTarget.put("accusedName", accusedName);
            defendantDeepPortraitTarget.put("completeList", completeFeaturesTarget);
            caseDeepPortraitTarget.add(defendantDeepPortraitTarget);
         }

         return caseDeepPortraitTarget;
      } else {
         return caseDeepPortraitTarget;
      }
   }

   public static JSONArray convert(String casecause, JSONArray extractList, String order) {
      JSONArray targetList = new JSONArray();

      for(int i = 0; i < BeanFactoryConfig.convertLabelConfig.getZhengan().toJson().size(); ++i) {
         JSONObject targetConfig = BeanFactoryConfig.convertLabelConfig.getZhengan().toJson().getJSONObject(i);
         if(targetConfig.containsKey(casecause)) {
            JSONArray features = ((BasicClass)targetConfig.getObject(casecause, BasicClass.class)).toJson();

            for(int j = 0; j < features.size(); ++j) {
               JSONObject targetFeature = features.getJSONObject(j);
               Iterator var9 = targetFeature.keySet().iterator();

               while(var9.hasNext()) {
                  String featureName = (String)var9.next();
                  String code = targetFeature.getJSONObject(featureName).getString("code");
                  String name = targetFeature.getJSONObject(featureName).getString("name");
                  String rule = targetFeature.getJSONObject(featureName).getString("rule");
                  ArrayList matchText = new ArrayList();
                  String targetValue = "";

                  String value;
                  for(int ruleList = 0; ruleList < extractList.size(); ++ruleList) {
                     JSONObject extractFeature = extractList.getJSONObject(ruleList);
                     value = extractFeature.getString("flag");
                     String text = extractFeature.getString("text");
                     String value1 = extractFeature.getString("value");
                     if(StringUtils.isNotEmpty(rule) && rule.contains(value) && StringUtils.isNotEmpty(value1) && value1.equals("true")) {
                        rule = rule.replaceAll(value, "true");
                        matchText.add(text);
                        targetValue = "true";
                     } else if(StringUtils.isNotEmpty(rule) && rule.contains(value)) {
                        rule = rule.replaceAll(value, "true");
                        matchText.add(text);
                        targetValue = value1;
                     }
                  }

                  rule = rule.replaceAll("or", "||").replaceAll("and", "&&");
                  List var21 = Arrays.asList(rule.split("\\s+"));
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
                     if (casecause.equals("总则")) {
                         targetList.add(convertPlot(order + code, matchText, targetValue, name));
                     } else {
                         targetList.add(convertFact(order + code, matchText, targetValue, name));
                     }
                  }
               }
            }
         }
      }

      return targetList;
   }

   private static JSONObject convertFact(String code, List matchText, String targetValue, String name) {
      JSONObject targetJson = new JSONObject();
      String text = matchText.toString().replaceAll("\\[", "").replaceAll("\\]", "");
      targetJson.put("code", code);
      targetJson.put("factsContent", text);
      targetJson.put("factsValue", targetValue);
      targetJson.put("factsName", name);
      return targetJson;
   }

   private static JSONObject convertPlot(String code, List matchText, String targetValue, String name) {
      JSONObject targetJson = new JSONObject();
      String text = matchText.toString().replaceAll("\\[", "").replaceAll("\\]", "");
      targetJson.put("code", code);
      targetJson.put("plotContent", text);
      targetJson.put("plotValue", targetValue);
      targetJson.put("plotName", name);
      return targetJson;
   }

}
