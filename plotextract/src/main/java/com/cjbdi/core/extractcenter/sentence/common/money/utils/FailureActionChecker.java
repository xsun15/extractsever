package com.cjbdi.core.extractcenter.sentence.common.money.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FailureActionChecker {

   static Map failureExpMap;
   static Map failurePatternMap;


   public static void setFailureExpMap(Map failureExpMap) {
      failureExpMap = failureExpMap;
      failurePatternMap = new HashMap();
      Set causeNameSet = failureExpMap.keySet();
      Iterator var2 = causeNameSet.iterator();

      while(var2.hasNext()) {
         String causeName = (String)var2.next();
         List expList = (List)failureExpMap.get(causeName);
         if(expList != null) {
            ArrayList patternList = new ArrayList();
            Iterator var6 = expList.iterator();

            while(var6.hasNext()) {
               String exp = (String)var6.next();
               Pattern pattern = Pattern.compile(exp);
               patternList.add(pattern);
            }

            failurePatternMap.put(causeName, patternList);
         }
      }

   }

   public static boolean isFailure(String causeName, String text) {
      if(failurePatternMap != null && !failurePatternMap.isEmpty()) {
         List patternList = (List)failurePatternMap.get(causeName);
         if(patternList == null) {
            patternList = (List)failurePatternMap.get("default");
         }

         if(patternList != null && !patternList.isEmpty()) {
            Iterator var3 = patternList.iterator();

            Matcher matcher;
            do {
               if(!var3.hasNext()) {
                  return false;
               }

               Pattern pattern = (Pattern)var3.next();
               matcher = pattern.matcher(text);
            } while(!matcher.find());

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
}
