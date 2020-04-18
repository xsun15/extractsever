package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class InitBasicExtractor {

   public LinkedHashMap basicPureRuleExtractors = new LinkedHashMap();
   public LinkedHashMap basicModelRuleExtractors = new LinkedHashMap();
   public List basicPrivateExtractors = new ArrayList();


   public void init(String casecause) {
      LinkedHashMap featuresMap = ((BasicCaseClass)BeanFactoryConfig.extractFeatureConfig.getCasecause().get(casecause)).getFeatures();
      Iterator var3 = featuresMap.keySet().iterator();

      while(var3.hasNext()) {
         String feature = (String)var3.next();
         String type = ((ExtractFeatureBasicConfig)featuresMap.get(feature)).getType();
         String name = ((ExtractFeatureBasicConfig)featuresMap.get(feature)).getName();
         String code = ((ExtractFeatureBasicConfig)featuresMap.get(feature)).getCode();
         List positivePureRule = ((ExtractFeatureBasicConfig)featuresMap.get(feature)).getPositivepurePattern();
         List negativePureRule = ((ExtractFeatureBasicConfig)featuresMap.get(feature)).getNegativepurePattern();
         PublicFeatureExtract publicFeaturePureRuleExtract;
         if(type.equals("共有纯正则")) {
            publicFeaturePureRuleExtract = new PublicFeatureExtract(positivePureRule, negativePureRule, code, name);
            this.basicPureRuleExtractors.put(code, publicFeaturePureRuleExtract);
         } else if(type.equals("共有模型正则")) {
            publicFeaturePureRuleExtract = new PublicFeatureExtract(positivePureRule, negativePureRule, code, name);
            List positiveModelRule = ((ExtractFeatureBasicConfig)featuresMap.get(feature)).getPositivemodelPattern();
            List negativeModelRule = ((ExtractFeatureBasicConfig)featuresMap.get(feature)).getNegativemodelPattern();
            PublicFeatureExtract publicFeatureModelRuleExtract = new PublicFeatureExtract(positiveModelRule, negativeModelRule, code, name);
            ArrayList publicFeatureExtractList = new ArrayList();
            publicFeatureExtractList.add(publicFeaturePureRuleExtract);
            publicFeatureExtractList.add(publicFeatureModelRuleExtract);
            this.basicModelRuleExtractors.put(code, publicFeatureExtractList);
         }
      }

   }

   public LinkedHashMap getBasicPureRuleExtractors() {
      return this.basicPureRuleExtractors;
   }

   public LinkedHashMap getBasicModelRuleExtractors() {
      return this.basicModelRuleExtractors;
   }

   public List getBasicPrivateExtractors() {
      return this.basicPrivateExtractors;
   }
}
