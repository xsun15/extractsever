package com.cjbdi.core.configurecentent.converlabel;

import com.alibaba.fastjson.JSONArray;
import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class BasicConfig {

   private List name;
   private List code;
   private List rule;
   private List<String> datatype;
   private List<String> type;


   public BasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      this.code = YamlPropertySourceFactoryUser.loadConfig(featureName + ".code", sourceName);
      this.rule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rule", sourceName);
      this.datatype = YamlPropertySourceFactoryUser.loadConfig(featureName + ".datatype", sourceName);
      this.type = YamlPropertySourceFactoryUser.loadConfig(featureName + ".type", sourceName);
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

   public String getDatatype() {
      return StringUtils.strip(this.datatype.toString(), "[]");
   }

   public String getType() {
      return StringUtils.strip(this.type.toString(), "[]");
   }

   public JSONArray toJson() {
      return new JSONArray();
   }
}
