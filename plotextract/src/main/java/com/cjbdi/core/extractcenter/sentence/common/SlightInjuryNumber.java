package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.NumberVictim;
import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;

import java.util.List;
import java.util.regex.Pattern;

public class SlightInjuryNumber extends BasicSentenceFeatureClass {
   private String code;
   private String feature;
   private String keyword;
   private String noiseword;
   private List<Pattern> positivePurePattern;
   private List<Pattern> negativePurePattern;

   public SlightInjuryNumber(String code) {
      init(code);
   }
   public void init(String code) {
      this.code = code;
      this.feature = "slightinjurynumberextract";
      this.keyword = "轻微伤";
      this.noiseword = "死|轻伤|重伤";
      this.positivePurePattern = BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(feature).getPositivepurePattern();
      this.negativePurePattern = BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(feature).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      NumberVictim numberVictim = new NumberVictim(positivePurePattern, negativePurePattern);
      NumberConfig numberConfig = numberVictim.doextract(defendantModel, casecauseModel, keyword, noiseword);
      if (numberConfig != null) {
         Label label = SetLabel.run(numberConfig, this.code);
         return label;
      }
      return null;
   }
}
