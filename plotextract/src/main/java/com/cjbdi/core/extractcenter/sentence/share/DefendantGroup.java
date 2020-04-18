package com.cjbdi.core.extractcenter.sentence.share;

import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;

public class DefendantGroup extends BasicSentenceFeatureClass {

   private ExtractFeatureBasicConfig extractFeatureBasicConfig;


   public DefendantGroup(ExtractFeatureBasicConfig extractFeatureBasicConfig) {
      this.extractFeatureBasicConfig = extractFeatureBasicConfig;
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass, int age, int low, int up) {
      String content = casecauseModel.getOpinion();
      return this.doExtract(content, age, low, up);
   }

   public Label doExtract(String lineText, int age, int low, int up) {
      BoolConfig boolConfig = MatchRule.matchPatternBoolConfig(lineText, this.extractFeatureBasicConfig.getPositivepurePattern(), this.extractFeatureBasicConfig.getNegativepurePattern());
      Label label = null;
      if(boolConfig != null) {
         label = SetLabel.run(boolConfig, this.extractFeatureBasicConfig.getCode());
         label.setParaText(lineText);
      } else {
         label = SetLabel.run(String.valueOf(age), this.extractFeatureBasicConfig.getCode());
         label.setParaText(lineText);
      }

      return label;
   }
}
