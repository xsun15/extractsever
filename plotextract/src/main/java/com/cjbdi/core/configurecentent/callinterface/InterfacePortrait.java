package com.cjbdi.core.configurecentent.callinterface;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class InterfacePortrait {

   private List ip;
   private List docsplitraw;
   private List docsplit;
   private List docportray;
   private List basicinfo;


   public InterfacePortrait(String featureName, String sourceName) {
      this.ip = YamlPropertySourceFactoryUser.loadConfig(featureName + ".ip", sourceName);
      this.docsplitraw = YamlPropertySourceFactoryUser.loadConfig(featureName + ".docsplitraw", sourceName);
      this.docsplit = YamlPropertySourceFactoryUser.loadConfig(featureName + ".docsplit", sourceName);
      this.docportray = YamlPropertySourceFactoryUser.loadConfig(featureName + ".docportray", sourceName);
      this.basicinfo = YamlPropertySourceFactoryUser.loadConfig(featureName + ".basicinfo", sourceName);
   }

   public String getIp() {
      return StringUtils.strip(this.ip.toString(), "[]");
   }

   public void setIp(List ip) {
      this.ip = ip;
   }

   public String getDocsplitraw() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.docsplitraw.toString(), "[]");
   }

   public void setDocsplitraw(List docsplitraw) {
      this.docsplitraw = docsplitraw;
   }

   public String getDocsplit() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.docsplit.toString(), "[]");
   }

   public void setDocsplit(List docsplit) {
      this.docsplit = docsplit;
   }

   public String getDocportray() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.docportray.toString(), "[]");
   }

   public void setDocportray(List docportray) {
      this.docportray = docportray;
   }

   public String getBasicinfo() {
      return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.basicinfo.toString(), "[]");
   }

   public void setBasicinfo(List basicinfo) {
      this.basicinfo = basicinfo;
   }
}
