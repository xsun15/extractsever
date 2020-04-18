package com.cjbdi.core.configurecentent.extractfeature.sentence.utils;

import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.ExtractFeatureTools;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyRatioBasic;
import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class MoneyBasicConfig {

   private List<String> name;
   private List<String> money;
   private List<MoneyRatioBasic> moneyRatio;
   private LinkedHashMap<String, LinkedHashMap<String, List<String>>> positivepurerule;
   private LinkedHashMap<String, LinkedHashMap<String, List<String>>> positivemodelrule;
   private LinkedHashMap<String, LinkedHashMap<String, List<String>>> negativepurerule;
   private LinkedHashMap<String, LinkedHashMap<String, List<String>>> negativemodelrule;
   private LinkedHashMap<String, LinkedHashMap<String, List<String>>> dockpurerule;
   private LinkedHashMap<String, LinkedHashMap<String, List<String>>> dockmodelrule;
   private LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> positivepurePattern;
   private LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> positivemodelPattern;
   private LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> negativepurePattern;
   private LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> negativemodelPattern;
   private LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> dockpurePattern;
   private LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> dockmodelPattern;


   public MoneyBasicConfig(String featureName, String sourceName) {
      this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
      ArrayList<String> levelname = new ArrayList();
      levelname.add("firstrule");
      levelname.add("secondrule");
      levelname.add("thirdrule");
      levelname.add("fourthrule");
      levelname.add("fifthrule");
      levelname.add("modelrule");
      levelname.add("convertrule");
      levelname.add("mustrule");
      ArrayList<String> casecauseList = new ArrayList();
      casecauseList.add("default.");
      casecauseList.add("steal.");
      casecauseList.add("fraud.");
      casecauseList.add("robbery.");
      casecauseList.add("findtrouble.");
      casecauseList.add("seizing.");
      casecauseList.add("dutyencroachment.");
      casecauseList.add("extortion.");
      casecauseList.add("endangerpa.");
      casecauseList.add("affary.");
      casecauseList.add("conceal.");
      casecauseList.add("counterfeitFraud.");
      casecauseList.add("fraudulentIdentificationFraud.");
      casecauseList.add("invalidFraud.");
      casecauseList.add("maliciousOverDraftFraud.");
      casecauseList.add("prostitution.");
      casecauseList.add("individualContractFraud.");
      casecauseList.add("individualFundRaisingFraud.");
      casecauseList.add("individualIllegallyAbsorbingPublicDeposits.");
      casecauseList.add("companyContractFraud.");
      casecauseList.add("companyFundRaisingFraud.");
      casecauseList.add("companyIllegallyAbsorbingPublicDeposits.");
      this.positivepurerule = this.readYml(featureName, ".positivepurerule.", levelname, casecauseList, sourceName);
      this.positivemodelrule = this.readYml(featureName, ".positivemodelrule.", levelname, casecauseList, sourceName);
      this.negativepurerule = this.readYml(featureName, ".negativepurerule.", levelname, casecauseList, sourceName);
      this.negativemodelrule = this.readYml(featureName, ".negativemodelrule.", levelname, casecauseList, sourceName);
      this.dockpurerule = this.readYml(featureName, ".dockpurerule.", levelname, casecauseList, sourceName);
      this.dockmodelrule = this.readYml(featureName, ".dockmodelrule.", levelname, casecauseList, sourceName);
      this.money = YamlPropertySourceFactoryUser.loadConfig(featureName + ".money", sourceName);
      this.moneyRatio = this.readYml(featureName + ".rule", sourceName);
      this.positivepurePattern = ExtractFeatureTools.toPatternMap2(this.positivepurerule);
      this.positivemodelPattern = ExtractFeatureTools.toPatternMap2(this.positivemodelrule);
      this.negativepurePattern = ExtractFeatureTools.toPatternMap2(this.negativepurerule);
      this.negativemodelPattern = ExtractFeatureTools.toPatternMap2(this.negativemodelrule);
      this.dockpurePattern = ExtractFeatureTools.toPatternMap2(this.dockpurerule);
      this.dockmodelPattern = ExtractFeatureTools.toPatternMap2(this.dockmodelrule);
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<String>>> readYml(String featureName, String rule, List levelname, List casecauseList, String sourceName) {
      LinkedHashMap map2 = new LinkedHashMap();
      Iterator var7 = casecauseList.iterator();

      while(var7.hasNext()) {
         String casecause = (String)var7.next();
         LinkedHashMap map = new LinkedHashMap();
         Iterator var10 = levelname.iterator();

         while(var10.hasNext()) {
            String level = (String)var10.next();
            String name = featureName + rule + casecause + level;
            List ruleList = YamlPropertySourceFactoryUser.loadConfig(name, sourceName);
            if(ruleList != null && ruleList.size() > 0) {
               map.put(StringUtils.strip(level, "."), ruleList);
            }
         }

         map2.put(StringUtils.strip(casecause, "."), map);
      }

      return map2;
   }

   public List<MoneyRatioBasic> readYml(String featureName, String sourceName) {
      ArrayList moneyRatioList = new ArrayList();
      List ruleList = YamlPropertySourceFactoryUser.loadConfig(featureName, sourceName);
      if(ruleList != null && ruleList.size() > 0) {
         Iterator var5 = ruleList.iterator();

         while(var5.hasNext()) {
            String rule = (String)var5.next();
            MoneyRatioBasic moneyRatio = new MoneyRatioBasic();
            moneyRatio.rule = rule.split("ratio")[0].trim();
            moneyRatio.ratio = Double.parseDouble(rule.split("ratio")[1].trim());
            moneyRatioList.add(moneyRatio);
         }
      }

      return moneyRatioList;
   }

   public String getName() {
      return StringUtils.strip(this.name.toString(), "[]");
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getPositivepurerule() {
      return this.positivepurerule;
   }

   public void setPositivepurerule(LinkedHashMap positivepurerule) {
      this.positivepurerule = positivepurerule;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getPositivemodelrule() {
      return this.positivemodelrule;
   }

   public void setPositivemodelrule(LinkedHashMap positivemodelrule) {
      this.positivemodelrule = positivemodelrule;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getNegativepurerule() {
      return this.negativepurerule;
   }

   public void setNegativepurerule(LinkedHashMap negativepurerule) {
      this.negativepurerule = negativepurerule;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getNegativemodelrule() {
      return this.negativemodelrule;
   }

   public void setNegativemodelrule(LinkedHashMap negativemodelrule) {
      this.negativemodelrule = negativemodelrule;
   }

   public List<String> getMoney() {
      return this.money;
   }

   public void setMoney(List money) {
      this.money = money;
   }

   public void setName(List name) {
      this.name = name;
   }

   public List<MoneyRatioBasic> getMoneyRatio() {
      return this.moneyRatio;
   }

   public void setMoneyRatio(List moneyRatio) {
      this.moneyRatio = moneyRatio;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getDockpurerule() {
      return this.dockpurerule;
   }

   public void setDockpurerule(LinkedHashMap dockpurerule) {
      this.dockpurerule = dockpurerule;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<String>>> getDockmodelrule() {
      return this.dockmodelrule;
   }

   public void setDockmodelrule(LinkedHashMap dockmodelrule) {
      this.dockmodelrule = dockmodelrule;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> getPositivepurePattern() {
      return this.positivepurePattern;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> getPositivemodelPattern() {
      return this.positivemodelPattern;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> getNegativepurePattern() {
      return this.negativepurePattern;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> getNegativemodelPattern() {
      return this.negativemodelPattern;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> getDockpurePattern() {
      return this.dockpurePattern;
   }

   public LinkedHashMap<String, LinkedHashMap<String, List<Pattern>>> getDockmodelPattern() {
      return this.dockmodelPattern;
   }
}
