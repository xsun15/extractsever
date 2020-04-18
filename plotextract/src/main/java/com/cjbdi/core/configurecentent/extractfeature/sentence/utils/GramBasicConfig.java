package com.cjbdi.core.configurecentent.extractfeature.sentence.utils;

import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.ExtractFeatureTools;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.GramRatioBasic;
import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class GramBasicConfig {

   private List name;
   private List keyword;
   private List noiseword;
   private LinkedHashMap positivepurerule;
   private LinkedHashMap positivemodelrule;
   private LinkedHashMap negativepurerule;
   private LinkedHashMap negativemodelrule;
   private LinkedHashMap positivepurePattern;
   private LinkedHashMap positivemodelPattern;
   private LinkedHashMap negativepurePattern;
   private LinkedHashMap negativemodelPattern;
   private List gramRatioBasic;


   public GramBasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      ArrayList levelname = new ArrayList();
      levelname.add("firstrule");
      levelname.add("secondrule");
      levelname.add("thirdrule");
      levelname.add("fourthrule");
      levelname.add("fifthrule");
      this.positivepurerule = this.readYml(featureName, ".positivepurerule.", levelname, sourceName);
      this.positivemodelrule = this.readYml(featureName, ".positivemodelrule.", levelname, sourceName);
      this.negativepurerule = this.readYml(featureName, ".negativepurerule.", levelname, sourceName);
      this.negativemodelrule = this.readYml(featureName, ".negativemodelrule.", levelname, sourceName);
      this.keyword = YamlPropertySourceFactoryUser.loadConfig(featureName + ".keyword", sourceName);
      this.noiseword = YamlPropertySourceFactoryUser.loadConfig(featureName + ".noiseword", sourceName);
      this.gramRatioBasic = this.readYml(featureName + ".rule", sourceName);
      this.positivepurePattern = ExtractFeatureTools.toPattern(this.positivepurerule);
      this.positivemodelPattern = ExtractFeatureTools.toPattern(this.positivemodelrule);
      this.negativepurePattern = ExtractFeatureTools.toPattern(this.negativepurerule);
      this.negativemodelPattern = ExtractFeatureTools.toPattern(this.negativemodelrule);
   }

   public LinkedHashMap readYml(String featureName, String rule, List levelname, String sourceName) {
      LinkedHashMap map = new LinkedHashMap();
      Iterator var6 = levelname.iterator();

      while(var6.hasNext()) {
         String level = (String)var6.next();
         String name = featureName + rule + level;
         List ruleList = YamlPropertySourceFactoryUser.loadConfig(name, sourceName);
         if(ruleList != null && ruleList.size() > 0) {
            map.put(StringUtils.strip(level, "."), ruleList);
         }
      }

      return map;
   }

   public List readYml(String featureName, String sourceName) {
      ArrayList gramRatioBasicList = new ArrayList();
      List ruleList = YamlPropertySourceFactoryUser.loadConfig(featureName, sourceName);
      if(ruleList != null && ruleList.size() > 0) {
         Iterator var5 = ruleList.iterator();

         while(var5.hasNext()) {
            String rule = (String)var5.next();
            GramRatioBasic gramRatioBasic = new GramRatioBasic();
            gramRatioBasic.rule = rule.split("ratio")[0].trim();
            gramRatioBasic.ratio = Double.parseDouble(rule.split("ratio")[1].trim());
            gramRatioBasicList.add(gramRatioBasic);
         }
      }

      return gramRatioBasicList;
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public void setName(List name) {
      this.name = name;
   }

   public String getKeyword() {
      return StringUtils.strip(this.keyword.toString(), "[]");
   }

   public void setKeyword(List keyword) {
      this.keyword = keyword;
   }

   public String getNoiseword() {
      return StringUtils.strip(this.noiseword.toString(), "[]");
   }

   public void setNoiseword(List noiseword) {
      this.noiseword = noiseword;
   }

   public LinkedHashMap getPositivepurerule() {
      return this.positivepurerule;
   }

   public void setPositivepurerule(LinkedHashMap positivepurerule) {
      this.positivepurerule = positivepurerule;
   }

   public LinkedHashMap getPositivemodelrule() {
      return this.positivemodelrule;
   }

   public void setPositivemodelrule(LinkedHashMap positivemodelrule) {
      this.positivemodelrule = positivemodelrule;
   }

   public LinkedHashMap getNegativepurerule() {
      return this.negativepurerule;
   }

   public void setNegativepurerule(LinkedHashMap negativepurerule) {
      this.negativepurerule = negativepurerule;
   }

   public LinkedHashMap getNegativemodelrule() {
      return this.negativemodelrule;
   }

   public void setNegativemodelrule(LinkedHashMap negativemodelrule) {
      this.negativemodelrule = negativemodelrule;
   }

   public List getGramRatioBasic() {
      return this.gramRatioBasic;
   }

   public void setGramRatioBasic(List gramRatioBasic) {
      this.gramRatioBasic = gramRatioBasic;
   }

   public LinkedHashMap getPositivepurePattern() {
      return this.positivepurePattern;
   }

   public LinkedHashMap getPositivemodelPattern() {
      return this.positivemodelPattern;
   }

   public LinkedHashMap getNegativepurePattern() {
      return this.negativepurePattern;
   }

   public LinkedHashMap getNegativemodelPattern() {
      return this.negativemodelPattern;
   }
}
