package com.cjbdi.core.configurecentent.converlabel.leianv1;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.converlabel.leianv1.LeianBasicConfig;
import com.cjbdi.core.configurecentent.converlabel.leianv1.utils.LeianBasicClass;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import java.util.ArrayList;
import java.util.Iterator;

public class IllegalDetension extends LeianBasicClass {

   public IllegalDetension() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getLeianV1ConfigPlace().getIllegaldetension());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         this.features.put(feature, new LeianBasicConfig(feature, BeanFactoryConfig.configPlace.getLeianV1ConfigPlace().getIllegaldetension()));
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
