package com.cjbdi.core.extractcenter.sentence.utils;

import java.util.Map;

public interface ParaSplitter {

   Map split(String var1);

   String getParaText(int var1);

   public interface ParaEnum {

      int ORG_TEXT = 0;
      int TITLE_TEXT = 1;
      int BACKGROUND_TEXT = 2;
      int CASE_TEXT = 3;
      int PUBLIC_PROSECUTION_TEXT = 4;
      int PLEA_TEXT = 5;
      int FACT_TEXT = 6;
      int EVIDENCE_TEXT = 7;
      int CONCLUSION_TEXT = 8;
      int APPEND_TEXT = 9;

   }
}
