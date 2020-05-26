package com.cjbdi.core.extractcenter.sentence.traffic;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class DrivingUnlicensedVehicle extends BasicSentenceFeatureClass {

   String code = "1012";
   String modelUrl;
   List positivePureRule;
   List negativePureRule;
   List positiveModelRule;
   List negativeModelRule;


   public DrivingUnlicensedVehicle() {
      this.modelUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictfeaturepbnew();
      this.positivePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getTraffic().getFeatures().get(this.code)).getPositivepurePattern();
      this.negativePureRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getTraffic().getFeatures().get(this.code)).getNegativepurePattern();
      this.positiveModelRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getTraffic().getFeatures().get(this.code)).getPositivemodelPattern();
      this.negativeModelRule = ((ExtractFeatureBasicConfig)BeanFactoryConfig.extractFeatureConfig.getTraffic().getFeatures().get(this.code)).getNegativemodelPattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      String conclusiontext = casecauseModel.getOpinion();
      Label label = this.doExtract(conclusiontext, defendantModel.getName(), this.positivePureRule, this.negativePureRule);
      if(label != null) {
         label.setCasecause(casecauseModel.getCasecause());
         return label;
      } else {
         String factText = casecauseModel.getJustice();
         JSONObject reqParam = new JSONObject();
         reqParam.put("casecause", casecauseModel.getCasecause());
         reqParam.put("content", factText);
         String responseStr = HttpRequest.sendPost(this.modelUrl, reqParam);
         JSONObject reponseJson = new JSONObject();
         if(StringUtils.isNotEmpty(responseStr)) {
            reponseJson = JSONObject.parseObject(responseStr);
         }

         Iterator var10 = reponseJson.keySet().iterator();

         while(var10.hasNext()) {
            String featureName = (String)var10.next();
            if(featureName.contains(this.code) && reponseJson.get(featureName).toString().contains("1")) {
               label = this.doExtract(factText, defendantModel.getName(), this.positivePureRule, this.negativePureRule);
               if(label != null) {
                  label.setCasecause(casecauseModel.getCasecause());
                  return label;
               }
            }
         }

         return null;
      }
   }

   public Label doExtract(String lineText, String defendant, List positiveRule, List negativeRule) {
      Label label = null;
      BoolConfig boolConfig = MatchRule.matchPatternBoolConfig(lineText, positiveRule, negativeRule);
      if(boolConfig != null) {
         ArrayList nameList = new ArrayList();
         String target = lineText.substring(0, lineText.indexOf(boolConfig.target));
         Segment segment = HanLP.newSegment().enableNameRecognize(true);
         List termList = segment.seg(target);
         Iterator var11 = termList.iterator();

         while(var11.hasNext()) {
            Term term = (Term)var11.next();
            if(term.nature.toString().equals("nr")) {
               nameList.add(term.word);
            }
         }

         if(nameList != null && nameList.size() > 0 && ((String)nameList.get(nameList.size() - 1)).equals(defendant)) {
            label = SetLabel.run(boolConfig, this.code);
            List<String> paras = new ArrayList<>();
            paras.add("经审理查明");
            label.setParas(paras);
         }
      }

      return label;
   }
}
