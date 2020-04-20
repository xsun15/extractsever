package com.cjbdi.core.configcenter.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GetFeatureName {

   public static ArrayList run(String filename) {
      ArrayList featureNameList = new ArrayList();

      try {
         ClassPathResource e = new ClassPathResource(filename);
         FileReader fr = new FileReader(e.getFile());
         BufferedReader bf = new BufferedReader(fr);

         String str;
         while((str = bf.readLine()) != null) {
            if(str.contains("extract") || str.contains("Extract")) {
               featureNameList.add(StringUtils.strip(str.trim(), ":"));
            }
         }

         bf.close();
         fr.close();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

      return featureNameList;
   }
}
