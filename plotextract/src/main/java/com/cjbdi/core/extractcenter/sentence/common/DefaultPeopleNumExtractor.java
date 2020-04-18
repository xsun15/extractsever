package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.CommonBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.List;

public class DefaultPeopleNumExtractor extends BasicSentenceFeatureClass {

   private String feature = "peopleextract";
   private List positivePurePattern;
   private String code;


   public DefaultPeopleNumExtractor(String code) {
      this.positivePurePattern = ((CommonBasicConfig) BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(this.feature)).getPositivepurePattern();
      this.code = code;
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      return null;
   }
}
