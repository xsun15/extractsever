package com.cjbdi.core.extractcenter.sentence.common.money;


public class MoneyConfig {

   public double value = 0.0D;
   public String target;
   public String context;
   public int start;
   public int end;
   public int startRegx;
   public int endRegx;
   public String para;
   public int indexinfact;
   public String isaccurate = "精确";
   public String sentence = "";
   public String oneMoneySentence;
   public int startColor;
   public int endColor;
   public String moneyType;
   public int paraindex;
   public boolean isconvert = false;
   public String longSentence = "";
   public double ratio = 1.0D;
   public boolean iscash = false;
   public boolean isadd = false;


   public boolean compare(MoneyConfig moneyConfig) {
      return moneyConfig != null && this.value == moneyConfig.value;
   }
}
