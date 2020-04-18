package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.CommonBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.NumberVictim;
import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.List;

public class NumberOfSecondLevelMinorInjury extends BasicSentenceFeatureClass {

   private String code;
   private String feature;
   private String keyword;
   private String noiseword;
   private List positivePurePattern;
   private List negativePurePattern;


   public NumberOfSecondLevelMinorInjury(String code) {
      this.init(code);
   }

   public void init(String code) {
      this.code = code;
      this.feature = "secondlevelminorinjurynumberextract";
      this.keyword = "轻伤二级|二级轻伤|轻伤2级|2级轻伤";
      this.noiseword = "死|轻微伤|重伤|一级|1级";
      this.positivePurePattern = ((CommonBasicConfig)BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(this.feature)).getPositivepurePattern();
      this.negativePurePattern = ((CommonBasicConfig)BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(this.feature)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      NumberVictim numberVictim = new NumberVictim(this.positivePurePattern, this.negativePurePattern);
      NumberConfig numberConfig = numberVictim.doextract(defendantModel, casecauseModel, this.keyword, this.noiseword);
      if(numberConfig != null) {
         Label label = SetLabel.run(numberConfig, this.code);
         return label;
      } else {
         return null;
      }
   }
}
