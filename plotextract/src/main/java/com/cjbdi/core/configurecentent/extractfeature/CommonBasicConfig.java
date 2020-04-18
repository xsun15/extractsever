package com.cjbdi.core.configurecentent.extractfeature;

import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.ExtractFeatureTools;
import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CommonBasicConfig {

   private List<String> name;
   private List<String> positivepurerule;
   private List<String> positivemodelrule;
   private List<String> negativepurerule;
   private List<String> negativemodelrule;
   private List<Pattern> positivepurePattern;
   private List<Pattern> positivemodelPattern;
   private List<Pattern> negativepurePattern;
   private List<Pattern> negativemodelPattern;


   public CommonBasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      this.positivepurerule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".positivepurerule", sourceName);
      this.positivemodelrule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".positivemodelrule", sourceName);
      this.negativepurerule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".negativepurerule", sourceName);
      this.negativemodelrule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".negativemodelrule", sourceName);
      this.positivepurePattern = ExtractFeatureTools.toPattern(this.positivepurerule);
      this.positivemodelPattern = ExtractFeatureTools.toPattern(this.positivemodelrule);
      this.negativepurePattern = ExtractFeatureTools.toPattern(this.negativepurerule);
      this.negativemodelPattern = ExtractFeatureTools.toPattern(this.negativemodelrule);
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public List getPositivepurerule() {
      return this.positivepurerule;
   }

   public List getPositivemodelrule() {
      return this.positivemodelrule;
   }

   public List getNegativepurerule() {
      return this.negativepurerule;
   }

   public List getNegativemodelrule() {
      return this.negativemodelrule;
   }

   public void setPositivepurerule(List positivepurerule) {
      this.positivepurerule = positivepurerule;
   }

   public void setPositivemodelrule(List positivemodelrule) {
      this.positivemodelrule = positivemodelrule;
   }

   public void setNegativepurerule(List negativepurerule) {
      this.negativepurerule = negativepurerule;
   }

   public void setNegativemodelrule(List negativemodelrule) {
      this.negativemodelrule = negativemodelrule;
   }

   public List getPositivepurePattern() {
      return this.positivepurePattern;
   }

   public List getPositivemodelPattern() {
      return this.positivemodelPattern;
   }

   public List getNegativepurePattern() {
      return this.negativepurePattern;
   }

   public List getNegativemodelPattern() {
      return this.negativemodelPattern;
   }
}
