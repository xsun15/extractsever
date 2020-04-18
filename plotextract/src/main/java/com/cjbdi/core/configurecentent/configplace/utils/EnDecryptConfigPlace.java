package com.cjbdi.core.configurecentent.configplace.utils;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class EnDecryptConfigPlace {

   private List encrypt;
   private List lxfeature;


   public EnDecryptConfigPlace(String featureName, String sourceName) {
      this.encrypt = YamlPropertySourceFactoryUser.loadConfig(featureName + ".encrypt", sourceName);
      this.lxfeature = YamlPropertySourceFactoryUser.loadConfig(featureName + ".encryptplace.lxfeature", sourceName);
   }

   public boolean getEncrypt() {
      return this.encrypt != null && !this.encrypt.isEmpty() && StringUtils.strip(this.encrypt.toString(), "[]").equals("true");
   }

   public void setEncrypt(List encrypt) {
      this.encrypt = encrypt;
   }

   public String getLxfeature() {
      return StringUtils.strip(this.lxfeature.toString(), "[]");
   }

   public void setLxfeature(List lxfeature) {
      this.lxfeature = lxfeature;
   }
}
