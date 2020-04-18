package com.cjbdi.core.extractcenter.sentence.fraud;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByGovernForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.fraud.FraudMoney;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "诈骗罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new FraudMoney());
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1016"));
      this.basicPrivateExtractors.add(new BeingPunishedByGovernForSameCasecause("1017"));
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1018"));
   }
}
