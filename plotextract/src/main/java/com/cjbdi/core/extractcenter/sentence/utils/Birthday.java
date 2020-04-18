package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.extractcenter.sentence.utils.DateTimeExtractor;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Birthday {

   public static Optional run(String text, String defendant, Optional yearInIdCardNum) {
      Iterator Num = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get("1001")).getPositivemodelPattern().iterator();

      while(Num.hasNext()) {
         Pattern e = (Pattern)Num.next();
         Matcher matcher = e.matcher(text);

         while(matcher.find()) {
            String matchText = matcher.group();
            if(matchText.contains(defendant) && DateTimeExtractor.extract(matchText).isPresent()) {
               return Optional.of(((LocalDateTime) DateTimeExtractor.extract(matchText).get()).toLocalDate());
            }
         }
      }

      if(yearInIdCardNum.isPresent()) {
         String Num1 = ((String)yearInIdCardNum.get()).substring(6, 10);

         try {
            LocalDateTime e1 = LocalDateTime.of(Integer.parseInt(Num1), 1, 1, 0, 0);
            return Optional.of(e1.toLocalDate());
         } catch (Exception var7) {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }
}
