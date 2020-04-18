package com.cjbdi.core.extractcenter.sentence.rape;

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

public class NumberOfYoungGirl extends BasicSentenceFeatureClass {

   private String code;
   private String keyword;
   private String noiseword;
   private List positivePureRule;
   private List negativePureRule;


   public NumberOfYoungGirl() {
      this.init();
   }

   public void init() {
      this.code = "1003";
      this.keyword = "幼女";
      this.noiseword = "轻伤|轻微伤|重伤|死|妇女";
      this.positivePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getRape().getFeatures().get(this.code)).getPositivepurePattern();
      this.negativePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getRape().getFeatures().get(this.code)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      NumberVictim numberVictim = new NumberVictim(this.positivePureRule, this.negativePureRule);
      NumberConfig numberConfig = numberVictim.doextract(defendantModel, casecauseModel, this.keyword, this.noiseword);
      if(numberConfig != null) {
         Label label = SetLabel.run(numberConfig, this.code);
         return label;
      } else {
         return null;
      }
   }
}
