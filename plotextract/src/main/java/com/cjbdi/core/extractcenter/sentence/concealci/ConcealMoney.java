package com.cjbdi.core.extractcenter.sentence.concealci;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.ColorTextConfig;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.cjbdi.core.servercenter.utils.Tools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ConcealMoney extends MoneyExtractor {

   private String code = "1021";
   private String casecause = "掩饰、隐瞒犯罪所得、犯罪所得收益罪";
   private String extractUrl;


   public ConcealMoney() {
      this.extractUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getRecognizemoney();
      this.init(this.casecause, this.extractUrl);
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      Label label = null;
      JSONObject money = this.run(defendantModel, casecauseModel);
      if(money != null && !money.isEmpty()) {
         label = SetLabel.runNumber(money, this.code);
      }

      return label;
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

   public List extractMoneyPureRule(String text) {
      ArrayList allmoney = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));
         String lastLine = "";

         for(int i = 0; i < textList.size(); ++i) {
            String line = (String)textList.get(i);
            List moneyConfigList = extractMoney(line, this.moneyRatioList);
            moneyConfigList = this.addMoneyPositionOffset(moneyConfigList, lastLine.length());
            lastLine = lastLine + line + "\n";
            if(moneyConfigList != null && moneyConfigList.size() > 0) {
               Iterator var8 = moneyConfigList.iterator();

               while(var8.hasNext()) {
                  MoneyConfig moneyConfig = (MoneyConfig)var8.next();
                  if(MatchRule.matchNum(moneyConfig.sentence, this.moneyRatioList) == 1) {
                     if(MatchRule.IsMatchPattern(moneyConfig.sentence, this.mustRule)) {
                        moneyConfig.moneyType = "有效";
                        continue;
                     }
                  } else if(MatchRule.matchNum(moneyConfig.sentence, this.moneyRatioList) > 1) {
                     String mosicText = MatchRule.cleanMatchTextPattern(moneyConfig.sentence, this.firstInvalidPureRuleList);
                     if(mosicText.contains(moneyConfig.target) && MatchRule.IsMatchPattern(mosicText, this.mustRule)) {
                        moneyConfig.moneyType = "有效";
                     }
                     continue;
                  }

                  moneyConfig.moneyType = "无效";
                  moneyConfig.paraindex = i;
                  moneyConfig.startColor = moneyConfig.start;
                  moneyConfig.endColor = moneyConfig.end;
               }

               allmoney.addAll(moneyConfigList);
            }
         }
      }

      return allmoney;
   }
}
