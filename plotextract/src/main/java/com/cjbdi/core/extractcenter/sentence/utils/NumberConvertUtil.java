package com.cjbdi.core.extractcenter.sentence.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberConvertUtil {

   private static final String _num_regex = "\\d[\\d,，]*(\\.\\d+)?";
   private static final Pattern _num_pattern = Pattern.compile("\\d[\\d,，]*(\\.\\d+)?");
   public static final String _numcharacter = "[0-9一二三四五六七八九十〇百千万亿壹贰叁肆伍陆柒捌玖零拾佰仟萬億]";
   private static final String _num_ch_regex = "[0-9一二三四五六七八九十〇百千万亿壹贰叁肆伍陆柒捌玖零拾佰仟萬億]+";
   private static final Pattern _num_ch_pattern = Pattern.compile("[0-9一二三四五六七八九十〇百千万亿壹贰叁肆伍陆柒捌玖零拾佰仟萬億]+");
   private static String[] _chNumbers = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "两", "廿", "卅", "〇", "○"};
   private static String[] _chBigNumbers = new String[]{"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "俩", "廿", "卅", "零", "零"};
   private static Long[] _tonumbers = new Long[]{Long.valueOf(1L), Long.valueOf(2L), Long.valueOf(3L), Long.valueOf(4L), Long.valueOf(5L), Long.valueOf(6L), Long.valueOf(7L), Long.valueOf(8L), Long.valueOf(9L), Long.valueOf(2L), Long.valueOf(2L), Long.valueOf(3L), Long.valueOf(0L), Long.valueOf(0L)};
   private static String[] _mults = new String[]{"亿", "万", "千", "百", "十"};
   private static String[] _bigMults = new String[]{"億", "萬", "仟", "佰", "拾"};
   private static Long[] _toMults = new Long[]{Long.valueOf(100000000L), Long.valueOf(10000L), Long.valueOf(1000L), Long.valueOf(100L), Long.valueOf(10L)};


   public static Optional extract(String str) {
      if(str.contains("两")) {
         return Optional.of(Integer.valueOf(2));
      } else {
         Matcher numMatcher = _num_pattern.matcher(str);
         if(numMatcher.find()) {
            String num2 = numMatcher.group();
            num2 = num2.replaceAll("[,，]", "");
            float num1 = Float.valueOf(num2).floatValue();
            return Optional.of(Integer.valueOf((int)num1));
         } else {
            numMatcher = _num_ch_pattern.matcher(str);
            if(numMatcher.find()) {
               float num = (float)bigToSmall(numMatcher.group()).longValue();
               return Optional.of(Integer.valueOf((int)num));
            } else {
               return Optional.empty();
            }
         }
      }
   }

   public static List extractAll(String str) {
      ArrayList numbers = new ArrayList();
      Matcher numMatcher = _num_pattern.matcher(str);

      while(numMatcher.find()) {
         String num = numMatcher.group();
         num = num.replaceAll("[,，]", "");
         float num1 = Float.valueOf(num).floatValue();
         numbers.add(Integer.valueOf((int)num1));
      }

      numMatcher = _num_ch_pattern.matcher(str);

      while(numMatcher.find()) {
         float num2 = (float)bigToSmall(numMatcher.group()).longValue();
         numbers.add(Integer.valueOf((int)num2));
      }

      return numbers;
   }

   public static float extractNum(String str) {
      float num = 0.0F;
      Matcher numMatcher = _num_pattern.matcher(str);
      if(numMatcher.find()) {
         String s = numMatcher.group();
         s = s.replaceAll("[,，]", "");
         num = Float.valueOf(s).floatValue();
      }

      return num;
   }

   public static float extractChNum(String str) {
      float num = 0.0F;
      Matcher numMatcher = _num_ch_pattern.matcher(str);
      if(numMatcher.find()) {
         num = (float)bigToSmall(numMatcher.group()).longValue();
      }

      return num;
   }

   private static Long bigToSmall(String money) {
      Long number = Long.valueOf(0L);

      for(int i = 0; i < _mults.length; ++i) {
         int index = money.lastIndexOf(_mults[i]) == -1?money.lastIndexOf(_bigMults[i]):money.lastIndexOf(_mults[i]);
         if(index >= 0) {
            String storeMult = money.substring(0, index);
            money = money.substring(index + 1);
            if(storeMult != null && storeMult.length() > 0) {
               number = Long.valueOf(number.longValue() + _toMults[i].longValue() * bigToSmall(storeMult).longValue());
            } else {
               number = Long.valueOf(number.longValue() + _toMults[i].longValue());
            }
         }
      }

      number = Long.valueOf(number.longValue() + getNumByBig(money).longValue());
      return number;
   }

   private static Long getNumByBig(String big) {
      Long result = Long.valueOf(0L);

      for(int e = 0; e < _chNumbers.length; ++e) {
         big = big.replaceAll(_chNumbers[e], _tonumbers[e].toString());
         big = big.replaceAll(_chBigNumbers[e], _tonumbers[e].toString());
      }

      try {
         result = Long.valueOf(big);
      } catch (Exception var3) {
         result = Long.valueOf(0L);
      }

      return result;
   }

}
