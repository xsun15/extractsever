package com.cjbdi.core.extractcenter.sentence.steal;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;

public class TheftMoney extends MoneyExtractor {

   private String code = "1018";
   private String casecause;
   private String extractUrl;


   public TheftMoney() {
      this.extractUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getRecognizemoney();
      this.casecause = "盗窃罪";
      this.init(this.casecause, this.extractUrl);
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
