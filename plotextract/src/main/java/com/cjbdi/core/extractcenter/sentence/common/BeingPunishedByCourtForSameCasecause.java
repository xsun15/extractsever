package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.CriminalPunishmentLabelExtractor;
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

public class BeingPunishedByCourtForSameCasecause extends BasicSentenceFeatureClass {

   private String code;
   private List positivePureRule;
   private List negativePureRule;


   public BeingPunishedByCourtForSameCasecause(String code) {
      this.code = code;
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      this.positivePureRule = ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.code)).getPositivepurePattern();
      this.negativePureRule = ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.code)).getNegativepurePattern();
      new Label();
      if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
         String criminalPunishmentLabelExtractor = casecauseModel.getOpinion();
         if(StringUtils.isNotEmpty(criminalPunishmentLabelExtractor)) {
            criminalPunishmentLabelExtractor = criminalPunishmentLabelExtractor.split("中华人民共和国")[0];
            if(StringUtils.isNotEmpty(criminalPunishmentLabelExtractor)) {
               Map recordsOpt = MatchRule.matchPattern(criminalPunishmentLabelExtractor, this.positivePureRule, this.negativePureRule);
               if(recordsOpt != null && recordsOpt.size() > 0) {
                  return SetLabel.run(recordsOpt, this.code);
               }
            }
         }
      }

      CriminalPunishmentLabelExtractor criminalPunishmentLabelExtractor1 = new CriminalPunishmentLabelExtractor(casecauseModel.getCasecause(), 99999);
      List recordsOpt1 = defendantModel.getCriminalRecordList();
      Optional crimeDate = defendantModel.getCrimeDate();
      if(crimeDate != null && !crimeDate.isPresent() && defendantModel.getArrestDate() != null && defendantModel.getArrestDate().isPresent()) {
         crimeDate = defendantModel.getArrestDate();
      } else if(crimeDate != null && !crimeDate.isPresent() && defendantModel.getProsecuteDate() != null && defendantModel.getProsecuteDate().isPresent()) {
         crimeDate = defendantModel.getProsecuteDate();
      }

      if(recordsOpt1 != null && !recordsOpt1.isEmpty() && crimeDate != null && crimeDate.isPresent()) {
         BoolConfig boolConfig = criminalPunishmentLabelExtractor1.proc((LocalDate)crimeDate.get(), recordsOpt1);
         if(boolConfig != null) {
            return SetLabel.run(boolConfig, this.code);
         }
      }

      return null;
   }
}
