package com.cjbdi.core.extractcenter.sentence.utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class VictimExtractor {

   public static Set run(String text, Set defendantNameSet) {
      HashSet victimNameSet = new HashSet();
      if(text != null && !text.isEmpty()) {
         Segment segment = HanLP.newSegment().enableNameRecognize(true);
         List textList = Arrays.asList(text.split("\n"));
         Iterator var5 = textList.iterator();

         while(var5.hasNext()) {
            String line = (String)var5.next();
            if(line.contains("被害人")) {
               List termList = segment.seg(line);
               boolean flag = false;
               Iterator var9 = termList.iterator();

               while(var9.hasNext()) {
                  Term term = (Term)var9.next();
                  String word = term.word;
                  String syntext = term.nature.toString();
                  if(word.equals("被害人")) {
                     flag = true;
                  } else if(flag && !syntext.equals("nr") && !syntext.equals("c") && !syntext.equals("cc") && !syntext.equals("w")) {
                     flag = false;
                  }

                  if(flag && syntext.equals("nr") && !defendantNameSet.contains(word)) {
                     victimNameSet.add(word);
                  }
               }
            }
         }
      }

      return victimNameSet;
   }
}
