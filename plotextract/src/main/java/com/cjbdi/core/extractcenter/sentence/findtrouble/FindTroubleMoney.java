package com.cjbdi.core.extractcenter.sentence.findtrouble;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;

public class FindTroubleMoney extends MoneyExtractor {

   private String code = "1040";
   private String casecause = "寻衅滋事罪";
   private String extractUrl;


   public FindTroubleMoney() {
      this.extractUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getRecognizemoney();
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

   public JSONObject run(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      return new JSONObject();
   }
}
