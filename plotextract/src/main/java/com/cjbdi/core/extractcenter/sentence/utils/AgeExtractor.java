package com.cjbdi.core.extractcenter.sentence.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public class AgeExtractor {

   public static Optional extract(Optional birth, Optional courtDate) {
      if(birth.isPresent() && courtDate.isPresent()) {
         try {
            Period e = Period.between((LocalDate)birth.get(), (LocalDate)courtDate.get());
            int years = e.getYears();
            int months = e.getMonths();
            int days = e.getDays();
            return Optional.of(Integer.valueOf(years));
         } catch (Exception var6) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }
}
