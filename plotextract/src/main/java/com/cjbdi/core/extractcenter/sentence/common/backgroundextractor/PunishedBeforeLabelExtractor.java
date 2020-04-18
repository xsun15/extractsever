package com.cjbdi.core.extractcenter.sentence.common.backgroundextractor;

import com.cjbdi.core.extractcenter.sentence.utils.CriminalRecord;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunishedBeforeLabelExtractor {

   public static List extract(String casecause, LocalDate caughtDate, List criminalRecords, String keyword) {
      return extract(casecause, caughtDate, criminalRecords, Integer.MAX_VALUE, keyword);
   }

   public static List extract(String casecause, LocalDate criminalDate, List criminalRecords, int months, String keyword) {
      casecause = casecause.replaceAll("罪", "");
      ArrayList records = new ArrayList();
      Iterator var6 = criminalRecords.iterator();

      while(var6.hasNext()) {
         CriminalRecord criminalRecord = (CriminalRecord)var6.next();
         if(criminalRecord.getCause().indexOf(casecause) >= 0) {
            Pattern timePattern = Pattern.compile(keyword);
            Matcher timeMatcher = timePattern.matcher(criminalRecord.getText());
            if(timeMatcher.find() && criminalRecord.getTime().isPresent() && criminalDate != null) {
               LocalDate criminalTime = (LocalDate)criminalRecord.getTime().get();
               Period period = Period.between(criminalTime, criminalDate);
               int spaceMonth = period.getYears() * 12 + period.getMonths();
               if(spaceMonth <= months) {
                  records.add(criminalRecord);
               }
            }
         }
      }

      return records;
   }
}
