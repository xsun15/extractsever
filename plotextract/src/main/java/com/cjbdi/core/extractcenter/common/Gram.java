package com.cjbdi.core.extractcenter.common;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureModel;
import com.cjbdi.core.configcenter.extractfeature.money.ExtractMoneyModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Gram {
   private JSONObject gram;
   private List<Pattern> negativeRule;
   private List<Pattern> positive1thRule;
   private List<Pattern> positive2thRule;
   private List<Pattern> positive3thRule;

   public Gram (JSONObject gram, ExtractFeatureModel extractFeatureModel) {
      this.gram = gram;
      this.positive1thRule = new ArrayList<>();
      this.positive2thRule = new ArrayList<>();
      this.positive3thRule = new ArrayList<>();
      this.negativeRule = new ArrayList<>();
      if (gram.containsKey("positivePureRule")) {
         if (!gram.getJSONObject("positivePureRule").isEmpty()) {
            if (gram.getJSONObject("positivePureRule").containsKey("1th"))
               positive1thRule.addAll(gram.getJSONObject("positivePureRule").getObject("1th", ExtractMoneyModel.class).getRule());
         }
      }
      if (gram.containsKey("positivePureRule")) {
         if (!gram.getJSONObject("positivePureRule").isEmpty()) {
            if (gram.getJSONObject("positivePureRule").containsKey("2th"))
               positive2thRule.addAll(gram.getJSONObject("positivePureRule").getObject("2th", ExtractMoneyModel.class).getRule());
         }
      }
      if (gram.containsKey("positivePureRule")) {
         if (!gram.getJSONObject("positivePureRule").isEmpty()) {
            if (gram.getJSONObject("positivePureRule").containsKey("3th"))
               positive3thRule.addAll(gram.getJSONObject("positivePureRule").getObject("3th", ExtractMoneyModel.class).getRule());
         }
      }
      if (gram.containsKey("negativePureRule")) {
         if (!gram.getJSONObject("negativePureRule").isEmpty()) {
            if (gram.getJSONObject("negativePureRule").containsKey("1th"))
               negativeRule.addAll(gram.getJSONObject("negativePureRule").getObject("1th", ExtractMoneyModel.class).getRule());
            if (gram.getJSONObject("negativePureRule").containsKey("2th"))
               negativeRule.addAll(gram.getJSONObject("negativePureRule").getObject("2th", ExtractMoneyModel.class).getRule());
         }
      }
   }
}
