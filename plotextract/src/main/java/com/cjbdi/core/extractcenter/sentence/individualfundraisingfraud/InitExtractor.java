package com.cjbdi.core.extractcenter.sentence.individualfundraisingfraud;

import com.cjbdi.core.extractcenter.sentence.common.DefaultPeopleNumExtractor;
import com.cjbdi.core.extractcenter.sentence.common.money.Money;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "集资诈骗罪（个人）";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new Money("1001"));
      this.basicPrivateExtractors.add(new DefaultPeopleNumExtractor("1005"));
   }
}
