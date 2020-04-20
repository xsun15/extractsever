package com.cjbdi.core.configcenter.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

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

   public static List run(List source, String privateKey) {
      ArrayList outList = new ArrayList();
      if(source != null && source.size() > 0) {
         Iterator var3 = source.iterator();

         while(var3.hasNext()) {
            String content = (String)var3.next();
            outList.add(run(content, privateKey).trim());
         }
      }

      return outList;
   }

   public static LinkedHashMap run(LinkedHashMap source, String privateKey) {
      LinkedHashMap map = new LinkedHashMap();
      if(source != null && source.size() > 0) {
         Iterator var3 = source.keySet().iterator();

         while(var3.hasNext()) {
            String name = (String)var3.next();
            map.put(name, run((List)source.get(name), privateKey));
         }
      }

      return map;
   }
}
