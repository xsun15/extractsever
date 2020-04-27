package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.ExtractFeatureTools;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import com.cjbdi.core.extractcenter.utils.CommonTools;
import com.cjbdi.core.servercenter.utils.Tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class CourtDecision {

   LinkedHashMap features = new LinkedHashMap();


   public CourtDecision() {
      ArrayList featureNameList = GetFeatureName.run(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getCourtdecision());
      Iterator var2 = featureNameList.iterator();

      while(var2.hasNext()) {
         String feature = (String)var2.next();
         ExtractFeatureBasicConfig extractFeatureBasicConfig = new ExtractFeatureBasicConfig(feature, BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getCourtdecision());
         extractFeatureBasicConfig.setPositivepurePattern(ExtractFeatureTools.toPattern(extractFeatureBasicConfig.getPositivepurerule()));
         extractFeatureBasicConfig.setNegativepurePattern(ExtractFeatureTools.toPattern(extractFeatureBasicConfig.getNegativepurerule()));
         this.features.put(feature, extractFeatureBasicConfig);
      }

   }

   public LinkedHashMap getFeatures() {
      return this.features;
   }
}
