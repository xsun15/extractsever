package com.cjbdi.test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;

public class Dependency {

   public static void main(String[] args) {
      CoNLLSentence sentence = HanLP.parseDependency("将其放在豫康新城南区17栋622宿舍床头的一部VIVO X7手机盗走");
      System.out.println(sentence);
      CoNLLWord[] wordArray = sentence.getWordArray();

      for(int head = wordArray.length - 1; head >= 0; --head) {
         CoNLLWord word = wordArray[head];
         System.out.printf("%s --(%s)--> %s\n", new Object[]{word.LEMMA, word.DEPREL, word.HEAD.LEMMA});
      }

      CoNLLWord var5 = wordArray[13];

      while((var5 = var5.HEAD) != null) {
         System.out.printf("%s --(%s)--> ", new Object[]{var5.LEMMA, var5.DEPREL});
      }

   }
}
