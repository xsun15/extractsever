package com.cjbdi.core.configurecentent.converlabel.leianv1;

import com.alibaba.fastjson.JSONArray;
import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class LeianBasicConfig {

   private List name;
   private List rule;


   public LeianBasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      this.rule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rule", sourceName);
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public String getRule() {
      return StringUtils.strip(this.rule.toString(), "[]");
   }

   public JSONArray toJson() {
      return new JSONArray();
   }
}
