package com.cjbdi.core.configurecentent.extractfeature.checklabel;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class CheckLabelBasicConfig {

   private List name;
   private List casecause;
   private List rule;


   public CheckLabelBasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      this.casecause = YamlPropertySourceFactoryUser.loadConfig(featureName + ".casecause", sourceName);
      this.rule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rule", sourceName);
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public void setName(List name) {
      this.name = name;
   }

   public String getCasecause() {
      return StringUtils.strip(this.casecause.toString(), "[]");
   }

   public void setCasecause(List casecause) {
      this.casecause = casecause;
   }

   public String getRule() {
      return StringUtils.strip(this.rule.toString(), "[]");
   }

   public void setRule(List rule) {
      this.rule = rule;
   }
}
