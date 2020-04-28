package com.cjbdi.core.extractcenter.sentence;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.extractcenter.checklabel.CheckLabel;
import com.cjbdi.core.extractcenter.sentence.InitExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.CleanText;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.servercenter.utils.AssignAllFeatureTaskLevelFirst;
import com.cjbdi.core.servercenter.utils.AssignCourtDecisionTask;
import com.cjbdi.core.servercenter.utils.AssignHeadBodyTailTask;
import com.cjbdi.core.servercenter.utils.ExtractCallable;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;

public class SentenceExtractor {

   private static Logger logger = LoggerFactory.getLogger(SentenceExtractor.class);
   public static InitExtractor initExtractor;


   public static void init() {
      initExtractor = new InitExtractor();
   }

   public JSONArray extract(String docType, String doc, List cases) {
      doc = CleanText.run(doc);
      JSONObject reqPara = new JSONObject();
      reqPara.put("docType", docType);
      reqPara.put("fullText", doc);
      String casePortrait = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocportray(), reqPara);
      if(!StringUtils.isNotEmpty(casePortrait)) {
         return null;
      } else {
         JSONArray casePortraitJson = JSONArray.parseArray(casePortrait);
         AssignAllFeatureTaskLevelFirst assignAllFeatureTaskLevelFirst = new AssignAllFeatureTaskLevelFirst();
         JSONArray caseDeepPortrait = assignAllFeatureTaskLevelFirst.extract(casePortraitJson);
         return caseDeepPortrait;
      }
   }

   public JSONArray extractCourtDecision(String docType, String doc, List cases) {
      doc = CleanText.run(doc);
      JSONObject reqPara = new JSONObject();
      reqPara.put("docType", docType);
      reqPara.put("fullText", doc);
      String casePortrait = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocportray(), reqPara);
      if(!StringUtils.isNotEmpty(casePortrait)) {
         return null;
      } else {
         JSONArray casePortraitJson = JSONArray.parseArray(casePortrait);
         AssignCourtDecisionTask assignCourtDecisionTask = new AssignCourtDecisionTask();
         JSONArray caseDeepPortrait = assignCourtDecisionTask.extract(casePortraitJson);
         return caseDeepPortrait;
      }
   }

   public JSONArray extractHeadBodyTail(String docType, String doc, List cases) {
      doc = CleanText.run(doc);
      JSONObject reqPara = new JSONObject();
      reqPara.put("docType", docType);
      reqPara.put("fullText", doc);
      String casePortrait = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocportray(), reqPara);
      if(!StringUtils.isNotEmpty(casePortrait)) {
         return null;
      } else {
         JSONArray casePortraitJson = JSONArray.parseArray(casePortrait);
         AssignHeadBodyTailTask assignHeadBodyTailTask = new AssignHeadBodyTailTask();
         JSONArray caseDeepPortrait = assignHeadBodyTailTask.extract(casePortraitJson);
         return caseDeepPortrait;
      }
   }

}
