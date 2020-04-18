package com.cjbdi.core.extractcenter.sentence.companycontractfraud;

import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.common.money.Money;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "合同诈骗罪（单位）";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new Money("1001"));
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1006"));
   }
}
