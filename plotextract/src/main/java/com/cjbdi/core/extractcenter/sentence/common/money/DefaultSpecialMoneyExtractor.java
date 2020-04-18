package com.cjbdi.core.extractcenter.sentence.common.money;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.cjbdi.core.servercenter.utils.Tools;
import java.util.Iterator;
import java.util.List;

public class DefaultSpecialMoneyExtractor extends BasicSentenceFeatureClass {

   private List moneyRules;
   private List specialRule;
   private String feature;
   private String code;


   public DefaultSpecialMoneyExtractor(String feature) {
      this.feature = feature;
      this.moneyRules = ((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract")).getMoneyRatio();
      this.code = "1008";
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      this.specialRule = ((ExtractFeatureBasicConfig)caseClass.getFeatures().get(this.code)).getPositivepurePattern();
      String factText = casecauseModel.getJustice();
      factText = Tools.deleteDigitDot(factText);
      List allmoney = MoneyExtractor.extractMoney(factText, this.moneyRules);
      double value = 0.0D;
      Iterator var8 = allmoney.iterator();

      while(var8.hasNext()) {
         MoneyConfig money = (MoneyConfig)var8.next();
         if(MatchRule.matchPatternsBool(money.sentence, this.specialRule)) {
            value += money.value;
         }
      }

      if(value >= 0.001D) {
         return SetLabel.run(String.valueOf(value), this.code);
      } else {
         return null;
      }
   }
}
