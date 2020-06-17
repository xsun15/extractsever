package com.cjbdi.core.configcenter.configplace.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.utils.CommonTools;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class SelfSentenceConfigPlace {
   JSONObject casecauseFeature = new JSONObject();


   public SelfSentenceConfigPlace(HashMap<String, HashMap<String, Object>> configPlace) {
      String rootpath = configPlace.get("place").get("path").toString();
      String source = rootpath + configPlace.get("convertLabel").get("selfSentence").toString();
      List<String> fileList = CommonTools.getFileName(source);
      if (fileList != null) {
         String cut = ".yml";
         for (String filename : fileList) {
            String casecause = CommonTools.cutString(filename, cut);
            String path = new File(source, filename).toString();
            casecauseFeature.put(casecause, path);
         }
      }
   }

   public JSONObject getCasecauseFeature() {
      return casecauseFeature;
   }

   public void setCasecauseFeature(JSONObject casecauseFeature) {
      this.casecauseFeature = casecauseFeature;
   }
}
