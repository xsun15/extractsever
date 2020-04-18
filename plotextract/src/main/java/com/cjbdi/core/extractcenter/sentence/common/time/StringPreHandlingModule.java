package com.cjbdi.core.extractcenter.sentence.common.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPreHandlingModule {

   public static String delKeyword(String target, String rules) {
      Pattern p = Pattern.compile(rules);
      Matcher m = p.matcher(target);
      StringBuffer sb = new StringBuffer();

      for(boolean result = m.find(); result; result = m.find()) {
         m.appendReplacement(sb, "");
      }

      m.appendTail(sb);
      String s = sb.toString();
      return s;
   }

   public static String numberTranslator(String target) {
      Pattern p = Pattern.compile("[一二两三四五六七八九123456789]万[一二两三四五六七八九123456789](?!(千|百|十))");
      Matcher m = p.matcher(target);
      StringBuffer sb = new StringBuffer();

      boolean result;
      String group;
      String[] s;
      int num;
      for(result = m.find(); result; result = m.find()) {
         group = m.group();
         s = group.split("万");
         num = 0;
         if(s.length == 2) {
            num += wordToNumber(s[0]) * 10000 + wordToNumber(s[1]) * 1000;
         }

         m.appendReplacement(sb, Integer.toString(num));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("[一二两三四五六七八九123456789]千[一二两三四五六七八九123456789](?!(百|十))");
      m = p.matcher(target);
      sb = new StringBuffer();

      for(result = m.find(); result; result = m.find()) {
         group = m.group();
         s = group.split("千");
         num = 0;
         if(s.length == 2) {
            num += wordToNumber(s[0]) * 1000 + wordToNumber(s[1]) * 100;
         }

         m.appendReplacement(sb, Integer.toString(num));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("[一二两三四五六七八九123456789]百[一二两三四五六七八九123456789](?!十)");
      m = p.matcher(target);
      sb = new StringBuffer();

      for(result = m.find(); result; result = m.find()) {
         group = m.group();
         s = group.split("百");
         num = 0;
         if(s.length == 2) {
            num += wordToNumber(s[0]) * 100 + wordToNumber(s[1]) * 10;
         }

         m.appendReplacement(sb, Integer.toString(num));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("[零一二两三四五六七八九]");
      m = p.matcher(target);
      sb = new StringBuffer();

      for(result = m.find(); result; result = m.find()) {
         m.appendReplacement(sb, Integer.toString(wordToNumber(m.group())));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("(?<=(周|星期))[末天日]");
      m = p.matcher(target);
      sb = new StringBuffer();

      for(result = m.find(); result; result = m.find()) {
         m.appendReplacement(sb, Integer.toString(wordToNumber(m.group())));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("(?<!(周|星期))0?[0-9]?十[0-9]?");
      m = p.matcher(target);
      sb = new StringBuffer();

      int tenthousand;
      for(result = m.find(); result; result = m.find()) {
         group = m.group();
         s = group.split("十");
         num = 0;
         if(s.length == 0) {
            num += 10;
         } else if(s.length == 1) {
            tenthousand = Integer.parseInt(s[0]);
            if(tenthousand == 0) {
               num += 10;
            } else {
               num += tenthousand * 10;
            }
         } else if(s.length == 2) {
            if(s[0].equals("")) {
               num += 10;
            } else {
               tenthousand = Integer.parseInt(s[0]);
               if(tenthousand == 0) {
                  num += 10;
               } else {
                  num += tenthousand * 10;
               }
            }

            num += Integer.parseInt(s[1]);
         }

         m.appendReplacement(sb, Integer.toString(num));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("0?[1-9]百[0-9]?[0-9]?");
      m = p.matcher(target);
      sb = new StringBuffer();

      for(result = m.find(); result; result = m.find()) {
         group = m.group();
         s = group.split("百");
         num = 0;
         if(s.length == 1) {
            tenthousand = Integer.parseInt(s[0]);
            num += tenthousand * 100;
         } else if(s.length == 2) {
            tenthousand = Integer.parseInt(s[0]);
            num += tenthousand * 100;
            num += Integer.parseInt(s[1]);
         }

         m.appendReplacement(sb, Integer.toString(num));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("0?[1-9]千[0-9]?[0-9]?[0-9]?");
      m = p.matcher(target);
      sb = new StringBuffer();

      for(result = m.find(); result; result = m.find()) {
         group = m.group();
         s = group.split("千");
         num = 0;
         if(s.length == 1) {
            tenthousand = Integer.parseInt(s[0]);
            num += tenthousand * 1000;
         } else if(s.length == 2) {
            tenthousand = Integer.parseInt(s[0]);
            num += tenthousand * 1000;
            num += Integer.parseInt(s[1]);
         }

         m.appendReplacement(sb, Integer.toString(num));
      }

      m.appendTail(sb);
      target = sb.toString();
      p = Pattern.compile("[0-9]+万[0-9]?[0-9]?[0-9]?[0-9]?");
      m = p.matcher(target);
      sb = new StringBuffer();

      for(result = m.find(); result; result = m.find()) {
         group = m.group();
         s = group.split("万");
         num = 0;
         if(s.length == 1) {
            tenthousand = Integer.parseInt(s[0]);
            num += tenthousand * 10000;
         } else if(s.length == 2) {
            tenthousand = Integer.parseInt(s[0]);
            num += tenthousand * 10000;
            num += Integer.parseInt(s[1]);
         }

         m.appendReplacement(sb, Integer.toString(num));
      }

      m.appendTail(sb);
      target = sb.toString();
      return target;
   }

   private static int wordToNumber(String s) {
      return !s.equals("零") && !s.equals("0")?(!s.equals("一") && !s.equals("1")?(!s.equals("二") && !s.equals("两") && !s.equals("2")?(!s.equals("三") && !s.equals("3")?(!s.equals("四") && !s.equals("4")?(!s.equals("五") && !s.equals("5")?(!s.equals("六") && !s.equals("6")?(!s.equals("七") && !s.equals("天") && !s.equals("日") && !s.equals("末") && !s.equals("7")?(!s.equals("八") && !s.equals("8")?(!s.equals("九") && !s.equals("9")?-1:9):8):7):6):5):4):3):2):1):0;
   }
}
