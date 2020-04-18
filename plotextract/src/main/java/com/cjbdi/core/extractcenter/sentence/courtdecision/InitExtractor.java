package com.cjbdi.core.extractcenter.sentence.courtdecision;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class InitExtractor {

   LinkedHashMap basicPureRuleExtractors = new LinkedHashMap();


   public InitExtractor() {
      Iterator var1 = BeanFactoryConfig.extractFeatureConfig.getCourtDecision().getFeatures().keySet().iterator();

      while(var1.hasNext()) {
         String feature = (String)var1.next();
         String code = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getCourtDecision().getFeatures().get(feature)).getCode();
         String name = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getCourtDecision().getFeatures().get(feature)).getName();
         List positivePureRule = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getCourtDecision().getFeatures().get(feature)).getPositivepurePattern();
         List negativePureRule = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getCourtDecision().getFeatures().get(feature)).getNegativepurePattern();
         PublicFeatureExtract publicFeaturePureRuleExtract = new PublicFeatureExtract(positivePureRule, negativePureRule, code, name);
         this.basicPureRuleExtractors.put(feature, publicFeaturePureRuleExtract);
      }

   }
}
