package com.cjbdi.core.extractcenter.sentence.chaseRace;

import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "危险驾驶罪（在道路上驾驶机动车，追逐竞驶）";


   public InitExtractor() {
      this.init(this.casecause);
   }
}
