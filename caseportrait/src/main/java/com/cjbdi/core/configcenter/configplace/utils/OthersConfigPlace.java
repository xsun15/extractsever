package com.cjbdi.core.configcenter.configplace.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class OthersConfigPlace {

   private List<String> interfaceurl;

   public OthersConfigPlace(String featureName, String sourceName) {
      this.interfaceurl = YamlPropertySourceFactoryUser.loadConfig(featureName + ".interfaceurl", sourceName);
   }

   public String getInterfaceurl() {
      return StringUtils.strip(this.interfaceurl.toString(), "[]");
   }

   public void setInterfaceurl(List interfaceurl) {
      this.interfaceurl = interfaceurl;
   }

}
