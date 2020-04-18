package com.cjbdi.core.extractcenter.sentence.providingvenuesfordrugusers;

import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.providingvenuesfordrugusers.AdministrativePenalty;
import com.cjbdi.core.extractcenter.sentence.providingvenuesfordrugusers.People;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "容留他人吸毒罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new People());
      this.basicPrivateExtractors.add(new AdministrativePenalty());
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1001"));
   }
}
