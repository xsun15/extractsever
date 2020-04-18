package com.cjbdi.core.extractcenter.sentence.prostitution;

import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.common.money.DefaultSpecialMoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.prostitution.AdministrativePenalty;
import com.cjbdi.core.extractcenter.sentence.prostitution.People;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "引诱、容留、介绍卖淫罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new AdministrativePenalty());
      this.basicPrivateExtractors.add(new People());
      this.basicPrivateExtractors.add(new DefaultSpecialMoneyExtractor("1002"));
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1005"));
   }
}
