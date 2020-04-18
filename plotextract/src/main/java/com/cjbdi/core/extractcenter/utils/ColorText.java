package com.cjbdi.core.extractcenter.utils;

import com.cjbdi.core.extractcenter.utils.ColorTextConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class ColorText {

   public String run(ColorTextConfig colorTextConfig) {
      if(StringUtils.isNotEmpty(colorTextConfig.text)) {
         List colorConfigList = this.convert2List(colorTextConfig.effectText, colorTextConfig.effectTextColor);
         colorConfigList.addAll(this.convert2List(colorTextConfig.invalidText, colorTextConfig.invalidTextColor));
         return this.highLightText(colorTextConfig.text, colorConfigList);
      } else {
         return "";
      }
   }

   public String highLightText(String text, List colorMoneyList) {
      String highText = "";
      if(colorMoneyList != null && colorMoneyList.size() > 0) {
         colorMoneyList = this.order(colorMoneyList);
         int textList1 = 0;
         boolean start1 = false;
         int line1 = 0;

         for(Iterator var7 = colorMoneyList.iterator(); var7.hasNext(); textList1 = line1) {
            ColorText.ColorConfig colorConfig = (ColorText.ColorConfig)var7.next();
            String money = colorConfig.money;
            String color = colorConfig.color;
            int start2 = Integer.parseInt(money.split(",")[0]);
            line1 = Integer.parseInt(money.split(",")[1]);
            if(textList1 < start2 && start2 < text.length()) {
               highText = highText + "<span style=\"line-height:48px\">" + text.substring(textList1, start2).replaceAll("\n", "<br/>") + "</span>";
            }

            if(start2 < line1 && line1 < text.length()) {
               highText = highText + "<span style=\"color: " + color + ";line-height:48px;font-size:18px\">" + text.substring(start2, line1).replaceAll("\n", "<br/>") + "</span>";
            }
         }

         if(line1 < text.length()) {
            highText = highText + "<span style=\"line-height:48px\">" + text.substring(line1).replaceAll("\n", "<br/>") + "</span>";
         }
      } else if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));

         String line;
         for(Iterator start = textList.iterator(); start.hasNext(); highText = highText + "<span style=\"line-height:48px\">" + line + "</span><br/>") {
            line = (String)start.next();
         }
      }

      return highText;
   }

   public List order(List colorMoneyList) {
      if(colorMoneyList != null && colorMoneyList.size() > 0) {
         for(int i = 0; i < colorMoneyList.size() - 1; ++i) {
            for(int j = 0; j < colorMoneyList.size() - i - 1; ++j) {
               int startj = Integer.parseInt(((ColorText.ColorConfig)colorMoneyList.get(j)).money.split(",")[0]);
               int startj1 = Integer.parseInt(((ColorText.ColorConfig)colorMoneyList.get(j + 1)).money.split(",")[0]);
               if(startj > startj1) {
                  ColorText.ColorConfig colorMoney = (ColorText.ColorConfig)colorMoneyList.get(j);
                  colorMoneyList.set(j, colorMoneyList.get(j + 1));
                  colorMoneyList.set(j + 1, colorMoney);
               }
            }
         }
      }

      return colorMoneyList;
   }

   public List convert2List(String money, String color) {
      ArrayList colorMoneyList = new ArrayList();
      if(StringUtils.isNotEmpty(money)) {
         List moneyList = Arrays.asList(money.split(";"));
         if(moneyList != null && moneyList.size() > 0) {
            Iterator var5 = moneyList.iterator();

            while(var5.hasNext()) {
               String one = (String)var5.next();
               if(StringUtils.isNotEmpty(one)) {
                  ColorText.ColorConfig colorMoney = new ColorText.ColorConfig();
                  colorMoney.money = one;
                  colorMoney.color = color;
                  colorMoneyList.add(colorMoney);
               }
            }
         }
      }

      return colorMoneyList;
   }

   class ColorConfig {

      public String money;
      public String color;


   }
}
