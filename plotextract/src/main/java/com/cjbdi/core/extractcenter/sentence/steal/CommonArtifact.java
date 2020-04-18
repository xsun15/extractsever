package com.cjbdi.core.extractcenter.sentence.steal;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.NumberGoods;
import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.List;

public class CommonArtifact extends BasicSentenceFeatureClass {

   private String feature;
   private String keyword;
   private String noiseword;
   private List positivePureRule;
   private List negativePureRule;


   public CommonArtifact(String feature) {
      this.feature = feature;
      this.keyword = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getSteal().getFeatures().get(feature)).getKeyword();
      this.noiseword = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getSteal().getFeatures().get(feature)).getNoiseword();
      this.positivePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getSteal().getFeatures().get(feature)).getPositivepurePattern();
      this.negativePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getSteal().getFeatures().get(feature)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      NumberGoods numberGoods = new NumberGoods(this.positivePureRule, this.negativePureRule);
      NumberConfig numberConfig = numberGoods.doextract(defendantModel, casecauseModel, this.keyword, this.noiseword);
      if(numberConfig != null) {
         Label label = SetLabel.run(numberConfig, ((ExtractFeatureBasicConfig)caseClass.getFeatures().get(this.feature)).getCode());
         return label;
      } else {
         return null;
      }
   }
}
