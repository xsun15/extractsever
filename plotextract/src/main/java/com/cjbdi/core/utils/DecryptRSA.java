package com.cjbdi.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class DecryptRSA {

   public static String run(String str, String privateKey) {
      String outStr = "";
      if(StringUtils.isNotEmpty(privateKey)) {
         try {
            byte[] e = Base64.decodeBase64(str.getBytes("UTF-8"));
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey)KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, priKey);
            outStr = new String(cipher.doFinal(e));
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      } else {
         outStr = str;
      }
      return outStr;
   }

   public static List<String> run(List source, String privateKey) {
      List<String> outList = new ArrayList();
      if(source != null && source.size() > 0) {
         Iterator var = source.iterator();
         while(var.hasNext()) {
            String content = (String)var.next();
            outList.add(run(content, privateKey).trim());
         }
      }
      return outList;
   }
}
