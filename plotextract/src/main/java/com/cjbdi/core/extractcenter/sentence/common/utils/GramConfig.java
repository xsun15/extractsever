package com.cjbdi.core.extractcenter.sentence.common.utils;


public class GramConfig {

   public double value = 0.0D;
   public String target;
   public String context;
   public String sentence = "";
   public String longsentence = "";
   public int start;
   public int end;
   public int startcolor;
   public int endcolor;


   public boolean compare(GramConfig gramConfig) {
      return gramConfig != null && this.value == gramConfig.value;
   }
}
