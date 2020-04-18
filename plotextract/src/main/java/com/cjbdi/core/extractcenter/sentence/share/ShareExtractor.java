package com.cjbdi.core.extractcenter.sentence.share;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Share;
import com.cjbdi.core.extractcenter.sentence.share.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;

public class ShareExtractor extends LabelExtractor {

   private Share share;
   private InitExtractor initExtractor = new InitExtractor();


   public ShareExtractor(Share share) {
      this.modelUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictfeaturepbnew();
      this.basicPureRuleExtracts = this.initExtractor.getBasicPureRuleExtractors();
      this.basicModelRuleExtracts = this.initExtractor.getBasicModelRuleExtractors();
      this.share = share;
   }

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
            List code = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get(responseStr)).getNegativecasecause();
            if(code == null || !code.contains(casecauseModel.getCasecause())) {
               Label negativecasecause = publicFeatureExtract.doExtract(reponseJson);
               if(negativecasecause != null) {
                  negativecasecause.setCasecause(casecauseModel.getCasecause());
                  negativecasecause.setParaText(casecauseModel.getOpinion());
                  negativecasecause.setParaName("本院认为");
                  labelList.add(negativecasecause);
               } else {
                  String flag = casecauseModel.getJustice();
                  negativecasecause = publicFeatureExtract.doExtract(flag);
                  if(negativecasecause != null) {
                     negativecasecause.setCasecause(casecauseModel.getCasecause());
                     negativecasecause.setParaText(casecauseModel.getJustice());
                     negativecasecause.setParaName("经审理查明");
                     labelList.add(negativecasecause);
                  }
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
            List negativecasecause1 = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getShare().getFeatures().get(code1)).getNegativecasecause();
            if(negativecasecause1 == null || !negativecasecause1.contains(casecauseModel.getCasecause())) {
               boolean flag1 = false;
               String conclusiontext = casecauseModel.getOpinion();
               PublicFeatureExtract publicFeatureExtract1 = (PublicFeatureExtract)((List)this.basicModelRuleExtracts.get(code1)).get(0);
               Label label = publicFeatureExtract1.doExtract(conclusiontext);
               if(label != null) {
                  label.setCasecause(casecauseModel.getCasecause());
                  label.setParaText(casecauseModel.getOpinion());
                  label.setParaName("本院认为");
                  labelList.add(label);
                  flag1 = true;
               }

               if(!flag1) {
                  Iterator var13 = reponseJson1.keySet().iterator();

                  while(var13.hasNext()) {
                     String featureName = (String)var13.next();
                     if(featureName.contains(code1) && reponseJson1.get(featureName).toString().contains("1")) {
                        PublicFeatureExtract basicModelRuleExtract = (PublicFeatureExtract)((List)this.basicModelRuleExtracts.get(code1)).get(1);
                        label = basicModelRuleExtract.doExtract(casecauseModel.getJustice());
                        if(label != null) {
                           label.setCasecause(casecauseModel.getCasecause());
                           label.setParaText(casecauseModel.getJustice());
                           label.setParaName("经审理查明");
                           labelList.add(label);
                           break;
                        }
                     }
                  }
               }
            }
         }

         return labelList;
      }
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList labelList = new ArrayList();
      String content = casecauseModel.getOpinion();
      Optional defendantAge = defendantModel.getAge();
      if(defendantAge.isPresent()) {
         int label = ((Integer)defendantAge.get()).intValue();
         Label label1 = this.initExtractor.getOldercrimeExtractor().doExtract(content, label, 65, 200);
         if(label1 != null) {
            labelList.add(label1);
         }

         label1 = this.initExtractor.getOldercrime65Extractor().doExtract(content, label, 65, 70);
         if(label1 != null) {
            labelList.add(label1);
         }

         label1 = this.initExtractor.getOldercrime70Extractor().doExtract(content, label, 70, 75);
         if(label1 != null) {
            labelList.add(label1);
         }

         label1 = this.initExtractor.getOldercrime75Extractor().doExtract(content, label, 75, 200);
         if(label1 != null) {
            labelList.add(label1);
         }

         label1 = this.initExtractor.getUnderagecrimeExtractor().doExtract(content, label, 14, 18);
         if(label1 != null) {
            labelList.add(label1);
         }

         label1 = this.initExtractor.getUnderagecrime16Extractor().doExtract(content, label, 16, 18);
         if(label1 != null) {
            labelList.add(label1);
         }

         label1 = this.initExtractor.getUnderagecrime14Extractor().doExtract(content, label, 14, 16);
         if(label1 != null) {
            labelList.add(label1);
         }
      }

      Label label2 = this.initExtractor.getRecidivismExtractor().doextract(defendantModel, casecauseModel);
      if(label2 != null) {
         labelList.add(label2);
      }

      label2 = this.initExtractor.getRecidivistExtractor().doextract(defendantModel, casecauseModel);
      if(label2 != null) {
         labelList.add(label2);
      }

      label2 = this.initExtractor.getPreviousRecordExtractor().doextract(defendantModel, casecauseModel);
      if(label2 != null) {
         labelList.add(label2);
      }

      label2 = this.initExtractor.getFirstCrimeExtractor().doextract(defendantModel, casecauseModel);
      if(label2 != null) {
         labelList.add(label2);
      }

      return labelList;
   }
}
