package com.cjbdi.core.configurecentent.casecause;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class PredCasecauseConfig {

   LinkedHashMap<String, BasicConfig> casecause = new LinkedHashMap();


   public PredCasecauseConfig() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getOthersConfigPlace().getCasecause());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         BasicConfig basicConfig = new BasicConfig(feature, BeanFactoryConfig.configPlace.getOthersConfigPlace().getCasecause());
         this.casecause.put(feature, basicConfig);
      }

   }

   public LinkedHashMap getCasecause() {
      return this.casecause;
   }

   public Map<String, String> getCasecauseName() {
      HashMap map = new HashMap();
      Iterator var2 = this.casecause.keySet().iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         BasicConfig basicConfig = this.casecause.get(feature);
         map.put(basicConfig.getName(), basicConfig.getEname());
      }

      return map;
   }

   public Map getCasecauseOrder() {
      HashMap map = new HashMap();
      Iterator var2 = this.casecause.keySet().iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         BasicConfig basicConfig = this.casecause.get(feature);
         map.put(basicConfig.getName(), basicConfig.getOrder());
      }

      return map;
   }
}
