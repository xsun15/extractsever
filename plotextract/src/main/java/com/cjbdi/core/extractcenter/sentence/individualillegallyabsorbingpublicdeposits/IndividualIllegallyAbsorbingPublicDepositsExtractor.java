package com.cjbdi.core.extractcenter.sentence.individualillegallyabsorbingpublicdeposits;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IndividualIllegallyAbsorbingPublicDeposits;
import com.cjbdi.core.extractcenter.sentence.individualillegallyabsorbingpublicdeposits.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IndividualIllegallyAbsorbingPublicDepositsExtractor extends LabelExtractor {

   private IndividualIllegallyAbsorbingPublicDeposits individualIllegallyAbsorbingPublicDeposits;
   private InitExtractor initExtractor = new InitExtractor();


   public IndividualIllegallyAbsorbingPublicDepositsExtractor(IndividualIllegallyAbsorbingPublicDeposits individualIllegallyAbsorbingPublicDeposits) {
      this.modelUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictfeaturermdl();
      this.basicPureRuleExtracts = this.initExtractor.getBasicPureRuleExtractors();
      this.basicModelRuleExtracts = this.initExtractor.getBasicModelRuleExtractors();
      this.individualIllegallyAbsorbingPublicDeposits = individualIllegallyAbsorbingPublicDeposits;
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList labelList = new ArrayList();
      Iterator var4 = this.initExtractor.basicPrivateExtractors.iterator();

      while(var4.hasNext()) {
         BasicSentenceFeatureClass basicSentenceFeatureClass = (BasicSentenceFeatureClass)var4.next();
         Label label = basicSentenceFeatureClass.run(defendantModel, casecauseModel, this.individualIllegallyAbsorbingPublicDeposits);
         if(label != null) {
            labelList.add(label);
         }
      }

      return labelList;
   }

   public IndividualIllegallyAbsorbingPublicDeposits getIndividualIllegallyAbsorbingPublicDeposits() {
      return individualIllegallyAbsorbingPublicDeposits;
   }

   public InitExtractor getInitExtractor() {
      return initExtractor;
   }
}
