package com.cjbdi.core.extractcenter.sentence.falseimprison;

import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;

public class VictimNumber extends BasicSentenceFeatureClass {

   String code = "1040";


   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass basicCaseClass) {
      if(defendantModel.getVictimNameSet() != null && !defendantModel.getVictimNameSet().isEmpty()) {
         Label label = new Label();
         label.setValue(String.valueOf(defendantModel.getVictimNameSet().size()));
         label.setFlag((long)Integer.parseInt(this.code));
         return label;
      } else {
         return null;
      }
   }
}
