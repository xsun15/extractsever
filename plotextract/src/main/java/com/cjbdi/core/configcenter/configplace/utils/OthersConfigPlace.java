package com.cjbdi.core.configcenter.configplace.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class OthersConfigPlace {
   private JSONObject others = new JSONObject();

   public OthersConfigPlace(HashMap<String, HashMap<String, Object>> configPlace) {
      String rootpath = configPlace.get("place").get("path").toString();
      others.put("time", rootpath + configPlace.get("others").get("time").toString());
      others.put("interfaceUrl", rootpath + configPlace.get("others").get("interfaceUrl").toString());
      others.put("factSplit", rootpath + configPlace.get("others").get("factSplit").toString());
      others.put("caseCause", rootpath + configPlace.get("others").get("caseCause").toString());
      others.put("priorityLevel", rootpath + configPlace.get("others").get("priorityLevel").toString());
   }

   public JSONObject getOthers() {
      return others;
   }

   public void setOthers(JSONObject others) {
      this.others = others;
   }
}
