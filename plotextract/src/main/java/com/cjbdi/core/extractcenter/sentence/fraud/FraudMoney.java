package com.cjbdi.core.extractcenter.sentence.fraud;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.ColorTextConfig;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.servercenter.utils.Tools;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class FraudMoney extends MoneyExtractor {

   private String code = "1015";
   private String casecause;
   private String extractUrl;


   public FraudMoney() {
      this.extractUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getRecognizemoney();
      this.casecause = "诈骗罪";
      this.init(this.casecause, this.extractUrl);
   }

   public JSONObject run(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      String opinion = casecauseModel.getOpinion();
      String justice = casecauseModel.getJustice();
      ColorTextConfig colorTextConfig = new ColorTextConfig();
      JSONObject result = new JSONObject();
      List allmoney;
      if(StringUtils.isNotEmpty(opinion)) {
         opinion = Tools.deleteDigitDot(opinion);
         allmoney = this.extractMoneyPurePattern(opinion, this.casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
         if(this.isOneEffectMoney(allmoney, opinion, colorTextConfig, result)) {
            return result;
         }
      }

      if(StringUtils.isNotEmpty(justice)) {
         justice = Tools.deleteDigitDot(justice);
         allmoney = this.extractMoneyPurePattern(justice, this.casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
         if(allmoney == null || allmoney.size() == 0) {
            return result;
         }

         FactTextConfig factTextConfig = FactTextSplit.run(justice);
         if(StringUtils.isNotBlank(factTextConfig.header) && StringUtils.isNotEmpty(factTextConfig.header)) {
            allmoney = this.extractMoneyPurePattern(factTextConfig.header, this.casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
            if(this.isOneEffectMoney(allmoney, factTextConfig.header, colorTextConfig, result)) {
               return result;
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.tail) && StringUtils.isNotEmpty(factTextConfig.tail)) {
            allmoney = this.extractMoneyPurePattern(factTextConfig.tail, this.casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
            if(this.isOneEffectMoney(allmoney, factTextConfig.tail, colorTextConfig, result)) {
               return result;
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
            allmoney = this.extractMoneyPurePattern(factTextConfig.body, this.casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
            return this.extractMoneyFromJusticBody(allmoney, factTextConfig.body, justice, colorTextConfig, result);
         }
      }

      return result;
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass steal) {
      Label label = null;
      JSONObject money = this.run(defendantModel, casecauseModel);
      if(money != null && !money.isEmpty()) {
         label = SetLabel.runNumber(money, this.code);
      }

      return label;
   }
}
