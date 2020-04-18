package com.cjbdi.core.extractcenter.sentence.transportationofhazardouschemical;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class OverloadExtractor {

   private static List positivepurerule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getSchoolBusBusinessOrPassengerTransportation().getFeatures().get("overloadextract")).getPositivepurerule();


   public static boolean run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      String factText;
      Map result;
      if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
         factText = casecauseModel.getOpinion();
         result = MatchRule.matchRule(factText, positivepurerule);
         if(result != null && result.size() > 0) {
            return true;
         }
      }

      if(StringUtils.isNotEmpty(casecauseModel.getJustice())) {
         factText = casecauseModel.getJustice();
         result = MatchRule.matchRule(factText, positivepurerule);
         if(result != null && result.size() > 0) {
            return true;
         }
      }

      return false;
   }

}
