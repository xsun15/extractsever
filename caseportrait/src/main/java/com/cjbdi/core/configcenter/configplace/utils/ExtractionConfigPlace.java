package com.cjbdi.core.configcenter.configplace.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ExtractionConfigPlace {

   private List<String> province;
   private List<String> casecause;
   private List<String> personparty;
   private List<String> portray;


   public ExtractionConfigPlace(String featureName, String sourceName) {
      this.province = YamlPropertySourceFactoryUser.loadConfig(featureName + ".province", sourceName);
      this.casecause = YamlPropertySourceFactoryUser.loadConfig(featureName + ".casecause", sourceName);
      this.personparty = YamlPropertySourceFactoryUser.loadConfig(featureName + ".personparty", sourceName);
      this.portray = YamlPropertySourceFactoryUser.loadConfig(featureName + ".portray", sourceName);
   }

   public String getProvince() {
      return StringUtils.strip(this.province.toString(), "[]");
   }

   public void setProvince(List<String> province) {
      this.province = province;
   }

   public String getCasecause() {
      return StringUtils.strip(this.casecause.toString(), "[]");
   }

   public void setCasecause(List<String> casecause) {
      this.casecause = casecause;
   }

   public String getPersonparty() {
      return StringUtils.strip(this.personparty.toString(), "[]");
   }

   public void setPersonparty(List<String> personparty) {
      this.personparty = personparty;
   }

   public String getPortray() {
      return StringUtils.strip(this.portray.toString(), "[]");
   }

   public void setPortray(List<String> portray) {
      this.portray = portray;
   }
}
