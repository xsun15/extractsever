package com.cjbdi.core.extractcenter.sentence.drug;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "走私、贩卖、运输、制造毒品罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1004"));
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1053"));
   }
}
