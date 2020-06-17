package com.cjbdi.core.configcenter.configplace.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class CheckLabelConfigPlace {
   private JSONObject feature = new JSONObject();


   public CheckLabelConfigPlace(HashMap<String, HashMap<String, Object>> configPlace) {
      String rootpath = configPlace.get("place").get("path").toString();
      String source = rootpath + configPlace.get("checkFeature").get("feature").toString();
      feature.put("feature", source);
   }

   public JSONObject getFeature() {
      return feature;
   }

   public void setFeature(JSONObject feature) {
      this.feature = feature;
   }
}
