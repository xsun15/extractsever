package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.decryptcenter.BasicCaseClass;

public class MaliciousOverDraftFraud extends BasicCaseClass {

   private String casecause = "恶意透支诈骗罪";


   public MaliciousOverDraftFraud() {
      this.init(this.casecause);
   }
}
