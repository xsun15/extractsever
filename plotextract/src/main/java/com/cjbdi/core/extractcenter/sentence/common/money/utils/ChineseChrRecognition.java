package com.cjbdi.core.extractcenter.sentence.common.money.utils;

import com.google.common.base.Strings;
import java.lang.Character.UnicodeBlock;

public class ChineseChrRecognition {

   public static char[] CHINESE_NUMBER_ARRAY = new char[]{'\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d', '\u4e03', '\u516b', '\u4e5d', '\u5341', '\u767e', '\u5343', '\u4e07', '\u96f6'};


   public static boolean isChineseStr(String str) {
      if(Strings.isNullOrEmpty(str)) {
         return false;
      } else {
         for(int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if(isChineseChar(c)) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isChineseChar(char c) {
      UnicodeBlock ub = UnicodeBlock.of(c);
      return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || ub == UnicodeBlock.GENERAL_PUNCTUATION;
   }

   public static boolean isChineseNumber(char c) {
      for(int i = 0; i < CHINESE_NUMBER_ARRAY.length; ++i) {
         if(CHINESE_NUMBER_ARRAY[i] == c) {
            return true;
         }
      }

      return false;
   }

}
