package com.cjbdi.core.extractcenter.sentence.transportationofhazardouschemical;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.transportationofhazardouschemical.OverloadExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class MiniVehicleOverloadExtractor extends BasicSentenceFeatureClass {

   private List positivepurerule;
   private String code = "1004";


   public MiniVehicleOverloadExtractor() {
      this.positivepurerule = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getDrunkDriving().getFeatures().get("miniVehicleOverloadextract")).getPositivepurerule();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      if(!OverloadExtractor.run(defendantModel, casecauseModel, caseClass)) {
         return null;
      } else {
         String factText;
         Map result;
         Label label;
         if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
            factText = casecauseModel.getOpinion();
            result = MatchRule.matchRule(factText, this.positivepurerule);
            if(result != null && result.size() > 0) {
               new Label();
               label = SetLabel.run(result, this.code);
               return label;
            }
         }

         if(StringUtils.isNotEmpty(casecauseModel.getJustice())) {
            factText = casecauseModel.getJustice();
            result = MatchRule.matchRule(factText, this.positivepurerule);
            if(result != null && result.size() > 0) {
               new Label();
               label = SetLabel.run(result, this.code);
               return label;
            }
         }

         return null;
      }
   }
}
