package com.cjbdi.core.extractcenter.sentence.utils;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexHelper {

   private static Logger _logger = LoggerFactory.getLogger(RegexHelper.class);
   private static String usedRegx = "";


   public static boolean MatchAnyString(List regexs, String text) {
      boolean isMatch = false;
      List patterns = compileList(regexs);
      Iterator var4 = patterns.iterator();

      while(var4.hasNext()) {
         Pattern pattern = (Pattern)var4.next();
         Matcher matcher = pattern.matcher(text);
         if(matcher.find()) {
            isMatch = true;
            break;
         }
      }

      return isMatch;
   }

   public static Optional MatchAnyStringResult(List regexs, String text) {
      List patterns = compileList(regexs);
      Iterator var3 = patterns.iterator();

      Matcher matcher;
      do {
         if(!var3.hasNext()) {
            return Optional.empty();
         }

         Pattern pattern = (Pattern)var3.next();
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      return Optional.ofNullable(matcher.group());
   }

   public static Optional MatchAnyStringAllResults(List regexs, String text) {
      ArrayList results = new ArrayList();
      List patterns = compileList(regexs);
      Iterator var4 = patterns.iterator();

      while(var4.hasNext()) {
         Pattern pattern = (Pattern)var4.next();
         Matcher matcher = pattern.matcher(text);
         if(matcher.find()) {
            results.add(matcher.group());
         }
      }

      if(results.size() > 0) {
         return Optional.ofNullable(results);
      } else {
         return Optional.empty();
      }
   }

   public static boolean MatchAnyPattern(List patterns, String text) {
      boolean isMatch = false;
      Iterator var3 = patterns.iterator();

      while(var3.hasNext()) {
         Pattern pattern = (Pattern)var3.next();
         Matcher matcher = pattern.matcher(text);
         if(matcher.find()) {
            usedRegx = pattern.toString();
            isMatch = true;
            break;
         }
      }

      return isMatch;
   }

   public static String getUsedRegx() {
      return usedRegx;
   }

   public static Optional MatchAnyPatternResult(List patterns, String text) {
      boolean isMatch = false;
      Iterator var3 = patterns.iterator();

      Matcher matcher;
      do {
         if(!var3.hasNext()) {
            return Optional.empty();
         }

         Pattern pattern = (Pattern)var3.next();
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      return Optional.ofNullable(matcher.group());
   }

   public static Optional MatchAnyPatternAllResults(List patterns, String text) {
      ArrayList results = new ArrayList();
      boolean isMatch = false;
      Iterator var4 = patterns.iterator();

      while(var4.hasNext()) {
         Pattern pattern = (Pattern)var4.next();
         Matcher matcher = pattern.matcher(text);
         if(matcher.find()) {
            results.add(matcher.group());
         }
      }

      if(results.size() > 0) {
         return Optional.ofNullable(results);
      } else {
         return Optional.empty();
      }
   }

   public static List compileList(List regexs) {
      ArrayList patterns = new ArrayList(regexs.size());
      Iterator var2 = regexs.iterator();

      while(var2.hasNext()) {
         String regex = (String)var2.next();

         try {
            patterns.add(Pattern.compile(regex));
         } catch (PatternSyntaxException var5) {
            _logger.error("Regex {} compile meet error.", var5, new Object[]{regex});
         }
      }

      return patterns;
   }

   public static List compileListAndRepalceDefendent(List regexs, String defendent, Map defendantTable) {
      ArrayList patterns = new ArrayList(regexs.size());
      Iterator var4 = regexs.iterator();

      while(var4.hasNext()) {
         String regex = (String)var4.next();

         try {
            if(defendantTable.size() == 1) {
               patterns.add(Pattern.compile(regex));
            } else {
               patterns.add(Pattern.compile(regex.replace("被告人", defendent).replace("被不起诉人", defendent)));
            }
         } catch (PatternSyntaxException var7) {
            _logger.error("Regex {} compile meet error.", var7, new Object[]{regex});
         }
      }

      return patterns;
   }

   public static String matchedCompleteSentences(List patternList, String lineText) {
      if(MatchAnyPatternResult(patternList, lineText).isPresent()) {
         String result = (String)MatchAnyPatternResult(patternList, lineText).get();
         String[] setArray = lineText.split("[，,。]");

         for(int i = 0; i < setArray.length; ++i) {
            if(setArray[i].contains(result)) {
               return setArray[i];
            }
         }

         return result;
      } else {
         return "";
      }
   }

}
