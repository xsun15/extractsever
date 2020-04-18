package com.cjbdi.core.extractcenter.sentence.companyillegallyabsorbingpublicdeposits;

import com.cjbdi.core.extractcenter.sentence.common.money.DefaultSpecialMoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.common.money.Money;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "非法吸收公众存款罪（单位）";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new Money("1001"));
      this.basicPrivateExtractors.add(new DefaultSpecialMoneyExtractor(""));
      this.basicPrivateExtractors.add(new DefaultSpecialMoneyExtractor(""));
   }
}
