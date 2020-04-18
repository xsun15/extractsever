package com.cjbdi.core.extractcenter.sentence.common.money.utils;

import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

public class InvalidPriceValueChecker {

   public String parseInvalidSumValueStr(String causeName, String text, String defendant, Set defendants, List Patternlist) {
      String result = "";
      String[] para = text.split("\n");

      for(int j = 0; j < para.length; ++j) {
         String[] sentences = para[j].split("。");

         for(int i = 0; i < sentences.length; ++i) {
            String sentence = sentences[i];
            sentence = MatchRule.cleanMatchText(sentence, Patternlist);
            if(sentence.contains("元")) {
               if(sentence.contains(defendant)) {
                  result = result + sentence + "。";
               } else {
                  boolean flag = true;
                  Iterator var13 = defendants.iterator();

                  while(var13.hasNext()) {
                     String name = (String)var13.next();
                     if(!name.equals(defendant) && sentence.contains(name)) {
                        flag = false;
                        break;
                     }
                  }

                  if(flag) {
                     result = result + sentence + "。";
                  }
               }
            } else {
               result = result + sentence + "。";
            }
         }

         if(StringUtils.isNotEmpty(result) && result.length() > 0 && StringUtils.isNotEmpty(para[j]) && para[j].length() > 0 && result.substring(result.length() - 1).equals("。") && !para[j].substring(para[j].length() - 1).equals("。")) {
            result = result.substring(0, result.length() - 1);
         }

         result = result + "\n";
      }

      return result;
   }
}
