package com.cjbdi.core.extractcenter.sentence.traffic;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Traffic;
import com.cjbdi.core.extractcenter.sentence.traffic.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TrafficExtractor extends LabelExtractor {

   private Traffic traffic;
   private InitExtractor initExtractor = new InitExtractor();


   public TrafficExtractor(Traffic traffic) {
      this.modelUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictfeaturepbnew();
      this.basicPureRuleExtracts = this.initExtractor.getBasicPureRuleExtractors();
      this.basicModelRuleExtracts = this.initExtractor.getBasicModelRuleExtractors();
      this.traffic = traffic;
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList labelList = new ArrayList();
      Iterator var4 = this.initExtractor.basicPrivateExtractors.iterator();

      while(var4.hasNext()) {
         BasicSentenceFeatureClass basicSentenceFeatureClass = (BasicSentenceFeatureClass)var4.next();
         Label label = basicSentenceFeatureClass.run(defendantModel, casecauseModel, this.traffic);
         if(label != null) {
            labelList.add(label);
         }
      }

      return labelList;
   }
}
