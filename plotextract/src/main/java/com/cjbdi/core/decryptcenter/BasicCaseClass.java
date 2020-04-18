package com.cjbdi.core.decryptcenter;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.ExtractFeatureTools;
import com.cjbdi.core.configurecentent.utils.DecryptRSA;
import com.cjbdi.core.configurecentent.utils.GetFeatureName;
import com.cjbdi.core.decryptcenter.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class BasicCaseClass extends PrivateKey {

   protected LinkedHashMap<String, ExtractFeatureBasicConfig> features = new LinkedHashMap();


   public void init(String casecause) {
      String place = (String)BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getCasecauseLevel().get(casecause);
      Iterator var4;
      String feature;
      if(BeanFactoryConfig.configPlace.getEnDecryptConfigPlace().getEncrypt()) {
         List featureNameList = Arrays.asList(place.split("/"));
         var4 = featureNameList.iterator();

         while(var4.hasNext()) {
            feature = (String)var4.next();
            if(feature.contains("yml")) {
               place = BeanFactoryConfig.configPlace.getEnDecryptConfigPlace().getLxfeature() + feature;
               break;
            }
         }
      }

      ArrayList featureNameList1 = GetFeatureName.run(place);
      var4 = featureNameList1.iterator();

      while(var4.hasNext()) {
         feature = (String)var4.next();
         ExtractFeatureBasicConfig extractFeatureBasicConfig = new ExtractFeatureBasicConfig(feature, place);
         if(BeanFactoryConfig.configPlace.getEnDecryptConfigPlace().getEncrypt()) {
            extractFeatureBasicConfig.setPositivepurerule(DecryptRSA.run(extractFeatureBasicConfig.getPositivepurerule(), this.privateKey));
            extractFeatureBasicConfig.setPositivemodelrule(DecryptRSA.run(extractFeatureBasicConfig.getPositivemodelrule(), this.privateKey));
            extractFeatureBasicConfig.setNegativepurerule(DecryptRSA.run(extractFeatureBasicConfig.getNegativepurerule(), this.privateKey));
            extractFeatureBasicConfig.setNegativemodelrule(DecryptRSA.run(extractFeatureBasicConfig.getNegativemodelrule(), this.privateKey));
            extractFeatureBasicConfig.setPositivepurePattern(ExtractFeatureTools.toPattern(extractFeatureBasicConfig.getPositivepurerule()));
            extractFeatureBasicConfig.setPositivemodelPattern(ExtractFeatureTools.toPattern(extractFeatureBasicConfig.getPositivemodelrule()));
            extractFeatureBasicConfig.setNegativepurePattern(ExtractFeatureTools.toPattern(extractFeatureBasicConfig.getNegativepurerule()));
            extractFeatureBasicConfig.setNegativemodelPattern(ExtractFeatureTools.toPattern(extractFeatureBasicConfig.getNegativemodelrule()));
         }

         String code = extractFeatureBasicConfig.getCode();
         this.features.put(code, extractFeatureBasicConfig);
      }

   }

   public LinkedHashMap<String, ExtractFeatureBasicConfig> getFeatures() {
      return this.features;
   }
}
