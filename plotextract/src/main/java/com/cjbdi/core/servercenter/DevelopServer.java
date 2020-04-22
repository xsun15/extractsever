package com.cjbdi.core.servercenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.developcenter.Feature;
import com.cjbdi.core.developcenter.SentenceExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.utils.*;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;


@RestController
public class DevelopServer {
   @RequestMapping(value = "/develop/findmatchrule",produces = "application/json;charset=UTF-8")
    public String findMatchRule(@RequestBody JSONObject jsonParam,@Context HttpServletRequest request) {
        String fullText = jsonParam.getString("fullText");
        String casecause = jsonParam.getString("casecause");
        String filename = jsonParam.getString("filename");
        fullText = CleanText.run(fullText);
        if (StringUtils.isNotEmpty(fullText) && StringUtils.isNotEmpty(casecause)) {
            String ecasecause = BeanFactoryConfig.predCasecauseConfig.getCasecauseName().get(casecause);
            if (StringUtils.isNotEmpty(ecasecause)) {
                List<String> invalidRuleList = new ArrayList<>();
                List<String> list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get(ecasecause).get("firstrule");
                if (list != null && !list.isEmpty()) {
                    invalidRuleList.addAll(list);
                }
                list = BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract").getNegativepurerule().get("default").get("firstrule");
                if (list != null && !list.isEmpty()) {
                    invalidRuleList.addAll(list);
                }
                if (invalidRuleList!=null) {
                    List<String> rules = MatchRule.matchRules(fullText, invalidRuleList);
                    if (rules!=null&&rules.size()>0) {
                        JSONObject result = new JSONObject();
                        result.put("ismatch", "匹配");
                        result.put("rules", rules);
                        result.put("content", fullText);
                        result.put("filename", filename);
                        return Json.encodePrettily(result);
                    } else {
                        JSONObject result = new JSONObject();
                        result.put("ismatch", "不匹配");
                        result.put("content", fullText);
                        result.put("filename", filename);
                        return Json.encodePrettily(result);
                    }
                }
            }
        }
        return "";
    }

    @RequestMapping(value = "/develop/extract/money",produces = "application/json;charset=UTF-8")
    public String extractFixFeature(@RequestBody JSONObject jsonParam,@Context HttpServletRequest request) {
        String result = "";
        ExecutorService executor = Executors.newSingleThreadExecutor();
        FutureTask<String> future = new FutureTask<String>(
                new Callable<String>() {
                    public String call() {
                        try {
                            return extractFixFeature(jsonParam);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
        executor.execute(future);
        try {
            return future.get(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            future.cancel(true);
        } catch (ExecutionException e) {
            future.cancel(true);
        } catch (TimeoutException e) {
            future.cancel(true);
        } finally {
            executor.shutdown();
        }
        return result;
    }
    @RequestMapping(value = "/develop/extract/feature",produces = "application/json;charset=UTF-8")
    public String extractPeople(@RequestBody JSONObject jsonParam,@Context HttpServletRequest request) {
        String fullText =jsonParam.getString("fullText");
        String extractorType = jsonParam.getString("extractorType");
        String extractorFrom = jsonParam.getString("extractorTypeFrom");
        String code = jsonParam.getString("code");
        String isBool = jsonParam.getString("isBool");
        if (fullText != null &&  fullText.length() > 0){
            fullText = CleanText.run(fullText);
            JSONObject reqPara = new JSONObject();
            reqPara.put("fullText", fullText);
            String paraSplitter = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocsplit(), reqPara);
            if (paraSplitter!=null&& StringUtils.isNotEmpty(paraSplitter)) {
                JSONObject paraSplitterJson = JSONObject.parseObject(paraSplitter);


                    String casePortrait = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocportray(), reqPara);
                    //生成 defendantModel 和 casecauseModel
                    if (StringUtils.isNotEmpty(casePortrait)) {
                        JSONArray casePortraitJson = JSONArray.parseArray(casePortrait);
                        JSONObject defendantPortray = casePortraitJson.getJSONObject(0);
                        DefendantModel defendantModel = new DefendantModel(defendantPortray);
                        //保证单人单案
                        if (defendantModel.getDefendantNameSet()==null || defendantModel.getDefendantNameSet().size()!=1) return null;
                        if (defendantModel.getCasecauseSet()==null || defendantModel.getCasecauseSet().size()!=1) return null;

                        Set<String> casecauseSet = defendantModel.getCasecauseSet();
                        Map<String, CasecauseModel> casecauseModelMap = defendantModel.getCasecauseModelMap();
                        CasecauseModel casecauseModel =  null;
                        for (String casecause : casecauseSet) {
                             casecauseModel = casecauseModelMap.get(casecause);
                        }
                        Label label = null;
                        //保证用户传入的案由和系统提取的案由相同
                        if (casecauseModel.getCasecause().equals(jsonParam.getString("casecause"))){
                            label =  Feature.extract(defendantModel,casecauseModel,extractorType,extractorFrom,code);
                        }
                        if (label != null){
                            System.out.println("有效数据");
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.put("casecause",casecauseModel.getCasecause());
                            jsonObject.put("isaccurate","精确");
                            if (extractorFrom.contains("本院认为")){
                                jsonObject.put("automark",label.getValue());
                            }else if(extractorFrom.contains("经审理查明")){
                                jsonObject.put("extractMark",label.getValue());
                            }
                            return jsonObject.toString();
                        }
                    }else{

                        System.out.println("案件画像为空");

                    }


            }
            return "";
        } else {
            System.out.println("原始文本为空");
            return "";
        }
    }


    public String extractFixFeature(JSONObject jsonParam) {
        String fullText =jsonParam.getString("fullText");
        String purpose = jsonParam.getString("purpose");
        String filename = jsonParam.getString("filename");
        if (fullText != null &&  fullText.length() > 0){
            List<String> casecauseList = new ArrayList<>();
            fullText = CleanText.run(fullText);
            String docType = "刑事判决书";
            JSONObject reqPara = new JSONObject();
            reqPara.put("docType", docType);
            reqPara.put("fullText", fullText);
            String paraSplitter = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocsplit(), reqPara);
            if (paraSplitter!=null&& StringUtils.isNotEmpty(paraSplitter)) {
                //从背景描述段落中抽取被告人
                casecauseList = new ArrayList<>();
                JSONObject result = null;
                result = SentenceExtractor.extract(docType,fullText,casecauseList,purpose);
                if (result != null && !result.isEmpty()) {
                    return Json.encodePrettily(result);
                }
            }
            return "";
        } else {
            return "";
        }
    }


}
