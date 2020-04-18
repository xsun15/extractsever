package com.cjbdi.core.extractcenter.sentence.traffic;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.NumberVictim;
import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.List;
import java.util.Set;

public class NumberOfEscapeCausingDeath extends BasicSentenceFeatureClass {

   private String code = "1026";
   private String keyword = "死亡";
   private String noiseword = "轻伤|轻微伤|重伤";
   private List positivePureRule;
   private List negativePureRule;


   public NumberOfEscapeCausingDeath() {
      this.positivePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getTraffic().getFeatures().get(this.code)).getPositivepurePattern();
      this.negativePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getTraffic().getFeatures().get(this.code)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      NumberConfig numberConfig = this.doextract(defendantModel, casecauseModel, this.keyword, this.noiseword);
      if(numberConfig != null) {
         Label label = SetLabel.run(numberConfig, ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.code)).getCode());
         return label;
      } else {
         return null;
      }
   }

   public NumberConfig doextract(DefendantModel defendantModel, CasecauseModel casecauseModel, String keyword, String noiseword) {
      Set defendants = defendantModel.getDefendantNameSet();
      NumberVictim numberVictim = new NumberVictim(this.positivePureRule, this.negativePureRule);
      String conclusion = casecauseModel.getOpinion();
      NumberConfig numberConfig = numberVictim.runSummarize(conclusion, keyword, noiseword);
      if(numberConfig != null) {
         return numberConfig;
      } else {
         numberConfig = numberVictim.runCalculate(conclusion, keyword, noiseword, defendants);
         return numberConfig != null?numberConfig:null;
      }
   }
}
