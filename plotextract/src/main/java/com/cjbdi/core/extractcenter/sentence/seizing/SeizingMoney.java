package com.cjbdi.core.extractcenter.sentence.seizing;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;

public class SeizingMoney extends MoneyExtractor {

   private String code = "1037";
   private String casecause = "抢夺罪";
   private String extractUrl;


   public SeizingMoney() {
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
}
