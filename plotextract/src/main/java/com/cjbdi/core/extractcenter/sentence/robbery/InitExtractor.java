package com.cjbdi.core.extractcenter.sentence.robbery;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByGovernForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.DeathNumber;
import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
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
import com.cjbdi.core.extractcenter.sentence.robbery.RobberyMoney;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "抢劫罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new RobberyMoney());
      this.basicPrivateExtractors.add(new DeathNumber("1032"));
      this.basicPrivateExtractors.add(new SeriousInjuryNumber("1031"));
      this.basicPrivateExtractors.add(new MinorInjuryNumber("1033"));
      this.basicPrivateExtractors.add(new SlightInjuryNumber("1034"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelSeriousInjury("1046"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelSeriousInjury("1047"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelMinorInjury("1048"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelMinorInjury("1049"));
      this.basicPrivateExtractors.add(new FirstLevelDisability("1035"));
      this.basicPrivateExtractors.add(new SecondLevelDisability("1036"));
      this.basicPrivateExtractors.add(new ThirdLevelDisability("1037"));
      this.basicPrivateExtractors.add(new FourthLevelDisability("1038"));
      this.basicPrivateExtractors.add(new FifthLevelDisability("1039"));
      this.basicPrivateExtractors.add(new SixthLevelDisability("1040"));
      this.basicPrivateExtractors.add(new SevenLevelDisability("1041"));
      this.basicPrivateExtractors.add(new EigthLevel("1042"));
      this.basicPrivateExtractors.add(new NinthLevelDisability("1043"));
      this.basicPrivateExtractors.add(new TenthLevelDisability("1044"));
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1050"));
      this.basicPrivateExtractors.add(new BeingPunishedByGovernForSameCasecause("1051"));
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1018"));
   }
}
