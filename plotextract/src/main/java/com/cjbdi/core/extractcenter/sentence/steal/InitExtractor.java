package com.cjbdi.core.extractcenter.sentence.steal;

import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByCourtForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.BeingPunishedByGovernForSameCasecause;
import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.steal.CommonArtifact;
import com.cjbdi.core.extractcenter.sentence.steal.TheftMoney;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "盗窃罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new BeingPunishedByCourtForSameCasecause("1021"));
      this.basicPrivateExtractors.add(new BeingPunishedByGovernForSameCasecause("1022"));
      this.basicPrivateExtractors.add(new TheftMoney());
      this.basicPrivateExtractors.add(new CommonArtifact("1017"));
      this.basicPrivateExtractors.add(new CommonArtifact("1016"));
      this.basicPrivateExtractors.add(new CommonArtifact("1015"));
      this.basicPrivateExtractors.add(new CommonArtifact("1014"));
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1019"));
   }
}
