package com.cjbdi.core.extractcenter.sentence.common.money;

import com.alibaba.fastjson.JSONObject;
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

public class Money extends MoneyExtractor {

   private String code;


   public Money(String code) {
      this.code = code;
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass steal) {
      Label label = null;
      JSONObject money = this.run(defendantModel, casecauseModel);
      if(money != null && !money.isEmpty()) {
         label = SetLabel.runNumber(money, this.code);
      }

      return label;
   }

   public JSONObject run(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      String casecause = casecauseModel.getCasecause();
      String opinion = casecauseModel.getOpinion();
      String justice = casecauseModel.getJustice();
      this.init();
      ColorTextConfig colorTextConfig = new ColorTextConfig();
      JSONObject result = new JSONObject();
      List allmoney;
      if(StringUtils.isNotEmpty(opinion)) {
         allmoney = this.extractMoneyUsedModel(opinion, casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
         if(this.isOneEffectMoney(allmoney, opinion, colorTextConfig, result)) {
            return result;
         }
      }

      if(StringUtils.isNotEmpty(justice)) {
         allmoney = this.extractMoneyUsedModel(justice, casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
         if(allmoney == null || allmoney.size() == 0) {
            return result;
         }

         FactTextConfig factTextConfig = FactTextSplit.run(justice);
         if(StringUtils.isNotBlank(factTextConfig.header) && StringUtils.isNotEmpty(factTextConfig.header)) {
            allmoney = this.extractMoneyUsedModel(factTextConfig.header, casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
            if(this.isOneEffectMoney(allmoney, factTextConfig.header, colorTextConfig, result)) {
               return result;
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.tail) && StringUtils.isNotEmpty(factTextConfig.tail)) {
            allmoney = this.extractMoneyUsedModel(factTextConfig.tail, casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
            if(this.isOneEffectMoney(allmoney, factTextConfig.tail, colorTextConfig, result)) {
               return result;
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
            allmoney = this.extractMoneyUsedModel(factTextConfig.body, casecause, this.modelRuleList, this.firstInvalidPureRuleList, this.secondInvalidPureRuleList, this.convertRuleList, this.moneyRatioList);
            return this.extractMoneyFromJusticBody(allmoney, factTextConfig.body, justice, colorTextConfig, result);
         }
      }

      return result;
   }
}
