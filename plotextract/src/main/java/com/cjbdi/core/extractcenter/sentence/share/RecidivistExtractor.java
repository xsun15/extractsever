package com.cjbdi.core.extractcenter.sentence.share;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.List;

public class RecidivistExtractor {

   private String code;
   private ExtractFeatureBasicConfig extractFeatureBasicConfig;


   public RecidivistExtractor(ExtractFeatureBasicConfig extractFeatureBasicConfig) {
      this.extractFeatureBasicConfig = extractFeatureBasicConfig;
      this.code = extractFeatureBasicConfig.getCode();
   }

   protected Label doextract(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      List negativecasecause = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get(this.code)).getNegativecasecause();
      if(!negativecasecause.contains(casecauseModel.getCasecause())) {
         String conclusion = casecauseModel.getOpinion();
         List PatternList = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get(this.code)).getPositivepurePattern();
         String code = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get(this.code)).getCode();
         if(!defendantModel.getDefendantNameSet().isEmpty() && defendantModel.getDefendantNameSet().size() == 1) {
            BoolConfig background = MatchRule.matchPatternBoolConfig(conclusion, PatternList);
            if(background != null) {
               Label boolConfig1 = SetLabel.run(background, code);
               boolConfig1.setParaText(casecauseModel.getOpinion());
               boolConfig1.setParaName("本院认为");
               return boolConfig1;
            }
         }

         String background1 = casecauseModel.getDefendant();
         if(!defendantModel.getDefendantNameSet().isEmpty() && defendantModel.getDefendantNameSet().size() == 1) {
            BoolConfig boolConfig = MatchRule.matchPatternBoolConfig(background1, PatternList);
            if(boolConfig != null) {
               Label label = SetLabel.run(boolConfig, code);
               label.setParaText(casecauseModel.getJustice());
               label.setParaName("被告人段");
               return label;
            }
         }
      }

      return null;
   }
}
