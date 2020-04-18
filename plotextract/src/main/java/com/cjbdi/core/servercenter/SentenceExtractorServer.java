package com.cjbdi.core.servercenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.convertlabelcenter.ConvertLabelFactory;
import com.cjbdi.core.convertlabelcenter.utils.ToLeianV1;
import com.cjbdi.core.convertlabelcenter.utils.ToSelfSentence;
import com.cjbdi.core.developcenter.good.ExtractGood;
import com.cjbdi.core.extractcenter.BeanFactoryExtract;
import com.cjbdi.core.extractcenter.utils.CleanText;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.servercenter.utils.Tools;
import io.vertx.core.json.Json;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentenceExtractorServer {

   @RequestMapping(
      value = {"/extract/good"},
      produces = {"application/json;charset=UTF-8"}
   )
   public String extractGood(@RequestBody JSONObject jsonParam, @Context HttpServletRequest request) {
      if(jsonParam.containsKey("fullText")) {
         String fullText = jsonParam.getString("fullText");
         String result = ExtractGood.run(fullText);
         return result;
      } else {
         return "";
      }
   }

   @RequestMapping(
      value = {"/split/document"},
      produces = {"application/json;charset=UTF-8"}
   )
   public String split(@RequestBody JSONObject jsonParam, @Context HttpServletRequest request) {
      if(jsonParam.containsKey("fullText")) {
         String fullText = jsonParam.getString("fullText");
         if(StringUtils.isNotEmpty(fullText)) {
            fullText = Tools.clean(fullText);
            String docType = "";
            if(jsonParam.containsKey("docType")) {
               docType = jsonParam.getString("docType");
            } else {
               docType = Tools.extractDocType(fullText);
            }

            if(StringUtils.isEmpty(docType)) {
               return "";
            }

            JSONObject reqPara = new JSONObject();
            reqPara.put("docType", docType);
            reqPara.put("fullText", fullText);
            String paraSplitter = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocsplitraw(), reqPara);
            return paraSplitter;
         }
      }

      return "";
   }

   @RequestMapping(
      value = {"/extract/sentence/feature/selfsentence"},
      produces = {"application/json;charset=UTF-8"}
   )
   public JSONObject extractSelfSentenceFeature(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
      if(reqParam.containsKey("fullText")) {
         String fullText = reqParam.getString("fullText");
         if(org.apache.commons.lang3.StringUtils.isNotEmpty(fullText)) {
            fullText = CleanText.run(fullText);
            String docType = "";
            if(reqParam.containsKey("docType")) {
               docType = reqParam.getString("docType");
            } else {
               docType = Tools.extractDocType(fullText);
            }

            if(org.apache.commons.lang3.StringUtils.isNotEmpty(docType)) {
               List casecauseList = (List)reqParam.getObject("casecause", List.class);
               ToSelfSentence var10000;
               JSONArray jsonArray;
               if(casecauseList != null && casecauseList.size() != 0) {
                  jsonArray = BeanFactoryExtract.sentenceExtractor.extract(docType, fullText, casecauseList);
                  var10000 = ConvertLabelFactory.toSelfSentence;
                  jsonArray = ToSelfSentence.run(jsonArray);
                  return Tools.packingResult("200", jsonArray);
               } else {
                  jsonArray = BeanFactoryExtract.sentenceExtractor.extract(docType, fullText, casecauseList);
                  var10000 = ConvertLabelFactory.toSelfSentence;
                  jsonArray = ToSelfSentence.run(jsonArray);
                  return Tools.packingResult("200", jsonArray);
               }
            } else {
               return Tools.packingResult("500", "不支持该类型文书解析");
            }
         } else {
            return Tools.packingResult("500", "文书内容为空");
         }
      } else {
         return Tools.packingResult("500", "请求参数缺少fullText");
      }
   }

   @RequestMapping(
      value = {"/extract/sentence/feature/leianv1"},
      produces = {"application/json;charset=UTF-8"}
   )
   public String extractLeianV1Feature(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
      if(reqParam.containsKey("fullText")) {
         String fullText = reqParam.getString("fullText");
         if(org.apache.commons.lang3.StringUtils.isNotEmpty(fullText)) {
            fullText = CleanText.run(fullText);
            String docType = "";
            if(reqParam.containsKey("docType")) {
               docType = reqParam.getString("docType");
            } else {
               docType = Tools.extractDocType(fullText);
            }

            if(org.apache.commons.lang3.StringUtils.isNotEmpty(docType)) {
               List casecauseList = (List)reqParam.getObject("casecause", List.class);
               ToLeianV1 var10000;
               JSONArray jsonArray;
               String result;
               if(casecauseList != null && casecauseList.size() == 1) {
                  jsonArray = BeanFactoryExtract.sentenceExtractor.extract(docType, fullText, casecauseList);
                  var10000 = ConvertLabelFactory.toLeianV1;
                  result = ToLeianV1.run(jsonArray);
                  return result;
               }

               jsonArray = BeanFactoryExtract.sentenceExtractor.extract(docType, fullText, casecauseList);
               if(jsonArray != null && jsonArray.size() == 1) {
                  var10000 = ConvertLabelFactory.toLeianV1;
                  result = ToLeianV1.run(jsonArray);
                  return result;
               }
            }
         }
      }

      return "";
   }

   @RequestMapping(
      value = {"/extract/sentence/feature"},
      produces = {"application/json;charset=UTF-8"}
   )
   public String extractSentenceFeature(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
      long startTime1 = System.currentTimeMillis();
      if(reqParam.containsKey("fullText")) {
         String fullText = reqParam.getString("fullText");
         if(org.apache.commons.lang3.StringUtils.isNotEmpty(fullText)) {
            fullText = CleanText.run(fullText);
            String docType = "";
            if(reqParam.containsKey("docType")) {
               docType = reqParam.getString("docType");
            } else {
               docType = Tools.extractDocType(fullText);
            }

            if(org.apache.commons.lang3.StringUtils.isNotEmpty(docType)) {
               List casecauseList = (List)reqParam.getObject("casecause", List.class);
               JSONArray jsonArray;
               if(casecauseList != null && casecauseList.size() > 0) {
                  jsonArray = BeanFactoryExtract.sentenceExtractor.extract(docType, fullText, casecauseList);
                  return Json.encodePrettily(jsonArray);
               }

               jsonArray = BeanFactoryExtract.sentenceExtractor.extract(docType, fullText, casecauseList);
               long endTime1 = System.currentTimeMillis();
               System.out.println("代码运行时间：" + (endTime1 - startTime1) + "ms");
               return Json.encodePrettily(jsonArray);
            }
         }
      }

      return "";
   }

   @RequestMapping(
      value = {"/predict/basicInfo"},
      produces = {"application/json;charset=UTF-8"}
   )
   public String predBasicInfo(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
      String result = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getBasicinfo(), reqParam);
      return result;
   }
}
