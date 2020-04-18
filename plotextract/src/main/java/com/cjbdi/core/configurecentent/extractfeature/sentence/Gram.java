package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.GramBasicConfig;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Gram {

   LinkedHashMap features = new LinkedHashMap();
   private String privatekey;


   public Gram() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getGram());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         GramBasicConfig gramBasicConfig = new GramBasicConfig(feature, BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getGram());
         this.features.put(feature, gramBasicConfig);
      }

   }

   public LinkedHashMap getFeatures() {
      return this.features;
   }
}
