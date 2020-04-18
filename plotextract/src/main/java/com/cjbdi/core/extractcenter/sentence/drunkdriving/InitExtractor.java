package com.cjbdi.core.extractcenter.sentence.drunkdriving;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByGovernForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.MinorInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.SeriousInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.SlightInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.money.DefaultSpecialMoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.drunkdriving.BloodAlcoholContentExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "危险驾驶罪（醉驾）";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new BloodAlcoholContentExtractor());
      this.basicPrivateExtractors.add(new SlightInjuryNumber("1005"));
      this.basicPrivateExtractors.add(new SeriousInjuryNumber("1007"));
      this.basicPrivateExtractors.add(new MinorInjuryNumber("1006"));
      this.basicPrivateExtractors.add(new DefaultSpecialMoneyExtractor("economicLossextract"));
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1014"));
      this.basicPrivateExtractors.add(new BeingPunishedByGovernForSameCasecause("1013"));
   }
}
