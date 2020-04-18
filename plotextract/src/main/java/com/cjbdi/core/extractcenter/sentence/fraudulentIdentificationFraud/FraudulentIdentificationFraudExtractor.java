package com.cjbdi.core.extractcenter.sentence.fraudulentIdentificationFraud;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.FraudulentIdentificationFraud;
import com.cjbdi.core.extractcenter.sentence.fraudulentIdentificationFraud.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FraudulentIdentificationFraudExtractor extends LabelExtractor {

   private FraudulentIdentificationFraud fraudulentIdentificationFraud;
   private InitExtractor initExtractor = new InitExtractor();


   public FraudulentIdentificationFraudExtractor(FraudulentIdentificationFraud fraudulentIdentificationFraud) {
      this.modelUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictfeaturermdl();
      this.basicPureRuleExtracts = this.initExtractor.getBasicPureRuleExtractors();
      this.basicModelRuleExtracts = this.initExtractor.getBasicModelRuleExtractors();
      this.fraudulentIdentificationFraud = fraudulentIdentificationFraud;
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList labelList = new ArrayList();
      Iterator var4 = this.initExtractor.basicPrivateExtractors.iterator();

      while(var4.hasNext()) {
         BasicSentenceFeatureClass basicSentenceFeatureClass = (BasicSentenceFeatureClass)var4.next();
         Label label = basicSentenceFeatureClass.run(defendantModel, casecauseModel, this.fraudulentIdentificationFraud);
         if(label != null) {
            labelList.add(label);
         }
      }

      return labelList;
   }
}
