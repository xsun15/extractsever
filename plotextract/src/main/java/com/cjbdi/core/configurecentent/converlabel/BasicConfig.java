package com.cjbdi.core.configurecentent.converlabel;

import com.alibaba.fastjson.JSONArray;
import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class BasicConfig {

   private List name;
   private List code;
   private List rule;


   public BasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      this.code = YamlPropertySourceFactoryUser.loadConfig(featureName + ".code", sourceName);
      this.rule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rule", sourceName);
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public String getCode() {
      return StringUtils.strip(this.code.toString(), "[]");
   }

   public String getRule() {
      return StringUtils.strip(this.rule.toString(), "[]");
   }

   public JSONArray toJson() {
      return new JSONArray();
   }
}
