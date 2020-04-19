package com.cjbdi.core.servercenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.extractcenter.BeanFactoryExtract;
import com.cjbdi.core.extractcenter.sentence.SentenceExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.List;
import java.util.concurrent.Callable;

public class ExtractCallable implements Callable {

   private DefendantModel defendantModel;
   private CasecauseModel casecauseModel;
   private String casecause;


   public ExtractCallable(DefendantModel defendantModel, CasecauseModel casecauseModel, String casecause) {
      this.defendantModel = defendantModel;
      this.casecauseModel = casecauseModel;
      this.casecause = casecause;
   }

   public Object call() {
      try {
         BeanFactoryExtract.sentenceExtractor.initExtractor.basicInfo(this.defendantModel, this.casecauseModel);
         List e = (BeanFactoryExtract.sentenceExtractor.initExtractor.getSentenceExtractors().get(this.casecause)).extractPublic(this.casecauseModel);
         e.addAll((BeanFactoryExtract.sentenceExtractor.initExtractor.getSentenceExtractors().get(this.casecause)).extractPrivate(this.defendantModel, this.casecauseModel));
         List plotList = (BeanFactoryExtract.sentenceExtractor.initExtractor.getSentenceExtractors().get("总则量刑情节")).extractPublic(this.casecauseModel);
         plotList.addAll((BeanFactoryExtract.sentenceExtractor.initExtractor.getSentenceExtractors().get("总则量刑情节")).extractPrivate(this.defendantModel, this.casecauseModel));
         List courtDecisionList = (BeanFactoryExtract.sentenceExtractor.initExtractor.getSentenceExtractors().get("判决结果")).extractPrivate(this.defendantModel, this.casecauseModel);
         this.defendantModel.setCasecauseFeatureMap(this.casecause, e);
         this.defendantModel.setShareFeatureMap(this.casecause, plotList);
         this.defendantModel.setCourtDecisionMap(this.casecause, courtDecisionList);
         JSONObject percaseFetaures = new JSONObject();
         percaseFetaures.put("caseCause", this.casecause);
         percaseFetaures.put("plotList", plotList);
         percaseFetaures.put("factsList", e);
         percaseFetaures.put("courtDecision", courtDecisionList);
         return percaseFetaures;
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
