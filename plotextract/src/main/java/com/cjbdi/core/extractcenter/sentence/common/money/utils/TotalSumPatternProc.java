package com.cjbdi.core.extractcenter.sentence.common.money.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TotalSumPatternProc {

   static Map totalSumExpMap;
   static Map totalSumPatternMap;


   public static void setTotalSumExpMap(Map totalSumExpMap) {
      totalSumExpMap = totalSumExpMap;
      totalSumPatternMap = new HashMap();
      Set causeNameSet = totalSumExpMap.keySet();
      Iterator var2 = causeNameSet.iterator();

      while(var2.hasNext()) {
         String causeName = (String)var2.next();
         List expList = (List)totalSumExpMap.get(causeName);
         if(expList != null) {
            ArrayList patternList = new ArrayList();
            Iterator var6 = expList.iterator();

            while(var6.hasNext()) {
               String exp = (String)var6.next();
               Pattern pattern = Pattern.compile(exp);
               patternList.add(pattern);
            }

            totalSumPatternMap.put(causeName, patternList);
         }
      }

   }

   public String parseTotalSumExp(String causeName, String text) {
      List totalSumPatternList = null;
      if(totalSumPatternMap.containsKey(causeName)) {
         totalSumPatternList = (List)totalSumPatternMap.get(causeName);
      } else {
         totalSumPatternList = (List)totalSumPatternMap.get("default");
      }

      if(totalSumPatternList != null) {
         Iterator var4 = totalSumPatternList.iterator();

         while(var4.hasNext()) {
            Pattern totalSumPattern = (Pattern)var4.next();
            Matcher matcher = totalSumPattern.matcher(text);
            if(matcher.find()) {
               return matcher.group();
            }
         }
      }

      return null;
   }
}
