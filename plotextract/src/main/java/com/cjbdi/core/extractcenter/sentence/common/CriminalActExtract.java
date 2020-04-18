package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.extractcenter.sentence.common.time.TimeNormalizer;
import com.cjbdi.core.extractcenter.sentence.common.time.TimeUnit;
import com.cjbdi.core.extractcenter.sentence.common.utils.CriminalAct;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CriminalActExtract {

   public static List run(String text) {
      if(text != null && !text.isEmpty()) {
         TimeNormalizer normalizer = new TimeNormalizer(BeanFactoryConfig.configPlace.getOthersConfigPlace().getTime());
         Segment segment = HanLP.newSegment();
         segment.enablePlaceRecognize(true);
         List textList = Arrays.asList(text.split("\n"));
         ArrayList criminalActList = new ArrayList();
         Iterator var5 = textList.iterator();

         while(var5.hasNext()) {
            String line = (String)var5.next();
            CriminalAct criminalAct = new CriminalAct();
            normalizer.parse(line);
            TimeUnit[] units = normalizer.getTimeUnit();
            if(units != null) {
               String time = units[0].Time_Expression;
               LocalDate localDate = LocalDate.parse(time);
               criminalAct.setLocalDate(localDate);
            }

            criminalAct.setAction(line);
            criminalActList.add(criminalAct);
         }
      }

      return null;
   }
}
