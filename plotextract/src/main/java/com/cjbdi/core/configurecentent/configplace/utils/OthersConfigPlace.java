package com.cjbdi.core.configurecentent.configplace.utils;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class OthersConfigPlace {

   private List time;
   private List interfaceurl;
   private List factsplit;
   private List casecause;


   public OthersConfigPlace(String featureName, String sourceName) {
      this.time = YamlPropertySourceFactoryUser.loadConfig(featureName + ".time", sourceName);
      this.interfaceurl = YamlPropertySourceFactoryUser.loadConfig(featureName + ".interfaceurl", sourceName);
      this.factsplit = YamlPropertySourceFactoryUser.loadConfig(featureName + ".factsplit", sourceName);
      this.casecause = YamlPropertySourceFactoryUser.loadConfig(featureName + ".casecause", sourceName);
   }

   public String getTime() {
      return StringUtils.strip(this.time.toString(), "[]");
   }

   public void setTime(List time) {
      this.time = time;
   }

   public String getInterfaceurl() {
      return StringUtils.strip(this.interfaceurl.toString(), "[]");
   }

   public void setInterfaceurl(List interfaceurl) {
      this.interfaceurl = interfaceurl;
   }

   public String getFactsplit() {
      return StringUtils.strip(this.factsplit.toString(), "[]");
   }

   public void setFactsplit(List factsplit) {
      this.factsplit = factsplit;
   }

   public String getCasecause() {
      return StringUtils.strip(this.casecause.toString(), "[]");
   }

   public void setCasecause(List casecause) {
      this.casecause = casecause;
   }
}
