package com.cjbdi.core.extractcenter.sentence.concealci;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByGovernForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.concealci.ConcealMoney;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "掩饰、隐瞒犯罪所得、犯罪所得收益罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1019"));
      this.basicPrivateExtractors.add(new BeingPunishedByGovernForSameCasecause("1020"));
      this.basicPrivateExtractors.add(new ConcealMoney());
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1022"));
   }
}
