package com.cjbdi.core.configcenter.configplace.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.utils.CommonTools;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class LeianConfigPlace {
   private JSONObject caseCauseFeature = new JSONObject();


   public LeianConfigPlace(HashMap<String, HashMap<String, Object>> configPlace) {
      String rootpath = configPlace.get("place").get("path").toString();
      String source = rootpath + configPlace.get("convertLabel").get("leiAn").toString();
      List<String> fileList = CommonTools.getFileName(source);
      if (fileList!=null) {
         String cut = ".yml";
         for (String filename : fileList) {
            String casecause = CommonTools.cutString(filename, cut);
            String path = new File(source, filename).toString();
            caseCauseFeature.put(casecause, path);
         }
      }
   }
   public JSONObject getCaseCauseFeature() {
      return caseCauseFeature;
   }

   public void setCaseCauseFeature(JSONObject caseCauseFeature) {
      this.caseCauseFeature = caseCauseFeature;
   }
}
