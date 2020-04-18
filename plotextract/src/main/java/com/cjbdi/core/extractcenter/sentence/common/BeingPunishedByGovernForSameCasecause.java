package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.GovernPunishmentLabelExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;

public class BeingPunishedByGovernForSameCasecause extends BasicSentenceFeatureClass {

   private String feature;
   private String code;
   private List positivePurePattern;
   private List negativePurePattern;


   public BeingPunishedByGovernForSameCasecause(String feature) {
      this.feature = feature;
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      this.positivePurePattern = ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.feature)).getPositivepurePattern();
      this.negativePurePattern = ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.feature)).getNegativepurePattern();
      this.code = ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.feature)).getCode();
      new Label();
      String conclusion = casecauseModel.getOpinion();
      if(StringUtils.isNotEmpty(conclusion)) {
         conclusion = conclusion.split("中华人民共和国")[0];
         if(StringUtils.isNotEmpty(conclusion)) {
            Map governPunishmentLabelExtractor = MatchRule.matchPattern(conclusion, this.positivePurePattern, this.negativePurePattern);
            if(governPunishmentLabelExtractor != null && governPunishmentLabelExtractor.size() > 0) {
               return SetLabel.run(governPunishmentLabelExtractor, ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.feature)).getCode());
            }
         }
      }

      GovernPunishmentLabelExtractor governPunishmentLabelExtractor1 = new GovernPunishmentLabelExtractor(casecauseModel.getCasecause(), 12);
      List recordsOpt = defendantModel.getCriminalRecordList();
      Optional courtDateOpt = defendantModel.getCrimeDate();
      if(recordsOpt != null && courtDateOpt.isPresent()) {
         BoolConfig boolConfig = governPunishmentLabelExtractor1.proc((LocalDate)courtDateOpt.get(), recordsOpt);
         if(boolConfig != null) {
            return SetLabel.run(boolConfig, ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.feature)).getCode());
         }
      }

      return null;
   }
}
