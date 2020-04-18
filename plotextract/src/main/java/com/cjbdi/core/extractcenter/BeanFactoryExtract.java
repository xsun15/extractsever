package com.cjbdi.core.extractcenter;

import com.cjbdi.core.extractcenter.sentence.SentenceExtractor;

public class BeanFactoryExtract {

   public static SentenceExtractor sentenceExtractor;

   public static void init() {
      sentenceExtractor = new SentenceExtractor();
   }

}
