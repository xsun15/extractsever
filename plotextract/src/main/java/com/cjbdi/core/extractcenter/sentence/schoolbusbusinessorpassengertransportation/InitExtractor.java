package com.cjbdi.core.extractcenter.sentence.schoolbusbusinessorpassengertransportation;

import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "危险驾驶罪（从事校车业务或者旅客运输）";


   public InitExtractor() {
      this.init(this.casecause);
   }
}
