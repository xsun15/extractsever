package com.cjbdi.core.extractcenter.sentence.individualillegallyabsorbingpublicdeposits;

import com.cjbdi.core.extractcenter.sentence.common.DefaultPeopleNumExtractor;
import com.cjbdi.core.extractcenter.sentence.common.money.DefaultSpecialMoneyExtractor;
import com.cjbdi.core.extractcenter.sentence.common.money.Money;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "非法吸收公众存款罪（个人）";


   public InitExtractor() {
      this.init(this.casecause);
//      this.basicPrivateExtractors.add(new Money("1001"));
      this.basicPrivateExtractors.add(new DefaultPeopleNumExtractor("1002"));
//      this.basicPrivateExtractors.add(new DefaultSpecialMoneyExtractor("DirectEconomicLossextraction"));
//      this.basicPrivateExtractors.add(new DefaultSpecialMoneyExtractor("AmountReturnedextraction"));
   }
}
