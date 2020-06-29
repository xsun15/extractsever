package com.cjbdi.core.extractcenter.sentence.share;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.CommonBasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.CriminalRecord;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.PrisonTermExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class RecidivismExtractor {

   private String code;
   private ExtractFeatureBasicConfig extractFeatureBasicConfig;


   public RecidivismExtractor(ExtractFeatureBasicConfig extractFeatureBasicConfig) {
      this.extractFeatureBasicConfig = extractFeatureBasicConfig;
      this.code = extractFeatureBasicConfig.getCode();
   }

   public Label doextract(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      Label label = null;
      List negativecasecause = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get(this.code)).getNegativecasecause();
      if(negativecasecause.contains(casecauseModel.getCasecause())) {
         return label;
      } else {
         String conclusion = casecauseModel.getOpinion().replaceAll(defendantModel.getName(), "被告人");
         List PatternList = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get(this.code)).getPositivepurePattern();
         BoolConfig boolConfig = MatchRule.matchPatternBoolConfig(conclusion, PatternList);
         if(defendantModel.getDefendantNameSet().size() == 1 && boolConfig != null) {
            label = SetLabel.run(boolConfig, this.code);
            label.setParaText(casecauseModel.getOpinion());
            label.setParaName("本院认为");
            return label;
         } else {
            PatternList = ((CommonBasicConfig) BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get("criminalpunishmentextract")).getPositivepurePattern();
            if (defendantModel.getCriminalRecordList()!= null && !defendantModel.getCriminalRecordList().isEmpty()) {
               for (int i = defendantModel.getCriminalRecordList().size() - 1; i >= 0; --i) {
                  CriminalRecord criminalRecord = (CriminalRecord) defendantModel.getCriminalRecordList().get(i);
                  if (!criminalRecord.getCause().contains("交通肇事") && !casecauseModel.getCasecause().contains("交通肇事")) {
                     Optional crimeDate = defendantModel.getCrimeDate();
                     if (!crimeDate.isPresent() && defendantModel.getArrestDate().isPresent()) {
                        crimeDate = defendantModel.getArrestDate();
                     } else if (!crimeDate.isPresent() && defendantModel.getProsecuteDate().isPresent()) {
                        crimeDate = defendantModel.getProsecuteDate();
                     }

                     if (crimeDate.isPresent()) {
                        if (criminalRecord.getReleaseTime().isPresent() && crimeDate.isPresent()) {
                           LocalDate var15 = (LocalDate) criminalRecord.getReleaseTime().get();
                           Period var16 = Period.between(var15, (LocalDate) crimeDate.get());
                           if (var16.getYears() < 5 || var16.getMonths() == 0 && var16.getYears() == 5) {
                              boolConfig = MatchRule.matchRuleBoolConfig(criminalRecord.getText(), casecauseModel.getDefendant());
                              label = SetLabel.run(boolConfig, this.code);
                              label.setParaText(casecauseModel.getDefendant());
                              label.setParaName("被告人段");
                              return label;
                           }
                        } else if (criminalRecord.getTime().isPresent()) {
                           PrisonTermExtractor prisonTermExtractor = new PrisonTermExtractor();
                           int prisonTime = prisonTermExtractor.extract(criminalRecord.getText(), PatternList);
                           LocalDate releaseTime = ((LocalDate) criminalRecord.getTime().get()).plus((long) prisonTime, ChronoUnit.MONTHS);
                           if (crimeDate.isPresent()) {
                              Period period = Period.between(releaseTime, (LocalDate) crimeDate.get());
                              if (period.getYears() < 5 || period.getMonths() == 0 && period.getYears() == 5) {
                                 boolConfig = MatchRule.matchRuleBoolConfig(criminalRecord.getText(), casecauseModel.getDefendant());
                                 label = SetLabel.run(boolConfig, this.code);
                                 label.setParaText(casecauseModel.getDefendant());
                                 label.setParaName("被告人段");
                                 return label;
                              }
                           }
                        }
                     }
                  }
               }
            }
            return label;
         }
      }
   }
}
