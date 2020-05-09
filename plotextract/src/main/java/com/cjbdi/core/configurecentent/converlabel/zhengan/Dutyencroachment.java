package com.cjbdi.core.configurecentent.converlabel.zhengan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.converlabel.BasicConfig;
import com.cjbdi.core.configurecentent.converlabel.sentence.utils.BasicClass;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;

import java.util.ArrayList;
import java.util.Iterator;

public class Dutyencroachment extends BasicClass {

   public Dutyencroachment() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getZhenganConfigPlace().getDutyencroachment());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         this.features.put(feature, new BasicConfig(feature, BeanFactoryConfig.configPlace.getZhenganConfigPlace().getDutyencroachment()));
      }

   }

   public JSONArray toJson() {
      JSONArray jsonArray = new JSONArray();
      Iterator var2 = this.features.keySet().iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         JSONObject jsonObject = new JSONObject();
         jsonObject.put(key, this.features.get(key));
         jsonArray.add(jsonObject);
      }

      return jsonArray;
   }
}
