package com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.CriminalRecord;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DrugsRecidivism extends BasicSentenceFeatureClass {

   private String name;
   private String feature;
   private String code = "1005";
   private List positiveRule;
   private List negativeRule;


   public DrugsRecidivism() {
      this.name = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getIllegalPossessionDrugs().getFeatures().get(this.code)).getName();
      this.positiveRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getIllegalPossessionDrugs().getFeatures().get(this.code)).getPositivepurePattern();
      this.negativeRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getIllegalPossessionDrugs().getFeatures().get(this.code)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      new Label();
      String conclusionText = casecauseModel.getOpinion();
      Label label;
      if(conclusionText != null && !conclusionText.isEmpty()) {
         Map extractResult = MatchRule.matchPattern(conclusionText, this.positiveRule, this.negativeRule);
         if(!extractResult.isEmpty()) {
            label = SetLabel.run(extractResult, this.code);
            return label;
         }
      }

      if(defendantModel.getCriminalRecordList() != null && !defendantModel.getCriminalRecordList().isEmpty()) {
         Iterator extractResult1 = defendantModel.getCriminalRecordList().iterator();

         while(extractResult1.hasNext()) {
            CriminalRecord criminalRecord = (CriminalRecord)extractResult1.next();
            String text = criminalRecord.getText();
            if(text.contains("走私毒品") || text.contains("贩卖毒品") || text.contains("运输毒品") || text.contains("制造毒品") || text.contains("非法持有毒品")) {
               label = SetLabel.run(this.code);
               return label;
            }
         }
      }

      return null;
   }
}
