package com.cjbdi.core.extractcenter.sentence.providingvenuesfordrugusers;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.List;

public class AdministrativePenalty extends BasicSentenceFeatureClass {

   private String name;
   private String feature;
   private String code = "1003";
   private List positiveRule;
   private List negativeRule;


   public AdministrativePenalty() {
      this.name = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getProvidingVenuesForDrugUsers().getFeatures().get(this.code)).getName();
      this.positiveRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getProvidingVenuesForDrugUsers().getFeatures().get(this.code)).getPositivepurePattern();
      this.negativeRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getProvidingVenuesForDrugUsers().getFeatures().get(this.code)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      new Label();
      return null;
   }
}
