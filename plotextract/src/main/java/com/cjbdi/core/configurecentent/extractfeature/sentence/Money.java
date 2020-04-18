package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyBasicConfig;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Money {

   LinkedHashMap<String, MoneyBasicConfig> features = new LinkedHashMap();
   private String privatekey;


   public Money() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getMoney());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         MoneyBasicConfig moneyBasicConfig = new MoneyBasicConfig(feature, BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getMoney());
         this.features.put(feature, moneyBasicConfig);
      }

   }

   public LinkedHashMap<String, MoneyBasicConfig> getFeatures() {
      return this.features;
   }
}
