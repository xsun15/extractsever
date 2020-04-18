package com.cjbdi.core.extractcenter.utils;


public class ZhNumber2NumberUsedForYearMonthDay {

   static char[] cnArr = new char[]{'\u4e00', '\u4e8c', '\u4e09', '\u56db', '\u4e94', '\u516d', '\u4e03', '\u516b', '\u4e5d'};
   static char[] chArr = new char[]{'\u5341', '\u767e', '\u5343', '\u4e07', '\u4ebf'};
   static String allChineseNum = "零一二三四五六七八九十百千万亿";


   public static int chineseNumToArabicNum(String chineseNum) {
      int result = 0;
      int temp = 1;
      int count = 0;
      int i = 0;

      while(i < chineseNum.length()) {
         boolean b = true;
         char c = chineseNum.charAt(i);
         int j = 0;

         while(true) {
            if(j < cnArr.length) {
               if(c != cnArr[j]) {
                  ++j;
                  continue;
               }

               if(0 != count) {
                  result += temp;
                  count = 0;
               }

               temp = j + 1;
               b = false;
            }

            if(b) {
               for(j = 0; j < chArr.length; ++j) {
                  if(c == chArr[j]) {
                     switch(j) {
                     case 0:
                        temp *= 10;
                        break;
                     case 1:
                        temp *= 100;
                        break;
                     case 2:
                        temp *= 1000;
                        break;
                     case 3:
                        temp *= 10000;
                        break;
                     case 4:
                        temp *= 100000000;
                     }

                     ++count;
                  }
               }
            }

            if(i == chineseNum.length() - 1) {
               result += temp;
            }

            ++i;
            break;
         }
      }

      return result;
   }

   public static boolean isChineseNum(String chineseStr) {
      char[] ch = chineseStr.toCharArray();
      char[] var2 = ch;
      int var3 = ch.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         char c = var2[var4];
         if(!allChineseNum.contains(String.valueOf(c))) {
            return false;
         }
      }

      return true;
   }

   public static boolean isNum(String str) {
      String reg = "[0-9]+";
      return str.matches(reg);
   }

}
