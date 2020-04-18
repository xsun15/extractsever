package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.CriminalRecord;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;
import java.util.List;

public class PunishedBeforeLabelExtractor {

   public static BoolConfig extract(String casecause, LocalDate caughtDate, List criminalRecords, int deadline, List Patternlist) {
      casecause = casecause.replaceAll("罪", "");
      Iterator var5 = criminalRecords.iterator();

      while(var5.hasNext()) {
         CriminalRecord criminalRecord = (CriminalRecord)var5.next();
         if(criminalRecord.getCause().contains(casecause)) {
            BoolConfig boolConfig = MatchRule.matchPatternBoolConfig(criminalRecord.getText(), Patternlist);
            if(criminalRecord.getTime().isPresent() && caughtDate != null) {
               LocalDate criminalTime = (LocalDate)criminalRecord.getTime().get();
               Period period = Period.between(criminalTime, caughtDate);
               int spaceMonth = period.getYears() * 12 + period.getMonths();
               if(spaceMonth <= deadline) {
                  return boolConfig;
               }
            }
         }
      }

      return null;
   }
}
