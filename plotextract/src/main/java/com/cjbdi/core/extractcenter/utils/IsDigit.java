package com.cjbdi.core.extractcenter.utils;

import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class IsDigit {

   public static boolean isInteger(String str) {
      Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
      return pattern.matcher(str).matches();
   }

   public static boolean isDouble(String str) {
      if(StringUtils.isNotEmpty(str)) {
         String reg = "^[0-9]+(.[0-9]+)?$";
         return str.matches(reg);
      } else {
         return false;
      }
   }
}
