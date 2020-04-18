package com.cjbdi.test;

import com.cjbdi.core.extractcenter.sentence.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.SentenceExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import java.util.concurrent.ExecutionException;

public class boolDevelopTest {

   public static void main(String[] args) throws ExecutionException, InterruptedException {
      new InitExtractor();
      LabelExtractor labelExtractor = (LabelExtractor) SentenceExtractor.initExtractor.getSentenceExtractors().get("交通肇事罪");
      boolean a = true;
   }
}
