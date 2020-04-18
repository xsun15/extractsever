package com.cjbdi.core.extractcenter.utils;


public class WordfigureToNumber {

   public static String run(String text) {
      text = text.replaceAll("九十", "90");
      text = text.replaceAll("八十", "80");
      text = text.replaceAll("七十", "70");
      text = text.replaceAll("六十", "60");
      text = text.replaceAll("五十", "50");
      text = text.replaceAll("四十", "40");
      text = text.replaceAll("三十", "30");
      text = text.replaceAll("二十", "20");
      text = text.replaceAll("十", "10");
      text = text.replaceAll("零", "0");
      text = text.replaceAll("一", "1");
      text = text.replaceAll("二", "2");
      text = text.replaceAll("三", "3");
      text = text.replaceAll("四", "4");
      text = text.replaceAll("五", "5");
      text = text.replaceAll("六", "6");
      text = text.replaceAll("七", "7");
      text = text.replaceAll("八", "8");
      text = text.replaceAll("九", "9");
      text = text.replaceAll("两", "2");
      return text;
   }
}
