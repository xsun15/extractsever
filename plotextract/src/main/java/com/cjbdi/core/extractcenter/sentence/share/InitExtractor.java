package com.cjbdi.core.extractcenter.sentence.share;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.extractcenter.sentence.share.DefendantGroup;
import com.cjbdi.core.extractcenter.sentence.share.FirstCrimeExtractor;
import com.cjbdi.core.extractcenter.sentence.share.PreviousRecordExtractor;
import com.cjbdi.core.extractcenter.sentence.share.RecidivismExtractor;
import com.cjbdi.core.extractcenter.sentence.share.RecidivistExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "总则量刑情节";
   private DefendantGroup oldercrimeExtractor;
   private DefendantGroup oldercrime65Extractor;
   private DefendantGroup oldercrime70Extractor;
   private DefendantGroup oldercrime75Extractor;
   private DefendantGroup underagecrimeExtractor;
   private DefendantGroup underagecrime16Extractor;
   private DefendantGroup underagecrime14Extractor;
   private RecidivismExtractor recidivismExtractor;
   private RecidivistExtractor recidivistExtractor;
   private PreviousRecordExtractor previousRecordExtractor;
   private FirstCrimeExtractor firstCrimeExtractor;


   public InitExtractor() {
      this.init(this.casecause);
      this.oldercrimeExtractor = new DefendantGroup((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1003"));
      this.oldercrime65Extractor = new DefendantGroup((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1004"));
      this.oldercrime70Extractor = new DefendantGroup((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1005"));
      this.oldercrime75Extractor = new DefendantGroup((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1006"));
      this.underagecrimeExtractor = new DefendantGroup((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1007"));
      this.underagecrime16Extractor = new DefendantGroup((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1008"));
      this.underagecrime14Extractor = new DefendantGroup((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1009"));
      this.recidivismExtractor = new RecidivismExtractor((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1049"));
      this.recidivistExtractor = new RecidivistExtractor((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1050"));
      this.previousRecordExtractor = new PreviousRecordExtractor((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1051"));
      this.firstCrimeExtractor = new FirstCrimeExtractor((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1052"));
   }

   public DefendantGroup getOldercrimeExtractor() {
      return this.oldercrimeExtractor;
   }

   public DefendantGroup getOldercrime65Extractor() {
      return this.oldercrime65Extractor;
   }

   public DefendantGroup getOldercrime70Extractor() {
      return this.oldercrime70Extractor;
   }

   public DefendantGroup getOldercrime75Extractor() {
      return this.oldercrime75Extractor;
   }

   public DefendantGroup getUnderagecrimeExtractor() {
      return this.underagecrimeExtractor;
   }

   public DefendantGroup getUnderagecrime16Extractor() {
      return this.underagecrime16Extractor;
   }

   public DefendantGroup getUnderagecrime14Extractor() {
      return this.underagecrime14Extractor;
   }

   public RecidivismExtractor getRecidivismExtractor() {
      return this.recidivismExtractor;
   }

   public RecidivistExtractor getRecidivistExtractor() {
      return this.recidivistExtractor;
   }

   public PreviousRecordExtractor getPreviousRecordExtractor() {
      return this.previousRecordExtractor;
   }

   public FirstCrimeExtractor getFirstCrimeExtractor() {
      return this.firstCrimeExtractor;
   }
}
