package com.cjbdi.core.configurecentent.splitdocument;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.splitdocument.FactBasicConfig;

public class FactTextConfig {

   private FactBasicConfig factTextConfig;


   public FactTextConfig() {
      String place = BeanFactoryConfig.configPlace.getOthersConfigPlace().getFactsplit();
      String feature = "factsplit";
      this.factTextConfig = new FactBasicConfig(feature, place);
   }

   public FactBasicConfig getFactTextConfig() {
      return this.factTextConfig;
   }

   public void setFactTextConfig(FactBasicConfig factTextConfig) {
      this.factTextConfig = factTextConfig;
   }
}
