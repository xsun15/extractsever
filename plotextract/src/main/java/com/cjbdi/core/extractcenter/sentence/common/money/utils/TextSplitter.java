package com.cjbdi.core.extractcenter.sentence.common.money.utils;

import com.google.common.base.Strings;

public class TextSplitter {

   static String SENTENCE_SPLIT_REG_EXP = "。|\n";
   static String PARA_SPLIT_REG_EXP = "\n";


   public static String[] split2paras(String fulltext) {
      return Strings.isNullOrEmpty(fulltext)?null:fulltext.split(PARA_SPLIT_REG_EXP);
   }

   public static String[] split2sentences(String fulltext) {
      return Strings.isNullOrEmpty(fulltext)?null:fulltext.split(SENTENCE_SPLIT_REG_EXP);
   }

   public static String[] split2sentences(String fulltext, String splitRex) {
      return Strings.isNullOrEmpty(fulltext)?null:fulltext.split(splitRex);
   }

}
