package com.cjbdi.core.extractcenter.sentence.findtrouble;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByGovernForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.DeathNumber;
import com.cjbdi.core.extractcenter.sentence.common.MinorInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfFirstLevelMinorInjury;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfFirstLevelSeriousInjury;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfSecondLevelMinorInjury;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfSecondLevelSeriousInjury;
import com.cjbdi.core.extractcenter.sentence.common.SeriousInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.SlightInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.EigthLevel;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.FifthLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.FirstLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.FourthLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.NinthLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.SecondLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.SevenLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.SixthLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.TenthLevelDisability;
import com.cjbdi.core.extractcenter.sentence.common.disabilitylevel.ThirdLevelDisability;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "寻衅滋事罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new DeathNumber("1021"));
      this.basicPrivateExtractors.add(new SeriousInjuryNumber("1020"));
      this.basicPrivateExtractors.add(new MinorInjuryNumber("1022"));
      this.basicPrivateExtractors.add(new SlightInjuryNumber("1023"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelSeriousInjury("1034"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelSeriousInjury("1035"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelMinorInjury("1036"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelMinorInjury("1037"));
      this.basicPrivateExtractors.add(new FirstLevelDisability("1024"));
      this.basicPrivateExtractors.add(new SecondLevelDisability("1025"));
      this.basicPrivateExtractors.add(new ThirdLevelDisability("1026"));
      this.basicPrivateExtractors.add(new FourthLevelDisability("1027"));
      this.basicPrivateExtractors.add(new FifthLevelDisability("1028"));
      this.basicPrivateExtractors.add(new SixthLevelDisability("1029"));
      this.basicPrivateExtractors.add(new SevenLevelDisability("1030"));
      this.basicPrivateExtractors.add(new EigthLevel("1031"));
      this.basicPrivateExtractors.add(new NinthLevelDisability("1032"));
      this.basicPrivateExtractors.add(new TenthLevelDisability("1033"));
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1038"));
      this.basicPrivateExtractors.add(new BeingPunishedByGovernForSameCasecause("1039"));
   }
}
