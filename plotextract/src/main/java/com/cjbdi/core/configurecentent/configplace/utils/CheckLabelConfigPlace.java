package com.cjbdi.core.configurecentent.configplace.utils;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class CheckLabelConfigPlace {

   private List sentence;


   public CheckLabelConfigPlace(String featureName, String sourceName) {
      this.sentence = YamlPropertySourceFactoryUser.loadConfig(featureName + ".sentence", sourceName);
   }

   public String getSentence() {
      return StringUtils.strip(this.sentence.toString(), "[]");
   }

   public void setSentence(List sentence) {
      this.sentence = sentence;
   }
}
