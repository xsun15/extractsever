package com.cjbdi.core.extractcenter.sentence.utils;

import com.cjbdi.core.extractcenter.sentence.utils.RegexHelper;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class PrisonTermExtractor {

   private List isPunishedPatterns;
   private List year;
   private List month;
   private List shi;
   private Map daxieToxiaoxie = new HashMap();


   public PrisonTermExtractor() {
      this.daxieToxiaoxie = new HashMap();
      this.isPunishedPatterns = new ArrayList();
      this.month = new ArrayList();
      this.year = new ArrayList();
      this.shi = new ArrayList();
      String isPunishedExp1 = "有期徒刑.{1,5}个?年零?.{1,5}个?月";
      Pattern pattern = Pattern.compile(isPunishedExp1);
      this.isPunishedPatterns.add(pattern);
      String isPunishedExp2 = "有期徒刑.{1,5}个?年";
      pattern = Pattern.compile(isPunishedExp2);
      this.isPunishedPatterns.add(pattern);
      String isPunishedExp3 = "有期徒刑.{1,5}个?月";
      pattern = Pattern.compile(isPunishedExp3);
      this.isPunishedPatterns.add(pattern);
      Pattern year = Pattern.compile("(\\d{1,3}|(一|二|三|四|五|六|七|八|九|十|两){1,4})个?年");
      this.year.add(year);
      Pattern mon = Pattern.compile("(\\d{1,3}|(一|二|三|四|五|六|七|八|九|十|两){1,4})个?月");
      this.month.add(mon);
      Pattern shi = Pattern.compile(".十");
      this.shi.add(shi);
      this.daxieToxiaoxie.put("一", Integer.valueOf(1));
      this.daxieToxiaoxie.put("二", Integer.valueOf(2));
      this.daxieToxiaoxie.put("两", Integer.valueOf(2));
      this.daxieToxiaoxie.put("三", Integer.valueOf(3));
      this.daxieToxiaoxie.put("四", Integer.valueOf(4));
      this.daxieToxiaoxie.put("五", Integer.valueOf(5));
      this.daxieToxiaoxie.put("六", Integer.valueOf(6));
      this.daxieToxiaoxie.put("七", Integer.valueOf(7));
      this.daxieToxiaoxie.put("八", Integer.valueOf(8));
      this.daxieToxiaoxie.put("九", Integer.valueOf(9));
      this.daxieToxiaoxie.put("十", Integer.valueOf(10));
      this.daxieToxiaoxie.put("", Integer.valueOf(0));
   }

   private int getyear(String text) {
      try {
         if(RegexHelper.MatchAnyPatternResult(this.year, text).isPresent()) {
            String e = (String) RegexHelper.MatchAnyPatternResult(this.year, text).get();
            if(!Pattern.matches("[^\\d]*个?年", e)) {
               return Integer.parseInt(e.replaceAll("(个|年)", ""));
            } else {
               int num = 0;
               e = e.replaceAll("(个|年)", "");
               if(RegexHelper.MatchAnyPattern(this.shi, e)) {
                  String shi = (String) RegexHelper.MatchAnyPatternResult(this.shi, e).get();
                  num += 10 * ((Integer)this.daxieToxiaoxie.get(shi.replace("十", ""))).intValue();
                  e = e.replaceAll((String) RegexHelper.MatchAnyPatternResult(this.shi, shi).get(), "");
                  num += ((Integer)this.daxieToxiaoxie.get(e)).intValue();
               } else if(e.length() == 2 && e.startsWith("十")) {
                  num += 10 + ((Integer)this.daxieToxiaoxie.get(e.replace("十", ""))).intValue();
               } else if(e.length() == 1) {
                  num = ((Integer)this.daxieToxiaoxie.get(e)).intValue();
               }

               return num;
            }
         } else {
            return 0;
         }
      } catch (Exception var5) {
         return 0;
      }
   }

   private int getMon(String text) {
      try {
         if(RegexHelper.MatchAnyPatternResult(this.month, text).isPresent()) {
            String e = (String) RegexHelper.MatchAnyPatternResult(this.month, text).get();
            if(!Pattern.matches("[^\\d]个?月", e)) {
               return Integer.parseInt(e.replaceAll("(个|月)", ""));
            } else {
               int num = 0;
               e = e.replaceAll("(个|月)", "");
               if(RegexHelper.MatchAnyPattern(this.shi, e)) {
                  String shi = (String) RegexHelper.MatchAnyPatternResult(this.shi, e).get();
                  num += 10 * ((Integer)this.daxieToxiaoxie.get(shi.replace("十", ""))).intValue();
                  e = e.replaceAll((String) RegexHelper.MatchAnyPatternResult(this.shi, shi).get(), "");
                  num += ((Integer)this.daxieToxiaoxie.get(e)).intValue();
               } else if(e.length() == 2 && e.startsWith("十")) {
                  num += 10 + ((Integer)this.daxieToxiaoxie.get(e.replace("十", ""))).intValue();
               } else if(e.length() == 1) {
                  num = ((Integer)this.daxieToxiaoxie.get(e)).intValue();
               }

               return num;
            }
         } else {
            return 0;
         }
      } catch (Exception var5) {
         return 0;
      }
   }

   public int extract(String text, List PatternList) {
      int ret = 0;
      Map extractResult = MatchRule.matchPattern(text, PatternList);
      if(!extractResult.isEmpty()) {
         String target = (String)extractResult.get("text");
         ret += 12 * this.getyear(target);
         ret += this.getMon(target);
      }

      return ret;
   }
}
