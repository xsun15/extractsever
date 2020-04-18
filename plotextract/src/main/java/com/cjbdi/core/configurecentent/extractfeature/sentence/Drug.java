package com.cjbdi.core.configurecentent.extractfeature.sentence;

import com.cjbdi.core.decryptcenter.BasicCaseClass;

public class Drug extends BasicCaseClass {

   private String casecause = "走私、贩卖、运输、制造毒品罪";


   public Drug() {
      this.init(this.casecause);
   }
}
