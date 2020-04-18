package com.cjbdi.test;

import com.cjbdi.core.extractcenter.sentence.common.time.TimeNormalizer;
import com.cjbdi.core.extractcenter.sentence.common.time.TimeUnit;
import java.util.regex.Pattern;

public class Time {

   public static void main(String[] args) {
      test();
   }

   public static void test() {
      String classPath = "/app/develop/plotextract/src/main/java/com/cjbdi/test/TimeExp.m";
      TimeNormalizer normalizer = new TimeNormalizer(classPath);
      normalizer.parse("是4月1日");
      TimeUnit[] unit = normalizer.getTimeUnit();
      System.out.println(unit[0].Time_Expression + "-" + unit[0].getIsAllDayTime());
   }

   public void editTimeExp() {
      String path = TimeNormalizer.class.getResource("").getPath();
      String classPath = path.substring(0, path.indexOf("/com/time"));
      System.out.println(classPath + "/TimeExp.m");
      Pattern p = Pattern.compile("your-regex");

      try {
         TimeNormalizer.writeModel(p, classPath + "/TimeExp.m");
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }
}
