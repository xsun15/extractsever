package com.cjbdi.core.extractcenter.sentence.endangerpa;

import com.cjbdi.core.extractcenter.sentence.common.MinorInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.SlightInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.endangerpa.PropertyLossLabelExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "妨害公务罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new MinorInjuryNumber("1008"));
      this.basicPrivateExtractors.add(new SlightInjuryNumber("1009"));
      this.basicPrivateExtractors.add(new PropertyLossLabelExtractor());
   }
}
