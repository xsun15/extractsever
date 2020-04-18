package com.cjbdi.core.extractcenter.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class CleanText {

   public static String run(String content) {
      content = content.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：*×\\n《》（）/]+", "");
      List contentList = Arrays.asList(content.split("\n"));
      String result = "";
      Iterator var3 = contentList.iterator();

      while(var3.hasNext()) {
         String line = (String)var3.next();
         if(StringUtils.isNotEmpty(line.trim().replaceAll("\\s+", ""))) {
            result = result + line + "\n";
         }
      }

      return result.trim();
   }
}
