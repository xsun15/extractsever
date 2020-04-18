package com.cjbdi.core.configurecentent.converlabel;

import com.cjbdi.core.configurecentent.converlabel.leianv1.LeianV1;
import com.cjbdi.core.configurecentent.converlabel.sentence.SelfSentence;

public class ConvertLabelConfig {

   private SelfSentence selfSentence = new SelfSentence();
   private LeianV1 leianV1 = new LeianV1();


   public SelfSentence getSelfSentence() {
      return this.selfSentence;
   }

   public LeianV1 getLeianV1() {
      return this.leianV1;
   }
}
