package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.CommonBasicConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.PunishedBeforeLabelExtractor;
import java.time.LocalDate;
import java.util.List;

public class CriminalPunishmentLabelExtractor {

   private String casecause;
   private List positvePureRule;
   private int deadline;


   public CriminalPunishmentLabelExtractor(String casecause, int deadline) {
      this.casecause = casecause;
      this.deadline = deadline;
      this.positvePureRule = ((CommonBasicConfig)BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get("criminalpunishmentextract")).getPositivepurePattern();
   }

   public BoolConfig proc(LocalDate caughtDate, List criminalRecords) {
      BoolConfig boolConfig = PunishedBeforeLabelExtractor.extract(this.casecause, caughtDate, criminalRecords, this.deadline, this.positvePureRule);
      return boolConfig != null?boolConfig:null;
   }
}
