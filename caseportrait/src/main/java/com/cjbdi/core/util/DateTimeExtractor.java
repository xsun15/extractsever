package com.cjbdi.core.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeExtractor {

   static List datePatterns = new ArrayList();
   static Pattern yearPattern;
   static Pattern monthPattern;
   static Pattern dayPattern;
   static Pattern tenDayPattern;
   static Pattern hourPattern;


   public static Optional extract(String text) {
      Iterator year = datePatterns.iterator();

      while(year.hasNext()) {
         Pattern month = (Pattern)year.next();
         Matcher day = month.matcher(text);
         if(day.find()) {
            text = day.group();
            break;
         }
      }

      int year1 = getNumberOrDefault(text, yearPattern, 0);
      int month1 = getNumberOrDefault(text, monthPattern, 0);
      int day1 = getDay(text, 0);
      int hour = getNumberOrDefault(text, hourPattern, 0);
      byte min = 0;
      Optional date = Optional.empty();
      if(year1 > 0 || month1 > 0 && day1 > 0) {
         if(month1 <= 0) {
            month1 = 1;
         }

         if(day1 <= 0) {
            day1 = 1;
         }

         try {
            LocalDateTime e = LocalDateTime.of(year1, month1, day1, hour, min);
            date = Optional.of(e);
         } catch (Exception var8) {
            date = Optional.empty();
         }
      }

      return date;
   }

   private static int getDay(String text, int defaultNumber) {
      Matcher matcher = dayPattern.matcher(text);
      String tenDayText;
      if(matcher.find()) {
         tenDayText = matcher.group();
         int date1 = Integer.parseInt(tenDayText.substring(0, tenDayText.length() - 1));
         return date1;
      } else {
         matcher = tenDayPattern.matcher(text);
         if(matcher.find()) {
            tenDayText = matcher.group();
            byte date;
            if(tenDayText.contains("上")) {
               date = 1;
            } else if(tenDayText.contains("中")) {
               date = 15;
            } else {
               date = 28;
            }

            return date;
         } else {
            return defaultNumber;
         }
      }
   }

   private static int getNumberOrDefault(String text, Pattern pattern, int defaultNumber) {
      Matcher matcher = pattern.matcher(text);
      if(matcher.find()) {
         String matchStr = matcher.group();
         int number = Integer.parseInt(matchStr.substring(0, matchStr.length() - 1));
         return number;
      } else {
         return defaultNumber;
      }
   }

   public static String getYear(String text) {
      Matcher matcher = yearPattern.matcher(text);
      if(matcher.find()) {
         String matchStr = matcher.group();
         return matchStr.replace("年", "");
      } else {
         return "";
      }
   }

   static {
      String ampmMinExp = ".{4}年.{1,2}月.{1,2}日[上中下]午.{1,2}时.{1,2}分";
      Pattern pattern = Pattern.compile(ampmMinExp);
      datePatterns.add(pattern);
      String minExp = ".{4}年.{1,2}月.{1,2}日.{1,2}时.{1,2}分";
      pattern = Pattern.compile(minExp);
      datePatterns.add(pattern);
      String ampmHourExp = ".{4}年.{1,2}月.{1,2}日[上中下]午.{1,2}时";
      pattern = Pattern.compile(ampmHourExp);
      datePatterns.add(pattern);
      String hourExp = ".{4}年.{1,2}月.{1,2}日.{1,2}时";
      pattern = Pattern.compile(hourExp);
      datePatterns.add(pattern);
      String dateExp = ".{4}年.{1,2}月.{1,2}日";
      pattern = Pattern.compile(dateExp);
      datePatterns.add(pattern);
      String monExp = ".{4}年.{1,2}月";
      pattern = Pattern.compile(monExp);
      datePatterns.add(pattern);
      String yearExp = ".{4}年";
      pattern = Pattern.compile(yearExp);
      datePatterns.add(pattern);
      yearPattern = Pattern.compile("\\d{2,4}年");
      monthPattern = Pattern.compile("\\d{1,2}月");
      dayPattern = Pattern.compile("\\d{1,2}日");
      tenDayPattern = Pattern.compile("[上中下]旬");
      hourPattern = Pattern.compile("\\d{1,2}时");
   }
}
