package com.cjbdi.core.extractcenter.sentence.common;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.CommonBasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.NumberConvertUtil;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.List;
import java.util.Optional;

public class DefaultCriminalTimesExtractor extends BasicSentenceFeatureClass {

   private String feature;
   private String code;
   private List positivePurePattern;


   public DefaultCriminalTimesExtractor(String code) {
      this.init(code);
   }

   public void init(String code) {
      this.feature = "timesextract";
      this.code = code;
      this.positivePurePattern = ((CommonBasicConfig)BeanFactoryConfig.extractFeatureConfig.getCommon().getFeatures().get(this.feature)).getPositivepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      String defendant = defendantModel.getName();
      String conclusionText = casecauseModel.getOpinion();
      Optional totalTimes = this.fromConclusion(defendant, conclusionText);
      if(totalTimes.isPresent()) {
         return SetLabel.run(String.valueOf(totalTimes.get()), this.code, ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.code)).getName());
      } else {
         JSONObject reqJson = new JSONObject();
         reqJson.put("text", casecauseModel.getJustice());
         reqJson.put("caseCause", casecauseModel.getCasecause());

         try {
            String e = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictcrimecountpb(), reqJson);
            if(e != null && !e.equals("0")) {
               return SetLabel.run(e, this.code, ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.code)).getName());
            }
         } catch (Exception var9) {
            var9.printStackTrace();
         }

         return SetLabel.run(String.valueOf(1), this.code, ((ExtractFeatureBasicConfig)basicCaseClass.getFeatures().get(this.code)).getName());
      }
   }

   private Optional fromConclusion(String name, String text) {
      if(text != null && !text.isEmpty()) {
         String[] sentences = text.split(";；。");
         String[] var4 = sentences;
         int var5 = sentences.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String str = var4[var6];
            String matchedStr = MatchRule.matchTextPattern(str, this.positivePurePattern);
            if(matchedStr != null && str.contains(name)) {
               Optional times = NumberConvertUtil.extract(matchedStr);
               if(times.isPresent()) {
                  return times;
               }
            }
         }
      }

      return Optional.empty();
   }
}
