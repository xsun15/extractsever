package com.cjbdi.core.extractcenter.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.cjbdi.core.servercenter.utils.Tools;
import org.apache.commons.lang.StringUtils;

public class CleanText {

   public static String run(String content) {
      content = content.replaceAll("＊", "某");
      content = content.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：:*×\\n《》（）Oo〇/]+","");
      String[] contentList = content.split("\n");
      String result = "";
      for (String line : contentList) {
         if (! "".equals(line.trim().replaceAll("\\s+", ""))) {
            result += line + "\n";
         }
      }
      result = Tools.deleteDigitDot(result);
      return result.trim();
   }
}
