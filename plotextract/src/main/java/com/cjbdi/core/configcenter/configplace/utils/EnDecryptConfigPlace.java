package com.cjbdi.core.configcenter.configplace.utils;

import java.util.HashMap;

public class EnDecryptConfigPlace {
   private boolean isEncrypt;


   public EnDecryptConfigPlace(HashMap<String, HashMap<String, Object>> configPlace) {
      String source = configPlace.get("enDecrypt").get("encrypt").toString();
      if (source.equalsIgnoreCase("true")) isEncrypt=true;
      else if (source.equalsIgnoreCase("false")) isEncrypt=false;
   }

   public boolean getIsEncrypt() {
      return isEncrypt;
   }
}
