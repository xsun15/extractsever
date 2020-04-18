package com.cjbdi.core.extractcenter.sentence.fraudulentIdentificationFraud;

import com.cjbdi.core.extractcenter.sentence.common.money.Money;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "使用以虚假的身份证明骗领的信用卡诈骗罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new Money("1001"));
   }
}
