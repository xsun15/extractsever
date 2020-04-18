package com.cjbdi.core.extractcenter.sentence.common.money.utils;

import com.cjbdi.core.extractcenter.sentence.common.money.utils.InvalidPriceValueChecker;
import com.cjbdi.core.extractcenter.sentence.common.money.utils.TotalSumPatternProc;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class SumExtractor {

   private InvalidPriceValueChecker invalidPriceValueChecker = new InvalidPriceValueChecker();
   private TotalSumPatternProc totalSumPatternProc = new TotalSumPatternProc();
   static Map singlePreExpMap;
   static Map singlePrePatternMap;
   static Map totalSumExpMap;
   static Map totalSumPatternMap;


   public static void setSumPreExpMap(Map sumPreExpMap) {
      singlePreExpMap = sumPreExpMap;
      singlePrePatternMap = new HashMap();
      Set causeNameSet = sumPreExpMap.keySet();
      Iterator var2 = causeNameSet.iterator();

      while(var2.hasNext()) {
         String causeName = (String)var2.next();
         List expList = (List)sumPreExpMap.get(causeName);
         if(expList != null) {
            ArrayList patternList = new ArrayList();
            Iterator var6 = expList.iterator();

            while(var6.hasNext()) {
               String exp = (String)var6.next();
               Pattern pattern = Pattern.compile(exp);
               patternList.add(pattern);
            }

            singlePrePatternMap.put(causeName, patternList);
         }
      }

   }

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

}
