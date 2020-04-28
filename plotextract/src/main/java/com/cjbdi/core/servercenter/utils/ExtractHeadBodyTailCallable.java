package com.cjbdi.core.servercenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.extractcenter.BeanFactoryExtract;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;

import java.util.List;
import java.util.concurrent.Callable;

public class ExtractHeadBodyTailCallable implements Callable {

   private DefendantModel defendantModel;
   private CasecauseModel casecauseModel;
   private String casecause;


   public ExtractHeadBodyTailCallable(DefendantModel defendantModel, CasecauseModel casecauseModel, String casecause) {
      this.defendantModel = defendantModel;
      this.casecauseModel = casecauseModel;
      this.casecause = casecause;
   }

   public Object call() {
      try {
         FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
         JSONObject percaseFetaures = new JSONObject();
         if (factTextConfig.isnormal) {
            percaseFetaures.put("caseCause", this.casecause);
            percaseFetaures.put("head", factTextConfig.header);
            percaseFetaures.put("body", factTextConfig.body);
            percaseFetaures.put("tail", factTextConfig.tail);
         }
         return percaseFetaures;
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
