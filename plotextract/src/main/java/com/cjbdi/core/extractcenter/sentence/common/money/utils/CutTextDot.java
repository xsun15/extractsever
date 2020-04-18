package com.cjbdi.core.extractcenter.sentence.common.money.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CutTextDot {

   public static String run(String text, String target, int start, int end) {
      List str = Arrays.asList(text.split("(，|。|？|！|、|：)"));
      Iterator var5 = str.iterator();

      String str1;
      do {
         if(!var5.hasNext()) {
            return "";
         }

         str1 = (String)var5.next();
      } while(!str1.contains(target) || text.indexOf(str1) > start || text.indexOf(str1) + str1.length() < end);

      return str1;
   }
}
