package com.cjbdi.core.configcenter.configplace.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class StructurationConfigPlace {

   private List<String> factsplit;
   private List<String> firsttrial;
   private List<String> indicitopinion;
   private List<String> indictment;
   private List<String> nopros;
   private List<String> trialreport;


   public StructurationConfigPlace(String featureName, String sourceName) {
      this.factsplit = YamlPropertySourceFactoryUser.loadConfig(featureName + ".factsplit", sourceName);
      this.firsttrial = YamlPropertySourceFactoryUser.loadConfig(featureName + ".firsttrial", sourceName);
      this.indicitopinion = YamlPropertySourceFactoryUser.loadConfig(featureName + ".indicitopinion", sourceName);
      this.indictment = YamlPropertySourceFactoryUser.loadConfig(featureName + ".indictment", sourceName);
      this.nopros = YamlPropertySourceFactoryUser.loadConfig(featureName + ".nopros", sourceName);
      this.trialreport = YamlPropertySourceFactoryUser.loadConfig(featureName + ".trialreport", sourceName);
   }

   public String getFactsplit() {
      return StringUtils.strip(this.factsplit.toString(), "[]");
   }

   public void setFactsplit(List<String> factsplit) {
      this.factsplit = factsplit;
   }

   public String getFirsttrial() {
      return StringUtils.strip(this.firsttrial.toString(), "[]");
   }

   public void setFirsttrial(List<String> firsttrial) {
      this.firsttrial = firsttrial;
   }

   public String getIndicitopinion() {
      return StringUtils.strip(this.indicitopinion.toString(), "[]");
   }

   public void setIndicitopinion(List<String> indicitopinion) {
      this.indicitopinion = indicitopinion;
   }

   public String getIndictment() {
      return StringUtils.strip(this.indictment.toString(), "[]");
   }

   public void setIndictment(List<String> indictment) {
      this.indictment = indictment;
   }

   public String getNopros() {
      return StringUtils.strip(this.nopros.toString(), "[]");
   }

   public void setNopros(List<String> nopros) {
      this.nopros = nopros;
   }

   public String getTrialreport() {
      return StringUtils.strip(this.trialreport.toString(), "[]");
   }

   public void setTrialreport(List<String> trialreport) {
      this.trialreport = trialreport;
   }
}
