package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.CommonBasicConfig;
import com.cjbdi.core.configurecentent.utils.DecryptRSA;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Common {

   LinkedHashMap<String, CommonBasicConfig> features = new LinkedHashMap();


   public Common() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getCommon());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         CommonBasicConfig commonBasicConfig = new CommonBasicConfig(feature, BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getCommon());
         this.features.put(feature, commonBasicConfig);
      }

   }

   public LinkedHashMap<String, CommonBasicConfig> getFeatures() {
      return this.features;
   }
}
