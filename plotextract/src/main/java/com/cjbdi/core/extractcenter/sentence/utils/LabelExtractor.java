package com.cjbdi.core.extractcenter.sentence.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class LabelExtractor {

   protected String modelUrl;
   protected Map basicPureRuleExtracts;
   protected Map basicModelRuleExtracts;


   public List extractPublic(CasecauseModel casecauseModel) {
      if(casecauseModel == null) {
         return new ArrayList(0);
      } else {
         ArrayList labelList = new ArrayList();
         Iterator reqParam = this.basicPureRuleExtracts.keySet().iterator();

         String responseStr;
         while(reqParam.hasNext()) {
            responseStr = (String)reqParam.next();
            String reponseJson = casecauseModel.getOpinion();
            PublicFeatureExtract publicFeatureExtract = (PublicFeatureExtract)this.basicPureRuleExtracts.get(responseStr);
            Label code = publicFeatureExtract.doExtract(reponseJson);
            if(code != null) {
               code.setCasecause(casecauseModel.getCasecause());
               code.setPrioritylevel("1");
               code.setParaText(casecauseModel.getOpinion());
               code.setParaName("本院认为");
               List<String> paras = new ArrayList<>();
               paras.add("本院认为");
               code.setParas(paras);
               labelList.add(code);
            } else {
               String flag = casecauseModel.getJustice();
               code = publicFeatureExtract.doExtract(flag);
               if(code != null) {
                  code.setCasecause(casecauseModel.getCasecause());
                  code.setPrioritylevel("2");
                  code.setParaText(casecauseModel.getJustice());
                  code.setParaName("经审理查明");
                  List<String> paras = new ArrayList<>();
                  paras.add("经审理查明");
                  code.setParas(paras);
                  labelList.add(code);
               }
            }
         }

         JSONObject reqParam1 = new JSONObject();
         reqParam1.put("casecause", casecauseModel.getCasecause());
         reqParam1.put("content", casecauseModel.getJustice());
         responseStr = HttpRequest.sendPost(this.modelUrl, reqParam1);
         JSONObject reponseJson1 = new JSONObject();
         if(StringUtils.isNotEmpty(responseStr)) {
            reponseJson1 = JSONObject.parseObject(responseStr);
         }

         Iterator publicFeatureExtract2 = this.basicModelRuleExtracts.keySet().iterator();

         while(publicFeatureExtract2.hasNext()) {
            String code1 = (String)publicFeatureExtract2.next();
            boolean flag1 = false;
            String conclusiontext = casecauseModel.getOpinion();
            PublicFeatureExtract publicFeatureExtract1 = (PublicFeatureExtract)((List)this.basicModelRuleExtracts.get(code1)).get(0);
            Label label = publicFeatureExtract1.doExtract(conclusiontext);
            if(label != null) {
               label.setCasecause(casecauseModel.getCasecause());
               label.setPrioritylevel("1");
               label.setParaText(casecauseModel.getOpinion());
               label.setParaName("本院认为");
               List<String> paras = new ArrayList<>();
               paras.add("本院认为");
               label.setParas(paras);
               labelList.add(label);
               flag1 = true;
            }

            if(!flag1) {
               Iterator var12 = reponseJson1.keySet().iterator();
               while(var12.hasNext()) {
                  String featureName = (String)var12.next();
                  if(featureName.contains(code1) && reponseJson1.get(featureName).toString().contains("1")) {
                     PublicFeatureExtract basicModelRuleExtract = (PublicFeatureExtract)((List)this.basicModelRuleExtracts.get(code1)).get(1);
                     label = basicModelRuleExtract.doExtract(casecauseModel.getJustice());
                     if(label != null) {
                        label.setCasecause(casecauseModel.getCasecause());
                        label.setPrioritylevel("2");
                        label.setParaText(casecauseModel.getJustice());
                        label.setParaName("经审理查明");
                        List<String> paras = new ArrayList<>();
                        paras.add("本院认为");
                        label.setParas(paras);
                        labelList.add(label);
                        break;
                     }
                  }
               }
            }
         }

         return labelList;
      }
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList label = new ArrayList();
      return label;
   }
}
