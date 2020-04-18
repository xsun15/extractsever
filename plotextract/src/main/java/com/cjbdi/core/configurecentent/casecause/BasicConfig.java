package com.cjbdi.core.configurecentent.casecause;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class BasicConfig {

   List ename;
   List name;
   List order;


   public BasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      this.ename = YamlPropertySourceFactoryUser.loadConfig(featureName + ".ename", sourceName);
      this.order = YamlPropertySourceFactoryUser.loadConfig(featureName + ".order", sourceName);
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public void setName(List name) {
      this.name = name;
   }

   public String getEname() {
      return StringUtils.strip(this.ename.toString(), "[]");
   }

   public void setEname(List ename) {
      this.ename = ename;
   }

   public String getOrder() {
      return StringUtils.strip(this.order.toString(), "[]");
   }

   public void setOrder(List order) {
      this.order = order;
   }
}
