package com.cjbdi.core.extractcenter.sentence.counterfeitFraud;

import com.cjbdi.core.extractcenter.sentence.common.money.Money;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "使用伪造的信用卡诈骗罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new Money("1001"));
   }
}
