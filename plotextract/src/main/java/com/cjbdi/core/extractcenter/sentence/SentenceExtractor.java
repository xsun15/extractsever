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
         JSONArray caseDeepPortrait = new JSONArray();
         int threadCount = 0;

         for(int pool = 0; pool < casePortraitJson.size(); ++pool) {
            JSONObject taskModelList = casePortraitJson.getJSONObject(pool);
            DefendantModel i = new DefendantModel(taskModelList);
            Set taskModel = i.getCasecauseSet();
            threadCount += taskModel.size();
         }

         ExecutorService var22 = Executors.newFixedThreadPool(threadCount);
         ArrayList var23 = new ArrayList();

         for(int var24 = 0; var24 < casePortraitJson.size(); ++var24) {
            JSONObject var26 = casePortraitJson.getJSONObject(var24);
            DefendantModel defendantName = new DefendantModel(var26);
            String completeFetaures = defendantName.getName();
            Set defendantDeepPortrait = defendantName.getCasecauseSet();
            SentenceExtractor.TaskModel future = new SentenceExtractor.TaskModel();
            future.defendantName = completeFetaures;
            Iterator e = defendantDeepPortrait.iterator();

            while(e.hasNext()) {
               String casecause = (String)e.next();
               CasecauseModel casecauseModel = (CasecauseModel)defendantName.getCasecauseModelMap().get(casecause);
               future.futureList.add(var22.submit(new ExtractCallable(defendantName, casecauseModel, casecause)));
            }

            var23.add(future);
         }

         Iterator var25 = var23.iterator();

         while(var25.hasNext()) {
            SentenceExtractor.TaskModel var27 = (SentenceExtractor.TaskModel)var25.next();
            String var28 = var27.defendantName;
            JSONArray var29 = new JSONArray();
            Iterator var30 = var27.futureList.iterator();

            while(var30.hasNext()) {
               Future var32 = (Future)var30.next();

               try {
                  JSONObject var33 = JSONObject.parseObject(var32.get().toString());
                  var29.add(var33);
               } catch (InterruptedException var20) {
                  var20.printStackTrace();
               } catch (ExecutionException var21) {
                  var21.printStackTrace();
               }
            }

            JSONObject var31 = new JSONObject();
            var31.put("accusedName", var28);
            var31.put("completeList", var29);
            caseDeepPortrait.add(var31);
         }

         var22.shutdown();
         caseDeepPortrait = CheckLabel.run(caseDeepPortrait);
         return caseDeepPortrait;
      }
   }


   class TaskModel {

      public String defendantName;
      public List futureList = new ArrayList();


   }
}
