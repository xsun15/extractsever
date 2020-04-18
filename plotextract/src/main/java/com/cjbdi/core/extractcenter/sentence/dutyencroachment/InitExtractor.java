package com.cjbdi.core.extractcenter.sentence.dutyencroachment;

import com.cjbdi.core.extractcenter.sentence.common.DefaultCriminalTimesExtractor;
import com.cjbdi.core.extractcenter.sentence.dutyencroachment.DutyMoney;
import com.cjbdi.core.extractcenter.sentence.utils.InitBasicExtractor;

public class InitExtractor extends InitBasicExtractor {

   private String casecause = "职务侵占罪";


   public InitExtractor() {
      this.init(this.casecause);
      this.basicPrivateExtractors.add(new DutyMoney());
      this.basicPrivateExtractors.add(new DefaultCriminalTimesExtractor("1012"));
   }
}
