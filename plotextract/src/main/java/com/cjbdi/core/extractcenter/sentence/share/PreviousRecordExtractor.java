package com.cjbdi.core.extractcenter.sentence.share;

import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.CriminalPunishmentLabelExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PreviousRecordExtractor {

   private String code;
   private List pPatternList;
   private List nPatternList;
   private ExtractFeatureBasicConfig extractFeatureBasicConfig;


   public PreviousRecordExtractor(ExtractFeatureBasicConfig extractFeatureBasicConfig) {
      this.extractFeatureBasicConfig = extractFeatureBasicConfig;
      this.pPatternList = extractFeatureBasicConfig.getPositivepurePattern();
      this.nPatternList = extractFeatureBasicConfig.getNegativepurePattern();
      this.code = extractFeatureBasicConfig.getCode();
   }

   protected Label doextract(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      String conclusion = casecauseModel.getOpinion();
      if (StringUtils.isNotEmpty(conclusion)) {
         conclusion = conclusion.replaceAll(defendantModel.getName(), "被告人");
         if (!defendantModel.getDefendantNameSet().isEmpty() && defendantModel.getDefendantNameSet().size() == 1) {
            BoolConfig criminalPunishmentLabelExtractor = MatchRule.matchPatternBoolConfig(conclusion, this.pPatternList, this.nPatternList);
            if (criminalPunishmentLabelExtractor != null) {
               Label recordsOpt1 = SetLabel.run(criminalPunishmentLabelExtractor, this.code);
               recordsOpt1.setParaText(casecauseModel.getOpinion());
               recordsOpt1.setParaName("本院认为");
               return recordsOpt1;
            }
         }
      }

      CriminalPunishmentLabelExtractor criminalPunishmentLabelExtractor1 = new CriminalPunishmentLabelExtractor(casecauseModel.getCasecause(), 99999);
      List recordsOpt = defendantModel.getCriminalRecordList();
      Optional crimeDate = defendantModel.getCrimeDate();
      if(!crimeDate.isPresent() && defendantModel.getArrestDate().isPresent()) {
         crimeDate = defendantModel.getArrestDate();
      } else if(!crimeDate.isPresent() && defendantModel.getProsecuteDate().isPresent()) {
         crimeDate = defendantModel.getProsecuteDate();
      }

      if(!recordsOpt.isEmpty() && crimeDate.isPresent()) {
         BoolConfig boolConfig = criminalPunishmentLabelExtractor1.proc((LocalDate)crimeDate.get(), recordsOpt);
         if(boolConfig != null) {
            Label label = SetLabel.run(boolConfig, this.code);
            label.setParaText(casecauseModel.getDefendant());
            label.setParaName("被告人段");
            return label;
         }
      }

      return null;
   }
}
