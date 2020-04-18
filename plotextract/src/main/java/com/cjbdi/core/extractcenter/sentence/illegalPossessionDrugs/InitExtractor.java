package com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs;

import com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs.DrugsRecidivism;
import com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs.DrugsTypes;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "非法持有毒品罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new DrugsRecidivism());
      this.basicPrivateExtractors.add(new DrugsTypes());
   }
}
