package com.cjbdi.core.convertlabelcenter;

import com.cjbdi.core.convertlabelcenter.utils.ToLeianV1;
import com.cjbdi.core.convertlabelcenter.utils.ToSelfSentence;
import com.cjbdi.core.convertlabelcenter.utils.ToZhengan;

public class ConvertLabelFactory {

   public static ToSelfSentence toSelfSentence = new ToSelfSentence();
   public static ToLeianV1 toLeianV1 = new ToLeianV1();
   public static ToZhengan toZhengan = new ToZhengan();


}
