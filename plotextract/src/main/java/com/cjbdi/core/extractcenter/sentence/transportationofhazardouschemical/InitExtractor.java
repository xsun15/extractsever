package com.cjbdi.core.extractcenter.sentence.transportationofhazardouschemical;

import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "危险驾驶罪（运输危险化学品）";


   public InitExtractor() {
      this.init(this.casecause);
   }
}
