package com.cjbdi.core.extractcenter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractNumber {

   public static String run(String text, String rule) {
      Pattern p = Pattern.compile(rule);
      Matcher m = p.matcher(text);
      return m.find()?run(m.group()):"";
   }

   public static String run(String text) {
      Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
      Matcher m = p.matcher(text);

      String result;
      for(result = ""; m.find(); result = result + m.group()) {
         ;
      }

      return result;
   }
}
