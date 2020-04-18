package com.cjbdi.core.extractcenter.sentence.traffic;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.DeathNumber;
import com.cjbdi.core.extractcenter.sentence.common.MinorInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfFirstLevelMinorInjury;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfFirstLevelSeriousInjury;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfSecondLevelMinorInjury;
import com.cjbdi.core.extractcenter.sentence.common.NumberOfSecondLevelSeriousInjury;
import com.cjbdi.core.extractcenter.sentence.common.SeriousInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.common.SlightInjuryNumber;
import com.cjbdi.core.extractcenter.sentence.traffic.DrivingUnlicensedVehicle;
import com.cjbdi.core.extractcenter.sentence.traffic.NumberOfEscapeCausingDeath;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "交通肇事罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1020"));
      this.basicPrivateExtractors.add(new DrivingUnlicensedVehicle());
      this.basicPrivateExtractors.add(new NumberOfEscapeCausingDeath());
      this.basicPrivateExtractors.add(new DeathNumber("1023"));
      this.basicPrivateExtractors.add(new SeriousInjuryNumber("1022"));
      this.basicPrivateExtractors.add(new MinorInjuryNumber("1024"));
      this.basicPrivateExtractors.add(new SlightInjuryNumber("1025"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelSeriousInjury("1027"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelSeriousInjury("1028"));
      this.basicPrivateExtractors.add(new NumberOfFirstLevelMinorInjury("1029"));
      this.basicPrivateExtractors.add(new NumberOfSecondLevelMinorInjury("1030"));
   }
}
