package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.decryptcenter.BasicCaseClass;

public class InvalidFraud extends BasicCaseClass {

   private String casecause = "使用作废的信用卡或者冒用他人信用卡诈骗罪";


   public InvalidFraud() {
      this.init(this.casecause);
   }
}
