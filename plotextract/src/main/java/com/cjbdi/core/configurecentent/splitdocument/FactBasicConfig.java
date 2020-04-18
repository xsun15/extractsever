package com.cjbdi.core.configurecentent.splitdocument;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;

public class FactBasicConfig {

   private List header;
   private List tail;
   private List cansize;


   public FactBasicConfig(String featureName, String sourceName) {
      this.header = YamlPropertySourceFactoryUser.loadConfig(featureName + ".header", sourceName);
      this.tail = YamlPropertySourceFactoryUser.loadConfig(featureName + ".tail", sourceName);
      this.cansize = YamlPropertySourceFactoryUser.loadConfig(featureName + ".cansize", sourceName);
   }

   public List getHeader() {
      return this.header;
   }

   public void setHeader(List header) {
      this.header = header;
   }

   public List getTail() {
      return this.tail;
   }

   public void setTail(List tail) {
      this.tail = tail;
   }

   public List getCansize() {
      return this.cansize;
   }

   public void setCansize(List cansize) {
      this.cansize = cansize;
   }
}
