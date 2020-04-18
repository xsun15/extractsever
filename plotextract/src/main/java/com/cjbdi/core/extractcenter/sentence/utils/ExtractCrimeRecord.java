package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.extractcenter.sentence.utils.CriminalRecord;
import com.cjbdi.core.extractcenter.sentence.utils.CriminalRecordExtractor;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExtractCrimeRecord {

   public static Map run(String backgroundText, String content, LinkedHashSet defendantTable) {
      HashMap defendantToCrimeRecord = new HashMap(defendantTable.size());
      CriminalRecordExtractor criminalRecordExtractor = new CriminalRecordExtractor();
      Iterator var5 = defendantTable.iterator();

      while(var5.hasNext()) {
         String defendant = (String)var5.next();
         List criminalRecords = criminalRecordExtractor.extract(defendant, backgroundText);
         defendantToCrimeRecord.put(defendant, criminalRecords);
      }

      return defendantToCrimeRecord;
   }

   public static List run(String backgroundText, Optional crimeDate, String defendant) {
      CriminalRecordExtractor criminalRecordExtractor = new CriminalRecordExtractor();
      List criminalRecords = criminalRecordExtractor.extract(defendant, backgroundText);
      ArrayList result = new ArrayList(criminalRecords);
      if(crimeDate != null && crimeDate.isPresent()) {
         Iterator var6 = criminalRecords.iterator();

         while(var6.hasNext()) {
            CriminalRecord criminalRecord = (CriminalRecord)var6.next();
            if(criminalRecord.getTime() != null && criminalRecord.getTime().isPresent() && ((LocalDate)criminalRecord.getTime().get()).isAfter((ChronoLocalDate)crimeDate.get())) {
               result.remove(criminalRecord);
            }
         }
      }

      return result;
   }
}
