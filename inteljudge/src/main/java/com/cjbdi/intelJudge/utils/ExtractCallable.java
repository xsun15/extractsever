package com.cjbdi.intelJudge.utils;

import com.cjbdi.intelJudge.configure.SampleModel;

import java.util.*;
import java.util.concurrent.Callable;

public class ExtractCallable implements Callable {

   private Set<String> targetKeyword;
   private List<SampleModel> sampleSet;
   private Map<String, Integer> retainWord;


   public ExtractCallable(Set<String> targetKeyword, List<SampleModel> sampleSet, Map<String, Integer> retainWord) {
      this.targetKeyword = targetKeyword;
      this.sampleSet = sampleSet;
      this.retainWord = retainWord;
   }

   public Object call() {
      try {
         Map<String, Double> similarities = new HashMap<>();
         for (SampleModel sampleModel : sampleSet) {
            List<String> keyword = sampleModel.getKeyword();
            List<String> interResult = new ArrayList<>(targetKeyword);
            interResult.retainAll(keyword);
            List<String> unionResult = new ArrayList<>(targetKeyword);
            unionResult.addAll(keyword);
            // 交集大小
            int interCount = 0;
            for (String word : interResult) {
               if (retainWord.containsKey(word)) {
                  interCount += retainWord.get(word);
               } else {
                  interCount += 1;
               }
            }
            // 并集大小
            int unionCount = 0;
            for (String word : unionResult) {
               if (retainWord.containsKey(word)) {
                  unionCount += retainWord.get(word);
               } else {
                  unionCount += 1;
               }
            }
            // 相似程度
            double similarity = 1.0* interCount / unionCount;
            similarities.put(sampleModel.getFilename(), similarity);
         }
         // 按相似度排序
         LinkedHashMap<String, Double> sortedMap = Tools.sortMapByValues(similarities);
         return sortedMap;
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }
}