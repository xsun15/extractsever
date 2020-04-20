package com.cjbdi.core.service;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.develop.FactCrime;
import com.cjbdi.core.extractcenter.BeanExtractCenter;
import com.cjbdi.core.extractcenter.extract.CasePortrait;
import com.cjbdi.core.extractcenter.extract.CaseSplit;
import com.cjbdi.core.extractcenter.model.*;
import com.cjbdi.core.util.CommonTools;
import com.cjbdi.core.util.RemoveSpecialChar;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class Service extends RemoveSpecialChar {
    @RequestMapping(value = "/document/split/raw", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String splitraw(@RequestBody JSONObject jsonParam, @Context HttpServletRequest request) {
        if (jsonParam.containsKey("fullText")) {
            String fullText = jsonParam.getString("fullText");
            if (StringUtils.isNotEmpty(fullText)) {
                fullText = clean(fullText);
                String docType = "";
                if (jsonParam.containsKey("docType")) docType = jsonParam.getString("docType");
                else docType = CommonTools.extractDocType(fullText);
                if (StringUtils.isEmpty(docType)) return "";
                if (docType.equals("刑事判决书")) {
                    FirstTrialModel firstTrialModel= BeanExtractCenter.firstTrialSplit.split(fullText);
                    return JSONObject.toJSONString(firstTrialModel);
                } else if (docType.equals("起诉书")) {
                    IndicitmentModel indicitmentModel = BeanExtractCenter.indicitmentSplit.split(fullText);
                    return JSONObject.toJSONString(indicitmentModel);
                } else if (docType.equals("审理报告")) {
                    TrialReportModel trialReportModel = BeanExtractCenter.trialReportSplit.split(fullText);
                    return JSONObject.toJSONString(trialReportModel);
                }
            }
        }
        return "";
    }

    @RequestMapping(value = "/document/split", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String split(@RequestBody JSONObject jsonParam, @Context HttpServletRequest request) {
        if (jsonParam.containsKey("fullText")) {
            String fullText = jsonParam.getString("fullText");
            if (StringUtils.isNotEmpty(fullText)) {
                fullText = clean(fullText);
                String docType = "";
                if (jsonParam.containsKey("docType")) docType = jsonParam.getString("docType");
                else docType = CommonTools.extractDocType(fullText);
                if (StringUtils.isEmpty(docType)) return "";
                if (docType.equals("刑事判决书")) {
                    FirstTrialModel firstTrialModel = BeanExtractCenter.firstTrialSplit.split(fullText);
                    return JSONObject.toJSONString(CaseSplit.run(firstTrialModel));
                } else if (docType.equals("起诉书")) {
                    IndicitmentModel indicitmentModel = BeanExtractCenter.indicitmentSplit.split(fullText);
                    return JSONObject.toJSONString(CaseSplit.run(indicitmentModel));
                } else if (docType.equals("审理报告")) {
                    TrialReportModel trialReportModel = BeanExtractCenter.trialReportSplit.split(fullText);
                    return JSONObject.toJSONString(trialReportModel);
                }
            }
        }
        return "";
    }

    @RequestMapping(value = "/document/genfactcrime", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String genFactCrime(@RequestBody JSONObject jsonParam, @Context HttpServletRequest request) {
        String filename = clean(jsonParam.getString("filename"));
        String docType = clean(jsonParam.get("docType").toString());
        String content = "";
        if(docType.equals("审理报告")) content = cleanTrialReportModel(jsonParam.get("content").toString());
        else content = clean(jsonParam.get("content").toString());
        if (docType.equals("刑事判决书")) {
            FirstTrialModel firstTrialModel = BeanExtractCenter.firstTrialSplit.split(content);
            JSONObject jsonObject = FactCrime.run(firstTrialModel.getAccuse());
            jsonObject.putAll(FactCrime.run(firstTrialModel.getJustice()));
            if (jsonObject!=null&&jsonObject.size()>0) {
                jsonObject.put("filename", filename);
                return jsonObject.toString();
            }
            return "";
        } else if (docType.equals("起诉书")){
            //分段
            IndicitmentModel indicitmentModel = BeanExtractCenter.indicitmentSplit.split(content);
            JSONObject jsonObject = FactCrime.run(indicitmentModel.getJustice());
            return jsonObject.toString();
        } else if (docType.equals("审理报告")){
            TrialReportModel trialReportModel = BeanExtractCenter.trialReportSplit.split(content);
            JSONObject jsonObject = FactCrime.run(trialReportModel.getCaseTrial());
            return jsonObject.toString();
        }
        return "";
    }

    @RequestMapping(value = "/document/select", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Boolean selectPaper(@RequestBody JSONObject jsonParam, @Context HttpServletRequest request) {
        if (jsonParam.containsKey("fullText")) {
            String fullText = jsonParam.getString("fullText");
            if (StringUtils.isNotEmpty(fullText)) {
                fullText = clean(fullText);
                String docType = "";
                if (jsonParam.containsKey("docType")) docType = jsonParam.getString("docType");
                else docType = CommonTools.extractDocType(fullText);
                if (StringUtils.isEmpty(docType)) return false;
                else if (docType.equals("刑事判决书")) {
                    FirstTrialModel firstTrialModel = BeanExtractCenter.firstTrialSplit.split(fullText);
                    Set<String> defendantSet = BeanExtractCenter.defendantExtract.extract(firstTrialModel.getDefendant());
                    List<String> caseList = BeanExtractCenter.firstTrialSplit.findCasecause(firstTrialModel.getDefendant(), firstTrialModel.getCourtOpinion(), firstTrialModel.getJustice());
                    if (defendantSet!=null&&defendantSet.size()>1&&caseList!=null&&caseList.size()>1) {
                        return true;
                    }
                    return false;
                } else if (docType.equals("起诉书")) {
                    //分段
                    IndicitmentModel indicitmentModel = BeanExtractCenter.indicitmentSplit.split(fullText);
                    //从被告人段中得到所有被告人
                    Set<String> defendantSet = BeanExtractCenter.defendantExtract.extract(indicitmentModel.getDefendant());
                    //抽取案由
                    List<String> caseList = BeanExtractCenter.indicitmentSplit.findCasecause(indicitmentModel.getDefendant(), indicitmentModel.getProcuOpinion(), indicitmentModel.getJustice());
                    if (defendantSet!=null&&defendantSet.size()>1&&caseList!=null&&caseList.size()>1) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    @RequestMapping(value = "/document/portrait", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String portrait(@RequestBody JSONObject jsonParam, @Context HttpServletRequest request) {

        if (jsonParam.containsKey("fullText")) {
            String fullText = jsonParam.getString("fullText");
            if (StringUtils.isNotEmpty(fullText)) {
                fullText = clean(fullText);
                String docType = "";
                if (jsonParam.containsKey("docType")) docType = jsonParam.getString("docType");
                else docType = CommonTools.extractDocType(fullText);
                if (StringUtils.isEmpty(docType)) return "";
                else if (docType.equals("刑事判决书")) {
                    FirstTrialModel firstTrialModel = BeanExtractCenter.firstTrialSplit.split(fullText);
                    Set<String> defendantSet = BeanExtractCenter.defendantExtract.extract(firstTrialModel.getDefendant());
                    List<String> caseList = BeanExtractCenter.firstTrialSplit.findCasecause(firstTrialModel.getDefendant(), firstTrialModel.getCourtOpinion(), firstTrialModel.getJustice());
                    List<DefendantModel> defendantModelList = CasePortrait.run(firstTrialModel.getCourtOpinion(), firstTrialModel.getJustice(), firstTrialModel.getAccuse(),
                            firstTrialModel.getSue(), firstTrialModel.getDefendant(), defendantSet, caseList);
                    return JSONObject.toJSONString(defendantModelList);
                } else if (docType.equals("起诉书")) {
                    //分段
                    IndicitmentModel indicitmentModel = BeanExtractCenter.indicitmentSplit.split(fullText);
                    //从被告人段中得到所有被告人
                    Set<String> defendantSet = BeanExtractCenter.defendantExtract.extract(indicitmentModel.getDefendant());
                    //抽取案由
                    List<String> caseList = BeanExtractCenter.indicitmentSplit.findCasecause(indicitmentModel.getDefendant(), indicitmentModel.getProcuOpinion(), indicitmentModel.getJustice());
                    List<DefendantModel> defendantModelList = CasePortrait.run(indicitmentModel.getProcuOpinion(), indicitmentModel.getJustice(), "",
                            indicitmentModel.getInvestigate(), indicitmentModel.getDefendant(), defendantSet, caseList);
                    return JSONObject.toJSONString(defendantModelList);
                } else if (docType.equals("不起诉决定书")) {
                    //分段
                    NoprosModel noprosModel = BeanExtractCenter.noprosSplit.split(fullText);
                    //从被告人段中得到所有被告人
                    Set<String> defendantSet = BeanExtractCenter.defendantExtract.extract(noprosModel.getDefendant());
                    //调用模型预测案由
                    List<String> caseList = BeanExtractCenter.noprosSplit.findCasecause(noprosModel.getDefendant(), noprosModel.getProcuOpinion(), noprosModel.getJustice());
                    return "";
                }
            }
        }
        return "";
    }


    @RequestMapping(value = "/predict/basicInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String predBasicInfo(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
        if (reqParam.containsKey("fullText")) {
            String fullText = reqParam.getString("fullText");
            if (StringUtils.isNotEmpty(fullText)) {
                fullText = clean(fullText);
                String docType = "";
                if (reqParam.containsKey("docType")) docType = reqParam.getString("docType");
                else docType = CommonTools.extractDocType(fullText);
                if (StringUtils.isEmpty(docType)) return "";
                String title = "";
                String caseID = "";
                Set<String> defendantSet = new HashSet<>();
                List<String> caseList = new ArrayList<>();
                if (docType.equals("刑事判决书")) {
                    FirstTrialModel firstTrialModel = BeanExtractCenter.firstTrialSplit.split(fullText);
                    caseID = firstTrialModel.getCourtCaseId();
                    defendantSet = BeanExtractCenter.defendantExtract.extract(firstTrialModel.getDefendant());
                    caseList = BeanExtractCenter.firstTrialSplit.findCasecause(firstTrialModel.getDefendant(), firstTrialModel.getCourtOpinion(), firstTrialModel.getJustice());
                } else if (docType.equals("起诉书")) {
                    IndicitmentModel indicitmentModel = BeanExtractCenter.indicitmentSplit.split(fullText);
                    caseID = indicitmentModel.getProcuCaseId();
                    //从被告人段中得到所有被告人
                    defendantSet = BeanExtractCenter.defendantExtract.extract(indicitmentModel.getDefendant());
                    //抽取案由
                    caseList = BeanExtractCenter.indicitmentSplit.findCasecause(indicitmentModel.getDefendant(), indicitmentModel.getProcuOpinion(), indicitmentModel.getJustice());
                }
                for (String defendant : defendantSet) {
                    title = title + defendant + "、";
                }
                title = title.substring(0, title.length()-1) + CommonTools.list2Str(caseList).replaceAll("罪", "") + "案";
                JSONObject resultJson = new JSONObject();
                resultJson.put("caseID", caseID);
                resultJson.put("casecasue", caseList);
                resultJson.put("caseTitle", title);
                resultJson.put("docType", docType);
                return resultJson.toString();
            }
        }
        return "";
    }
}
