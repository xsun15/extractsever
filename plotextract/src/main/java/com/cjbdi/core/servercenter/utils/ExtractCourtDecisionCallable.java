package com.cjbdi.core.servercenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.extractcenter.BeanFactoryExtract;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;

import java.util.List;
import java.util.concurrent.Callable;

public class ExtractCourtDecisionCallable implements Callable {

   private DefendantModel defendantModel;
   private CasecauseModel casecauseModel;
   private String casecause;


   public ExtractCourtDecisionCallable(DefendantModel defendantModel, CasecauseModel casecauseModel, String casecause) {
      this.defendantModel = defendantModel;
      this.casecauseModel = casecauseModel;
      this.casecause = casecause;
   }

   public Object call() {
      try {
         List courtDecisionList = (BeanFactoryExtract.sentenceExtractor.initExtractor.getSentenceExtractors().get("判决结果")).extractPrivate(this.defendantModel, this.casecauseModel);
         this.defendantModel.setCourtDecisionMap(this.casecause, courtDecisionList);
         JSONObject percaseFetaures = new JSONObject();
         percaseFetaures.put("caseCause", this.casecause);
         percaseFetaures.put("courtDecision", courtDecisionList);
         return percaseFetaures;
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
