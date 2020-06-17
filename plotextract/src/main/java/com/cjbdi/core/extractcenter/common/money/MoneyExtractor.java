package com.cjbdi.core.extractcenter.common.money;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;

public class MoneyExtractor {
   private JSONObject moneyExtractor;

   public MoneyExtractor() {
      moneyExtractor = new JSONObject();
      for (String caseId : BeanFactoryConfig.extractFeatureConfig.getMoneyControl().keySet()) {
         String method = BeanFactoryConfig.extractFeatureConfig.getMoneyControl().getString(caseId);
         if (method.equals("rule")) {
            JSONObject caseMoney = BeanFactoryConfig.extractFeatureConfig.getCaseMoney().getJSONObject(caseId);
            moneyExtractor.put(caseId, new PRExtractMoney(caseMoney));
         } else if (method.equals("model")) {

         }
      }
   }
}
