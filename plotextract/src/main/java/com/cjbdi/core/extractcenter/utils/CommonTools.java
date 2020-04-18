package com.cjbdi.core.extractcenter.utils;

import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.utils.IsDigit;
import com.cjbdi.core.extractcenter.utils.ZhNumber2NumberUsedForYearMonthDay;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class CommonTools {

   public static MoneyConfig max(List moneyConfigs) {
      if(moneyConfigs != null && moneyConfigs.size() > 0) {
         MoneyConfig maxval = (MoneyConfig)moneyConfigs.get(0);
         Iterator var2 = moneyConfigs.iterator();

         while(var2.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var2.next();
            if(moneyConfig.value >= maxval.value) {
               maxval = moneyConfig;
            }
         }

         return maxval;
      } else {
         return null;
      }
   }

   public static String convertYear2Month(Map map) {
      byte sum = 0;
      int sum1 = sum + convert("year", "年", 12, map);
      sum1 += convert("month", "月", 1, map);
      sum1 += convert("亿", "亿", 100000000, map);
      sum1 += convert("千万", "千万", 10000000, map);
      sum1 += convert("百万", "百万", 1000000, map);
      sum1 += convert("十万", "十万", 100000, map);
      sum1 += convert("万", "万", 10000, map);
      sum1 += convert("千", "千", 1000, map);
      sum1 += convert("百", "百", 100, map);
      sum1 += convert("十", "十", 10, map);
      sum1 += convert("个", "个", 1, map);
      return String.valueOf(sum1);
   }

   public static int convert(String keyword, String replace, int ratio, Map map) {
      int sum = 0;
      if(map.containsKey(keyword) && StringUtils.isNotEmpty((String)map.get(keyword))) {
         String value = ((String)map.get(keyword)).replaceAll(replace, "");
         if(StringUtils.isNotEmpty(value)) {
            if(!IsDigit.isInteger(value)) {
               sum += ZhNumber2NumberUsedForYearMonthDay.chineseNumToArabicNum(value.replaceAll("两", "二")) * ratio;
            } else {
               sum += Integer.parseInt(value) * ratio;
            }
         }
      }

      return sum;
   }
}
