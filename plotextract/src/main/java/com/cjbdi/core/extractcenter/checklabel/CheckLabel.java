package com.cjbdi.core.extractcenter.checklabel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.checklabel.CheckLabelBasicConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class CheckLabel {

   public static JSONArray run(JSONArray extractResult) {
      JSONArray caseDeepPortraitTarget = new JSONArray();
      new ArrayList();
      Iterator var3 = extractResult.iterator();

      while(var3.hasNext()) {
         Object obj = var3.next();
         JSONObject defendantDeepPortrait = JSONObject.parseObject(obj.toString());
         String accusedName = defendantDeepPortrait.getString("accusedName");
         JSONObject defendantDeepPortraitTarget = new JSONObject();
         JSONArray completeFeatures = defendantDeepPortrait.getJSONArray("completeList");
         JSONArray completeFeaturesTarget = new JSONArray();
         Iterator var10 = completeFeatures.iterator();

         while(var10.hasNext()) {
            Object obj1 = var10.next();
            JSONObject percaseFetaures = JSONObject.parseObject(obj1.toString());
            JSONObject percaseFetauresTarget = new JSONObject();
            String casecause = percaseFetaures.getString("caseCause");
            JSONArray factLists = percaseFetaures.getJSONArray("factsList");
            JSONArray factListsTarget = check(casecause, factLists);
            JSONArray plotList = percaseFetaures.getJSONArray("plotList");
            JSONArray plotListTarget = check("总则", plotList);
            JSONArray courtDecisionLists = percaseFetaures.getJSONArray("courtDecision");
            percaseFetauresTarget.put("caseCause", casecause);
            percaseFetauresTarget.put("factsList", factListsTarget);
            percaseFetauresTarget.put("plotList", plotListTarget);
            percaseFetauresTarget.put("courtDecision", courtDecisionLists);
            completeFeaturesTarget.add(percaseFetauresTarget);
         }

         defendantDeepPortraitTarget.put("accusedName", accusedName);
         defendantDeepPortraitTarget.put("completeList", completeFeaturesTarget);
         caseDeepPortraitTarget.add(defendantDeepPortraitTarget);
      }

      return caseDeepPortraitTarget;
   }

   public static JSONArray check(String casecause, JSONArray factLists) {
      JSONArray saveFactLists = new JSONArray();
      LinkedHashMap checkRuleList = BeanFactoryConfig.checkLabelConfig.getFeatures();
      Iterator var4 = checkRuleList.keySet().iterator();

      while(var4.hasNext()) {
         String feature = (String)var4.next();
         CheckLabelBasicConfig checkLabelBasicConfig = (CheckLabelBasicConfig)checkRuleList.get(feature);
         String checkCasecause = checkLabelBasicConfig.getCasecause();
         if(checkCasecause.equals(casecause)) {
            String checkrule = checkLabelBasicConfig.getRule();
            ArrayList targetindex = new ArrayList();

            int saveIndex;
            for(saveIndex = 0; saveIndex < factLists.size(); ++saveIndex) {
               JSONObject priority = factLists.getJSONObject(saveIndex);
               String removeIndex = priority.getString("flag");
               if(checkrule.contains(removeIndex)) {
                  targetindex.add(Integer.valueOf(saveIndex));
               }
            }

            saveIndex = 0;
            if(targetindex != null && targetindex.size() > 1) {
               int var15 = 20;
               Iterator var16 = targetindex.iterator();

               while(var16.hasNext()) {
                  Integer k = (Integer)var16.next();
                  if(factLists.getJSONObject(k.intValue()).containsKey("prioritylevel") && var15 > Integer.parseInt(factLists.getJSONObject(k.intValue()).getString("prioritylevel"))) {
                     saveIndex = k.intValue();
                     var15 = Integer.parseInt(factLists.getJSONObject(k.intValue()).getString("prioritylevel"));
                  }
               }

               ArrayList var17 = new ArrayList();
               Iterator var18 = targetindex.iterator();

               while(var18.hasNext()) {
                  int k1 = ((Integer)var18.next()).intValue();
                  if(k1 != saveIndex) {
                     var17.add(Integer.valueOf(k1));
                  }
               }

               for(int var19 = 0; var19 < factLists.size(); ++var19) {
                  if(var17.isEmpty() || !var17.contains(Integer.valueOf(var19))) {
                     saveFactLists.add(factLists.getJSONObject(var19));
                  }
               }

               return saveFactLists;
            }
         }
      }

      return factLists;
   }
}
