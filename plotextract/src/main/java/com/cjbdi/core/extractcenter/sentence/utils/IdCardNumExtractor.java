package com.cjbdi.core.extractcenter.sentence.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdCardNumExtractor {

   static List patternList = new ArrayList();


   public static void setCaseExpList(List caseExpList) {
      Iterator var1 = caseExpList.iterator();

      while(var1.hasNext()) {
         String exp = (String)var1.next();
         Pattern pattern = Pattern.compile(exp);
         patternList.add(pattern);
      }

   }

   public static Optional extract(String text, String defendant) {
      Iterator var2 = patternList.iterator();

      while(var2.hasNext()) {
         Pattern pattern = (Pattern)var2.next();
         Matcher matcher = pattern.matcher(text);

         while(matcher.find()) {
            String matchText = matcher.group();
            if(matchText.contains(defendant)) {
               int index = matchText.indexOf("身份证号码");
               if(index > 0) {
                  String ret = matchText.substring(index + 5, matchText.length() - 1).replaceAll("[^0-9*]", "");
                  return Optional.of(ret);
               }
            }
         }
      }

      return Optional.empty();
   }

}
