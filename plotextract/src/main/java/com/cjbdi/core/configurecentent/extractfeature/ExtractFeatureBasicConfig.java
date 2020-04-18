package com.cjbdi.core.configurecentent.extractfeature;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ExtractFeatureBasicConfig {

   private List name;
   private List type;
   private List code;
   private List keyword;
   private List noiseword;
   private List ptype;
   private List positivepurerule;
   private List positivemodelrule;
   private List negativepurerule;
   private List negativemodelrule;
   private List negativecasecause;
   private List positivepurePattern;
   private List positivemodelPattern;
   private List negativepurePattern;
   private List negativemodelPattern;


   public ExtractFeatureBasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      this.type = YamlPropertySourceFactoryUser.loadConfig(featureName + ".type", sourceName);
      this.code = YamlPropertySourceFactoryUser.loadConfig(featureName + ".code", sourceName);
      this.positivepurerule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".positivepurerule", sourceName);
      this.positivemodelrule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".positivemodelrule", sourceName);
      this.negativepurerule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".negativepurerule", sourceName);
      this.negativecasecause = YamlPropertySourceFactoryUser.loadConfig(featureName + ".negativecasecause", sourceName);
      this.keyword = YamlPropertySourceFactoryUser.loadConfig(featureName + ".keyword", sourceName);
      this.noiseword = YamlPropertySourceFactoryUser.loadConfig(featureName + ".noiseword", sourceName);
      this.ptype = YamlPropertySourceFactoryUser.loadConfig(featureName + ".ptype", sourceName);
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public String getType() {
      return StringUtils.strip(this.type.toString(), "[]");
   }

   public String getCode() {
      return StringUtils.strip(this.code.toString(), "[]");
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

   public List getNegativecasecause() {
      return this.negativecasecause;
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

   public void setNegativecasecause(List negativecasecause) {
      this.negativecasecause = negativecasecause;
   }

   public String getKeyword() {
      return StringUtils.strip(this.keyword.toString(), "[]");
   }

   public void setKeyword(List keyword) {
      this.keyword = keyword;
   }

   public void setKeyword(String keyword) {
      this.keyword.clear();
      this.keyword.add(keyword);
   }

   public String getNoiseword() {
      return StringUtils.strip(this.noiseword.toString(), "[]");
   }

   public void setNoiseword(List noiseword) {
      this.noiseword = noiseword;
   }

   public void setNoiseword(String noiseword) {
      this.noiseword.clear();
      this.noiseword.add(noiseword);
   }

   public String getPtype() {
      return StringUtils.strip(this.ptype.toString(), "[]");
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

   public void setPositivepurePattern(List positivepurePattern) {
      this.positivepurePattern = positivepurePattern;
   }

   public void setPositivemodelPattern(List positivemodelPattern) {
      this.positivemodelPattern = positivemodelPattern;
   }

   public void setNegativepurePattern(List negativepurePattern) {
      this.negativepurePattern = negativepurePattern;
   }

   public void setNegativemodelPattern(List negativemodelPattern) {
      this.negativemodelPattern = negativemodelPattern;
   }
}
