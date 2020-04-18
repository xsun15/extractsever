package com.cjbdi.core.extractcenter.sentence.affray;

import com.cjbdi.core.extractcenter.sentence.common.*;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.*;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "聚众斗殴罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new PropertyLossLabelExtractor());
      this.basicPrivateExtractors.add(new DeathNumber("1014"));
      this.basicPrivateExtractors.add(new SeriousInjuryNumber("1013"));
      this.basicPrivateExtractors.add(new MinorInjuryNumber("1015"));
      this.basicPrivateExtractors.add(new SlightInjuryNumber("1016"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelSeriousInjury("1027"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelSeriousInjury("1028"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelMinorInjury("1029"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelMinorInjury("1030"));
      this.basicPrivateExtractors.add(new FirstLevelDisability("1017"));
      this.basicPrivateExtractors.add(new SecondLevelDisability("1018"));
      this.basicPrivateExtractors.add(new ThirdLevelDisability("1019"));
      this.basicPrivateExtractors.add(new FourthLevelDisability("1020"));
      this.basicPrivateExtractors.add(new FifthLevelDisability("1021"));
      this.basicPrivateExtractors.add(new SixthLevelDisability("1022"));
      this.basicPrivateExtractors.add(new SevenLevelDisability("1023"));
      this.basicPrivateExtractors.add(new EigthLevel("1024"));
      this.basicPrivateExtractors.add(new NinthLevelDisability("1025"));
      this.basicPrivateExtractors.add(new TenthLevelDisability("1026"));
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1031"));
      this.basicPrivateExtractors.add(new BeingPunishedByGovernForSameCasecause("1032"));
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1033"));
   }
}
