package com.cjbdi.core.extractcenter.sentence.common.time;

import com.cjbdi.core.extractcenter.sentence.common.time.CommonDateUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil extends CommonDateUtil {

   private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
   public static final String FORMAT_CALENDAR_DATE = "yyyy年MM月dd日E";
   public static final String FORMAT_CALENDAR_TIME = "HH:mm";
   private static final List TIMEUNITS = new ArrayList();


   public static boolean isToday(Date date) {
      return isTheDay(date, new Date());
   }

   public static boolean isTheDay(Date date, Date day) {
      return date.getTime() >= dayBegin(day).getTime() && date.getTime() <= dayEnd(day).getTime();
   }

   public static Date roundMin(Date date, int round) {
      if(round > 60 || round < 0) {
         round = 0;
      }

      Calendar c = Calendar.getInstance();
      c.setTime(date);
      int min = c.get(12);
      if(min % round >= round / 2) {
         min = round * (min / (round + 1));
      } else {
         min = round * (min / round);
      }

      c.set(12, min);
      c.set(13, 0);
      return c.getTime();
   }

   public static Date getSpecificHourInTheDay(Date date, int hourIn24) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.set(11, hourIn24);
      c.set(12, 0);
      c.set(13, 0);
      c.set(14, 0);
      return c.getTime();
   }

   public static Date getFirstDayOfWeek(Date date) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      int day_of_week = c.get(7) - 1;
      if(day_of_week == 0) {
         day_of_week = 7;
      }

      c.add(5, -day_of_week + 1);
      return c.getTime();
   }

   public static Date getRelativeTime(Date date, int calUnit, int relative) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.add(calUnit, relative);
      return c.getTime();
   }

   public static Date dayBegin(Date date) {
      return getSpecificHourInTheDay(date, 0);
   }

   public static Date dayEnd(Date date) {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.set(11, 23);
      c.set(12, 59);
      c.set(13, 59);
      c.set(14, 999);
      return c.getTime();
   }

   public static String formatDateDefault(Date date) {
      return formatDate(date, "yyyy-MM-dd HH:mm:ss");
   }

   public static boolean checkDateFormatAndValite(String strDateTime, String format) {
      if(strDateTime != null && strDateTime.length() != 0) {
         SimpleDateFormat sdf = new SimpleDateFormat(format);

         try {
            Date e = sdf.parse(strDateTime);
            String str = sdf.format(e);
            LOGGER.debug("func<checkDateFormatAndValite> strDateTime<" + strDateTime + "> format<" + format + "> str<" + str + ">");
            return str.equals(strDateTime);
         } catch (Exception var5) {
            var5.printStackTrace();
            return false;
         }
      } else {
         return false;
      }
   }

   static {
      TIMEUNITS.add(Integer.valueOf(1));
      TIMEUNITS.add(Integer.valueOf(2));
      TIMEUNITS.add(Integer.valueOf(5));
      TIMEUNITS.add(Integer.valueOf(10));
      TIMEUNITS.add(Integer.valueOf(12));
      TIMEUNITS.add(Integer.valueOf(13));
   }
}
