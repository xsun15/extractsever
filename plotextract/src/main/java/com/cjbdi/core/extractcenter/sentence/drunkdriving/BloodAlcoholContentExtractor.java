package com.cjbdi.core.extractcenter.sentence.drunkdriving;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class BloodAlcoholContentExtractor extends BasicSentenceFeatureClass {

   private List positivepurePattern;
   private String code = "1001";


   public BloodAlcoholContentExtractor() {
      this.positivepurePattern = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getDrunkDriving().getFeatures().get(this.code)).getPositivepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      String factText;
      Map result;
      Label label;
      if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
         factText = casecauseModel.getOpinion();
         result = MatchRule.matchPattern(factText, this.positivepurePattern);
         if(result != null && result.size() > 0) {
            new Label();
            label = SetLabel.run((String)result.get("text"), this.code);
            return label;
         }
      }

      if(StringUtils.isNotEmpty(casecauseModel.getJustice())) {
         factText = casecauseModel.getJustice();
         result = MatchRule.matchPattern(factText, this.positivepurePattern);
         if(result != null && result.size() > 0) {
            new Label();
            label = SetLabel.run((String)result.get("text"), this.code);
            return label;
         }
      }

      return null;
   }
}
