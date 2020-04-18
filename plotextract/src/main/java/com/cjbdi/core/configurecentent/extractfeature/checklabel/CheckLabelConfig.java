package com.cjbdi.core.configurecentent.extractfeature.checklabel;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.checklabel.CheckLabelBasicConfig;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class CheckLabelConfig {

   LinkedHashMap features = new LinkedHashMap();
   private String privatekey;


   public CheckLabelConfig() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getCheckLabelConfigPlace().getSentence());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         CheckLabelBasicConfig checkLabelBasicConfig = new CheckLabelBasicConfig(feature, BeanFactoryConfig.configPlace.getCheckLabelConfigPlace().getSentence());
         this.features.put(feature, checkLabelBasicConfig);
      }

   }

   public LinkedHashMap getFeatures() {
      return this.features;
   }
}
