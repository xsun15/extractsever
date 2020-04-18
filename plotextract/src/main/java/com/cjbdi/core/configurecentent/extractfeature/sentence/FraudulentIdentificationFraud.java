package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.decryptcenter.BasicCaseClass;

public class FraudulentIdentificationFraud extends BasicCaseClass {

   private String casecause = "使用以虚假的身份证明骗领的信用卡诈骗罪";


   public FraudulentIdentificationFraud() {
      this.init(this.casecause);
   }
}
