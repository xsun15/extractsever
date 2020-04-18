package com.cjbdi.core.extractcenter.sentence.common.disabilitylevel;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.CommonBasicConfig;
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
import java.util.regex.Pattern;

public class SixthLevelDisability extends BasicSentenceFeatureClass {

   private String code;
   private String feature;
   private String keyword;
   private String noiseword;
   private List positivePurePattern;
   private List negativePurePattern;


   public SixthLevelDisability(String code) {
      this.init(code);
   }

   public void init(String code) {
      this.code = code;
      this.feature = "disabilitylevelextract";
      this.keyword = "伤残(等级)?[六6]级|[六6]级伤残";
      this.noiseword = "死|轻微伤|[(重伤)(轻伤)]|[(重伤)(轻伤)][一二12]级|伤残(等级)?[7-9二三四五七八九十1-5]级|[7-9二三四五七八九十1-5]级伤残";
      this.positivePurePattern = ((CommonBasicConfig)BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(this.feature)).getPositivepurePattern();
      this.reset();
      this.negativePurePattern = ((CommonBasicConfig)BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(this.feature)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      NumberVictim numberVictim = new NumberVictim(this.positivePurePattern, this.negativePurePattern);
      NumberConfig numberConfig = numberVictim.doextract(defendantModel, casecauseModel, this.keyword, this.noiseword);
      if(numberConfig != null) {
         Label label = SetLabel.run(numberConfig, ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.code)).getCode());
         return label;
      } else {
         return null;
      }
   }

   public void reset() {
      if(this.positivePurePattern != null && this.positivePurePattern.size() > 0) {
         for(int i = 0; i < this.positivePurePattern.size(); ++i) {
            String line = ((Pattern)this.positivePurePattern.get(i)).toString();
            this.positivePurePattern.set(i, Pattern.compile(line.replaceAll("占位", "六6")));
         }
      }

   }
}
