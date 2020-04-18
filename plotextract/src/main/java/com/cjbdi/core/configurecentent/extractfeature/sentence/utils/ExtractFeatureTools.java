package com.cjbdi.core.configurecentent.extractfeature.sentence.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ExtractFeatureTools {

   public static List toPattern(List list) {
      if(list != null && !list.isEmpty()) {
         ArrayList patternList = new ArrayList();
         Iterator var2 = list.iterator();

         while(var2.hasNext()) {
            String rule = (String)var2.next();
            patternList.add(Pattern.compile(rule));
         }

         return patternList;
      } else {
         return new ArrayList();
      }
   }

   public static LinkedHashMap toPattern(LinkedHashMap hashMap) {
      if(hashMap != null && !hashMap.isEmpty()) {
         LinkedHashMap hashMapPattern = new LinkedHashMap();
         Iterator var2 = hashMap.keySet().iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            List ruleList = (List)hashMap.get(key);
            hashMapPattern.put(key, toPattern(ruleList));
         }

         return hashMapPattern;
      } else {
         return new LinkedHashMap();
      }
   }

   public static LinkedHashMap toPatternMap2(LinkedHashMap hashMap) {
      if(hashMap != null && !hashMap.isEmpty()) {
         LinkedHashMap hashMapPattern = new LinkedHashMap();
         Iterator var2 = hashMap.keySet().iterator();

         while(var2.hasNext()) {
            String key = (String)var2.next();
            LinkedHashMap ruleList = (LinkedHashMap)hashMap.get(key);
            hashMapPattern.put(key, toPattern(ruleList));
         }

         return hashMapPattern;
      } else {
         return new LinkedHashMap();
      }
   }
}
