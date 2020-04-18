package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.extractcenter.sentence.utils.AgeExtractor;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.Optional;

public class Age {

   public static Optional run(DefendantModel defendantModel) {
      try {
         Optional e = defendantModel.getBirthday();
         Optional crimeDate = defendantModel.getCrimeDate();
         if(!crimeDate.isPresent() && defendantModel.getArrestDate().isPresent()) {
            crimeDate = defendantModel.getArrestDate();
         } else if(!crimeDate.isPresent() && defendantModel.getProsecuteDate().isPresent()) {
            crimeDate = defendantModel.getProsecuteDate();
         }

         Optional age = AgeExtractor.extract(e, crimeDate);
         return age;
      } catch (Exception var4) {
         var4.printStackTrace();
         return Optional.empty();
      }
   }
}
