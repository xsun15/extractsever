package com.cjbdi.core.extractcenter.sentence.maliciousOverDraftFraud;

import com.cjbdi.core.extractcenter.sentence.common.money.Money;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "恶意透支诈骗罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new Money("1001"));
   }
}
