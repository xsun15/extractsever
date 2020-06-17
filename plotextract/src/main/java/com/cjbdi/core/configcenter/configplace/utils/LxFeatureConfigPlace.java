package com.cjbdi.core.configcenter.configplace.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.utils.CommonTools;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class LxFeatureConfigPlace {
   private JSONObject feature = new JSONObject();
   private JSONObject money = new JSONObject();
   private JSONObject gram = new JSONObject();


   public LxFeatureConfigPlace(HashMap<String, HashMap<String, Object>> configPlace) {
      String rootpath = configPlace.get("place").get("path").toString();
      // 共有情节配置表
      String source = rootpath + configPlace.get("lxFeature").get("common").toString();
      List<String> commonList = CommonTools.getFileName(source);
      if (commonList!=null) {
         String cut = ".yml";
         for (String filename : commonList) {
            String casecause = CommonTools.cutString(filename, cut);
            String path = new File(source, filename).toString();
            feature.put(casecause, path);
         }
      }
      // 案由情节配置表
      source = rootpath + configPlace.get("lxFeature").get("feature").toString();
      List<String> featureList = CommonTools.getFileName(source);
      if (featureList!=null) {
         String cut = ".yml";
         for (String filename : featureList) {
            String casecause = CommonTools.cutString(filename, cut);
            String path = new File(source, filename).toString();
            feature.put(casecause, path);
         }
      }
      // 金额配置表
      source = rootpath + configPlace.get("lxFeature").get("money").toString();
      List<String> moneyList = CommonTools.getFileName(source);
      if (moneyList!=null) {
         String cut = ".yml";
         for (String filename : moneyList) {
            String casecause = CommonTools.cutString(filename, cut);
            String path = new File(source, filename).toString();
            money.put(casecause, path);
         }
      }
      // 毒品克数配置表
      source = rootpath + configPlace.get("lxFeature").get("gram").toString();
      List<String> gramList = CommonTools.getFileName(source);
      if (gramList!=null) {
         String cut = ".yml";
         for (String filename : gramList) {
            String casecause = CommonTools.cutString(filename, cut);
            String path = new File(source, filename).toString();
            gram.put(casecause, path);
         }
      }
   }

   public JSONObject getFeature() {
      return feature;
   }

   public void setFeature(JSONObject feature) {
      this.feature = feature;
   }

   public JSONObject getMoney() {
      return money;
   }

   public void setMoney(JSONObject money) {
      this.money = money;
   }

   public JSONObject getGram() {
      return gram;
   }

   public void setGram(JSONObject gram) {
      this.gram = gram;
   }
}
