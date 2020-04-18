package com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IllegalPossessionDrugs;
import com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IllegalPossessionDrugExtractor extends LabelExtractor {

   private IllegalPossessionDrugs illegalPossessionDrugs;
   private InitExtractor initExtractor = new InitExtractor();


   public IllegalPossessionDrugExtractor(IllegalPossessionDrugs illegalPossessionDrugs) {
      this.modelUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictfeaturermdl();
      this.basicPureRuleExtracts = this.initExtractor.getBasicPureRuleExtractors();
      this.basicModelRuleExtracts = this.initExtractor.getBasicModelRuleExtractors();
      this.illegalPossessionDrugs = illegalPossessionDrugs;
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList labelList = new ArrayList();
      Iterator var4 = this.initExtractor.basicPrivateExtractors.iterator();

      while(var4.hasNext()) {
         BasicSentenceFeatureClass basicSentenceFeatureClass = (BasicSentenceFeatureClass)var4.next();
         Label label = basicSentenceFeatureClass.run(defendantModel, casecauseModel, this.illegalPossessionDrugs);
         if(label != null) {
            labelList.add(label);
         }

         List labels = basicSentenceFeatureClass.runlabels(defendantModel, casecauseModel, this.illegalPossessionDrugs);
         if(labels != null) {
            labelList.addAll(labels);
         }
      }

      return labelList;
   }
}
