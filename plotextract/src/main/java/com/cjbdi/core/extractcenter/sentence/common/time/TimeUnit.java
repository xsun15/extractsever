package com.cjbdi.core.extractcenter.sentence.common.time;

import com.cjbdi.core.extractcenter.sentence.common.time.RangeTimeEnum;
import com.cjbdi.core.extractcenter.sentence.common.time.TimeNormalizer;
import com.cjbdi.core.extractcenter.sentence.common.time.TimePoint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUnit {

   public String Time_Expression = null;
   public String Time_Norm = "";
   public int[] time_full;
   public int[] time_origin;
   private Date time;
   private Boolean isAllDayTime = Boolean.valueOf(true);
   private boolean isFirstTimeSolveContext = true;
   TimeNormalizer normalizer = null;
   public TimePoint _tp = new TimePoint();
   public TimePoint _tp_origin = new TimePoint();
   private static Map TUNIT_MAP = new HashMap();


   public TimeUnit(String exp_time, TimeNormalizer n) {
      this.Time_Expression = exp_time;
      this.normalizer = n;
      this.Time_Normalization();
   }

   public TimeUnit(String exp_time, TimeNormalizer n, TimePoint contextTp) {
      this.Time_Expression = exp_time;
      this.normalizer = n;
      this._tp_origin = contextTp;
      this.Time_Normalization();
   }

   public Date getTime() {
      return this.time;
   }

   public void norm_setyear() {
      String rule = "[0-9]{2}(?=年)";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[0] = Integer.parseInt(match.group());
         if(this._tp.tunit[0] >= 0 && this._tp.tunit[0] < 100) {
            if(this._tp.tunit[0] < 30) {
               this._tp.tunit[0] += 2000;
            } else {
               this._tp.tunit[0] += 1900;
            }
         }
      }

      rule = "[0-9]?[0-9]{3}(?=年)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[0] = Integer.parseInt(match.group());
      }

   }

   public void norm_setmonth() {
      String rule = "((10)|(11)|(12)|([1-9]))(?=月)";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[1] = Integer.parseInt(match.group());
         this.preferFuture(1);
      }

   }

   public void norm_setmonth_fuzzyday() {
      String rule = "((10)|(11)|(12)|([1-9]))(月|\\.|\\-)([0-3][0-9]|[1-9])";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         String matchStr = match.group();
         Pattern p = Pattern.compile("(月|\\.|\\-)");
         Matcher m = p.matcher(matchStr);
         if(m.find()) {
            int splitIndex = m.start();
            String month = matchStr.substring(0, splitIndex);
            String date = matchStr.substring(splitIndex + 1);
            this._tp.tunit[1] = Integer.parseInt(month);
            this._tp.tunit[2] = Integer.parseInt(date);
            this.preferFuture(1);
         }
      }

   }

   public void norm_setday() {
      String rule = "((?<!\\d))([0-3][0-9]|[1-9])(?=(日|号))";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[2] = Integer.parseInt(match.group());
         this.preferFuture(2);
      }

   }

   public void norm_sethour() {
      String rule = "(?<!(周|星期))([0-2]?[0-9])(?=(点|时))";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[3] = Integer.parseInt(match.group());
         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "凌晨";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.day_break.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "早上|早晨|早间|晨间|今早|明早";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.early_morning.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "上午";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.morning.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "(中午)|(午间)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] >= 0 && this._tp.tunit[3] <= 10) {
            this._tp.tunit[3] += 12;
         }

         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.noon.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "(下午)|(午后)|(pm)|(PM)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] >= 0 && this._tp.tunit[3] <= 11) {
            this._tp.tunit[3] += 12;
         }

         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.afternoon.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "晚上|夜间|夜里|今晚|明晚";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] >= 1 && this._tp.tunit[3] <= 11) {
            this._tp.tunit[3] += 12;
         } else if(this._tp.tunit[3] == 12) {
            this._tp.tunit[3] = 0;
         } else if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.night.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

   }

   public void norm_setminute() {
      String rule = "([0-5]?[0-9](?=分(?!钟)))|((?<=((?<!小)[点时]))[0-5]?[0-9](?!刻))";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find() && !match.group().equals("")) {
         this._tp.tunit[4] = Integer.parseInt(match.group());
         this.preferFuture(4);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "(?<=[点时])[1一]刻(?!钟)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[4] = 15;
         this.preferFuture(4);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "(?<=[点时])半";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[4] = 30;
         this.preferFuture(4);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "(?<=[点时])[3三]刻(?!钟)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[4] = 45;
         this.preferFuture(4);
         this.isAllDayTime = Boolean.valueOf(false);
      }

   }

   public void norm_setsecond() {
      String rule = "([0-5]?[0-9](?=秒))|((?<=分)[0-5]?[0-9])";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         this._tp.tunit[5] = Integer.parseInt(match.group());
         this.isAllDayTime = Boolean.valueOf(false);
      }

   }

   public void norm_setTotal() {
      String rule = "(?<!(周|星期))([0-2]?[0-9]):[0-5]?[0-9]:[0-5]?[0-9]";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      String[] tmp_parser;
      String tmp_target;
      if(match.find()) {
         tmp_parser = new String[3];
         tmp_target = match.group();
         tmp_parser = tmp_target.split(":");
         this._tp.tunit[3] = Integer.parseInt(tmp_parser[0]);
         this._tp.tunit[4] = Integer.parseInt(tmp_parser[1]);
         this._tp.tunit[5] = Integer.parseInt(tmp_parser[2]);
         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      } else {
         rule = "(?<!(周|星期))([0-2]?[0-9]):[0-5]?[0-9]";
         pattern = Pattern.compile(rule);
         match = pattern.matcher(this.Time_Expression);
         if(match.find()) {
            tmp_parser = new String[2];
            tmp_target = match.group();
            tmp_parser = tmp_target.split(":");
            this._tp.tunit[3] = Integer.parseInt(tmp_parser[0]);
            this._tp.tunit[4] = Integer.parseInt(tmp_parser[1]);
            this.preferFuture(3);
            this.isAllDayTime = Boolean.valueOf(false);
         }
      }

      rule = "(中午)|(午间)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] >= 0 && this._tp.tunit[3] <= 10) {
            this._tp.tunit[3] += 12;
         }

         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.noon.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "(下午)|(午后)|(pm)|(PM)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] >= 0 && this._tp.tunit[3] <= 11) {
            this._tp.tunit[3] += 12;
         }

         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.afternoon.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "晚";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         if(this._tp.tunit[3] >= 1 && this._tp.tunit[3] <= 11) {
            this._tp.tunit[3] += 12;
         } else if(this._tp.tunit[3] == 12) {
            this._tp.tunit[3] = 0;
         }

         if(this._tp.tunit[3] == -1) {
            this._tp.tunit[3] = RangeTimeEnum.night.getHourTime();
         }

         this.preferFuture(3);
         this.isAllDayTime = Boolean.valueOf(false);
      }

      rule = "[0-9]?[0-9]?[0-9]{2}-((10)|(11)|(12)|([1-9]))-((?<!\\d))([0-3][0-9]|[1-9])";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         tmp_parser = new String[3];
         tmp_target = match.group();
         tmp_parser = tmp_target.split("-");
         this._tp.tunit[0] = Integer.parseInt(tmp_parser[0]);
         this._tp.tunit[1] = Integer.parseInt(tmp_parser[1]);
         this._tp.tunit[2] = Integer.parseInt(tmp_parser[2]);
      }

      rule = "((10)|(11)|(12)|([1-9]))/((?<!\\d))([0-3][0-9]|[1-9])/[0-9]?[0-9]?[0-9]{2}";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         tmp_parser = new String[3];
         tmp_target = match.group();
         tmp_parser = tmp_target.split("/");
         this._tp.tunit[1] = Integer.parseInt(tmp_parser[0]);
         this._tp.tunit[2] = Integer.parseInt(tmp_parser[1]);
         this._tp.tunit[0] = Integer.parseInt(tmp_parser[2]);
      }

      rule = "[0-9]?[0-9]?[0-9]{2}\\.((10)|(11)|(12)|([1-9]))\\.((?<!\\d))([0-3][0-9]|[1-9])";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         tmp_parser = new String[3];
         tmp_target = match.group();
         tmp_parser = tmp_target.split("\\.");
         this._tp.tunit[0] = Integer.parseInt(tmp_parser[0]);
         this._tp.tunit[1] = Integer.parseInt(tmp_parser[1]);
         this._tp.tunit[2] = Integer.parseInt(tmp_parser[2]);
      }

   }

   public void norm_setBaseRelated() {
      String[] time_grid = new String[6];
      time_grid = this.normalizer.getTimeBase().split("-");
      int[] ini = new int[6];

      for(int calendar = 0; calendar < 6; ++calendar) {
         ini[calendar] = Integer.parseInt(time_grid[calendar]);
      }

      Calendar var10 = Calendar.getInstance();
      var10.setFirstDayOfWeek(2);
      var10.set(ini[0], ini[1] - 1, ini[2], ini[3], ini[4], ini[5]);
      var10.getTime();
      boolean[] flag = new boolean[]{false, false, false};
      String rule = "\\d+(?=天[以之]?前)";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      int s;
      if(match.find()) {
         flag[2] = true;
         s = Integer.parseInt(match.group());
         var10.add(5, -s);
      }

      rule = "\\d+(?=天[以之]?后)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         s = Integer.parseInt(match.group());
         var10.add(5, s);
      }

      rule = "\\d+(?=(个)?月[以之]?前)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[1] = true;
         s = Integer.parseInt(match.group());
         var10.add(2, -s);
      }

      rule = "\\d+(?=(个)?月[以之]?后)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[1] = true;
         s = Integer.parseInt(match.group());
         var10.add(2, s);
      }

      rule = "\\d+(?=年[以之]?前)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[0] = true;
         s = Integer.parseInt(match.group());
         var10.add(1, -s);
      }

      rule = "\\d+(?=年[以之]?后)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[0] = true;
         s = Integer.parseInt(match.group());
         var10.add(1, s);
      }

      String var11 = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(var10.getTime());
      String[] time_fin = var11.split("-");
      if(flag[0] || flag[1] || flag[2]) {
         this._tp.tunit[0] = Integer.parseInt(time_fin[0]);
      }

      if(flag[1] || flag[2]) {
         this._tp.tunit[1] = Integer.parseInt(time_fin[1]);
      }

      if(flag[2]) {
         this._tp.tunit[2] = Integer.parseInt(time_fin[2]);
      }

   }

   public void norm_setCurRelated() {
      String[] time_grid = new String[6];
      time_grid = this.normalizer.getOldTimeBase().split("-");
      int[] ini = new int[6];

      for(int calendar = 0; calendar < 6; ++calendar) {
         ini[calendar] = Integer.parseInt(time_grid[calendar]);
      }

      Calendar var15 = Calendar.getInstance();
      var15.setFirstDayOfWeek(2);
      var15.set(ini[0], ini[1] - 1, ini[2], ini[3], ini[4], ini[5]);
      var15.getTime();
      boolean[] flag = new boolean[]{false, false, false};
      String rule = "前年";
      Pattern pattern = Pattern.compile(rule);
      Matcher match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[0] = true;
         var15.add(1, -2);
      }

      rule = "去年";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[0] = true;
         var15.add(1, -1);
      }

      rule = "今年";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[0] = true;
         var15.add(1, 0);
      }

      rule = "明年";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[0] = true;
         var15.add(1, 1);
      }

      rule = "后年";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[0] = true;
         var15.add(1, 2);
      }

      rule = "上(个)?月";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[1] = true;
         var15.add(2, -1);
      }

      rule = "(本|这个)月";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[1] = true;
         var15.add(2, 0);
      }

      rule = "下(个)?月";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[1] = true;
         var15.add(2, 1);
      }

      rule = "大前天";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         var15.add(5, -3);
      }

      rule = "(?<!大)前天";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         var15.add(5, -2);
      }

      rule = "昨";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         var15.add(5, -1);
      }

      rule = "今(?!年)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         var15.add(5, 0);
      }

      rule = "明(?!年)";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         var15.add(5, 1);
      }

      rule = "(?<!大)后天";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         var15.add(5, 2);
      }

      rule = "大后天";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;
         var15.add(5, 3);
      }

      rule = "(?<=(上上(周|星期)))[1-7]?";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      int s;
      if(match.find()) {
         flag[2] = true;

         try {
            s = Integer.parseInt(match.group());
         } catch (NumberFormatException var14) {
            s = 1;
         }

         if(s == 7) {
            s = 1;
         } else {
            ++s;
         }

         var15.add(4, -2);
         var15.set(7, s);
      }

      rule = "(?<=((?<!上)上(周|星期)))[1-7]?";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;

         try {
            s = Integer.parseInt(match.group());
         } catch (NumberFormatException var13) {
            s = 1;
         }

         if(s == 7) {
            s = 1;
         } else {
            ++s;
         }

         var15.add(4, -1);
         var15.set(7, s);
      }

      rule = "(?<=((?<!下)下(周|星期)))[1-7]?";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;

         try {
            s = Integer.parseInt(match.group());
         } catch (NumberFormatException var12) {
            s = 1;
         }

         if(s == 7) {
            s = 1;
         } else {
            ++s;
         }

         var15.add(4, 1);
         var15.set(7, s);
      }

      rule = "(?<=(下下(周|星期)))[1-7]?";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;

         try {
            s = Integer.parseInt(match.group());
         } catch (NumberFormatException var11) {
            s = 1;
         }

         if(s == 7) {
            s = 1;
         } else {
            ++s;
         }

         var15.add(4, 2);
         var15.set(7, s);
      }

      rule = "(?<=((?<!(上|下))(周|星期)))[1-7]?";
      pattern = Pattern.compile(rule);
      match = pattern.matcher(this.Time_Expression);
      if(match.find()) {
         flag[2] = true;

         try {
            s = Integer.parseInt(match.group());
         } catch (NumberFormatException var10) {
            s = 1;
         }

         if(s == 7) {
            s = 1;
         } else {
            ++s;
         }

         var15.add(4, 0);
         var15.set(7, s);
         this.preferFutureWeek(s, var15);
      }

      String var16 = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(var15.getTime());
      String[] time_fin = var16.split("-");
      if(flag[0] || flag[1] || flag[2]) {
         this._tp.tunit[0] = Integer.parseInt(time_fin[0]);
      }

      if(flag[1] || flag[2]) {
         this._tp.tunit[1] = Integer.parseInt(time_fin[1]);
      }

      if(flag[2]) {
         this._tp.tunit[2] = Integer.parseInt(time_fin[2]);
      }

   }

   public void modifyTimeBase() {
      String[] time_grid = new String[6];
      time_grid = this.normalizer.getTimeBase().split("-");
      String s = "";
      if(this._tp.tunit[0] != -1) {
         s = s + Integer.toString(this._tp.tunit[0]);
      } else {
         s = s + time_grid[0];
      }

      for(int i = 1; i < 6; ++i) {
         s = s + "-";
         if(this._tp.tunit[i] != -1) {
            s = s + Integer.toString(this._tp.tunit[i]);
         } else {
            s = s + time_grid[i];
         }
      }

      this.normalizer.setTimeBase(s);
   }

   public void Time_Normalization() {
      this.norm_setyear();
      this.norm_setmonth();
      this.norm_setday();
      this.norm_setmonth_fuzzyday();
      this.norm_setBaseRelated();
      this.norm_setCurRelated();
      this.norm_sethour();
      this.norm_setminute();
      this.norm_setsecond();
      this.norm_setTotal();
      this.modifyTimeBase();
      this._tp_origin.tunit = (int[])this._tp.tunit.clone();
      String[] time_grid = new String[6];
      time_grid = this.normalizer.getTimeBase().split("-");

      int tunitpointer;
      for(tunitpointer = 5; tunitpointer >= 0 && this._tp.tunit[tunitpointer] < 0; --tunitpointer) {
         ;
      }

      for(int _result_tmp = 0; _result_tmp < tunitpointer; ++_result_tmp) {
         if(this._tp.tunit[_result_tmp] < 0) {
            this._tp.tunit[_result_tmp] = Integer.parseInt(time_grid[_result_tmp]);
         }
      }

      String[] var5 = new String[6];
      var5[0] = String.valueOf(this._tp.tunit[0]);
      if(this._tp.tunit[0] >= 10 && this._tp.tunit[0] < 100) {
         var5[0] = "19" + String.valueOf(this._tp.tunit[0]);
      }

      if(this._tp.tunit[0] > 0 && this._tp.tunit[0] < 10) {
         var5[0] = "200" + String.valueOf(this._tp.tunit[0]);
      }

      for(int cale = 1; cale < 6; ++cale) {
         var5[cale] = String.valueOf(this._tp.tunit[cale]);
      }

      Calendar var6 = Calendar.getInstance();
      var6.clear();
      if(Integer.parseInt(var5[0]) != -1) {
         this.Time_Norm = this.Time_Norm + var5[0] + "年";
         var6.set(1, Integer.valueOf(var5[0]).intValue());
         if(Integer.parseInt(var5[1]) != -1) {
            this.Time_Norm = this.Time_Norm + var5[1] + "月";
            var6.set(2, Integer.valueOf(var5[1]).intValue() - 1);
            if(Integer.parseInt(var5[2]) != -1) {
               this.Time_Norm = this.Time_Norm + var5[2] + "日";
               var6.set(5, Integer.valueOf(var5[2]).intValue());
               if(Integer.parseInt(var5[3]) != -1) {
                  this.Time_Norm = this.Time_Norm + var5[3] + "时";
                  var6.set(11, Integer.valueOf(var5[3]).intValue());
                  if(Integer.parseInt(var5[4]) != -1) {
                     this.Time_Norm = this.Time_Norm + var5[4] + "分";
                     var6.set(12, Integer.valueOf(var5[4]).intValue());
                     if(Integer.parseInt(var5[5]) != -1) {
                        this.Time_Norm = this.Time_Norm + var5[5] + "秒";
                        var6.set(13, Integer.valueOf(var5[5]).intValue());
                     }
                  }
               }
            }
         }
      }

      this.time = var6.getTime();
      this.time_full = (int[])this._tp.tunit.clone();
   }

   public Boolean getIsAllDayTime() {
      return this.isAllDayTime;
   }

   public void setIsAllDayTime(Boolean isAllDayTime) {
      this.isAllDayTime = isAllDayTime;
   }

   public String toString() {
      return this.Time_Expression + " ---> " + this.Time_Norm;
   }

   private void preferFuture(int checkTimeIndex) {
      int c;
      for(c = 0; c < checkTimeIndex; ++c) {
         if(this._tp.tunit[c] != -1) {
            return;
         }
      }

      this.checkContextTime(checkTimeIndex);

      for(c = 0; c < checkTimeIndex; ++c) {
         if(this._tp.tunit[c] != -1) {
            return;
         }
      }

      if(this.normalizer.isPreferFuture()) {
         Calendar var6 = Calendar.getInstance();
         if(this.normalizer.getTimeBase() != null) {
            String[] curTime = this.normalizer.getTimeBase().split("-");
            var6.set(Integer.valueOf(curTime[0]).intValue(), Integer.valueOf(curTime[1]).intValue() - 1, Integer.valueOf(curTime[2]).intValue(), Integer.valueOf(curTime[3]).intValue(), Integer.valueOf(curTime[4]).intValue(), Integer.valueOf(curTime[5]).intValue());
         }

         int var7 = var6.get(((Integer)TUNIT_MAP.get(Integer.valueOf(checkTimeIndex))).intValue());
         if(var7 >= this._tp.tunit[checkTimeIndex]) {
            int addTimeUnit = ((Integer)TUNIT_MAP.get(Integer.valueOf(checkTimeIndex - 1))).intValue();
            var6.add(addTimeUnit, 1);

            for(int i = 0; i < checkTimeIndex; ++i) {
               this._tp.tunit[i] = var6.get(((Integer)TUNIT_MAP.get(Integer.valueOf(i))).intValue());
               if(((Integer)TUNIT_MAP.get(Integer.valueOf(i))).intValue() == 2) {
                  ++this._tp.tunit[i];
               }
            }

         }
      }
   }

   private void preferFutureWeek(int weekday, Calendar c) {
      if(this.normalizer.isPreferFuture()) {
         byte checkTimeIndex = 2;

         for(int curC = 0; curC < checkTimeIndex; ++curC) {
            if(this._tp.tunit[curC] != -1) {
               return;
            }
         }

         Calendar var6 = Calendar.getInstance();
         if(this.normalizer.getTimeBase() != null) {
            String[] curWeekday = this.normalizer.getTimeBase().split("-");
            var6.set(Integer.valueOf(curWeekday[0]).intValue(), Integer.valueOf(curWeekday[1]).intValue() - 1, Integer.valueOf(curWeekday[2]).intValue(), Integer.valueOf(curWeekday[3]).intValue(), Integer.valueOf(curWeekday[4]).intValue(), Integer.valueOf(curWeekday[5]).intValue());
         }

         int var7 = var6.get(7);
         if(weekday == 1) {
            weekday = 7;
         }

         if(var7 >= weekday) {
            c.add(3, 1);
         }
      }
   }

   private void checkContextTime(int checkTimeIndex) {
      for(int i = 0; i < checkTimeIndex; ++i) {
         if(this._tp.tunit[i] == -1 && this._tp_origin.tunit[i] != -1) {
            this._tp.tunit[i] = this._tp_origin.tunit[i];
         }
      }

      if(this.isFirstTimeSolveContext && checkTimeIndex == 3 && this._tp_origin.tunit[checkTimeIndex] >= 12 && this._tp.tunit[checkTimeIndex] < 12) {
         this._tp.tunit[checkTimeIndex] += 12;
      }

      this.isFirstTimeSolveContext = false;
   }

   static {
      TUNIT_MAP.put(Integer.valueOf(0), Integer.valueOf(1));
      TUNIT_MAP.put(Integer.valueOf(1), Integer.valueOf(2));
      TUNIT_MAP.put(Integer.valueOf(2), Integer.valueOf(5));
      TUNIT_MAP.put(Integer.valueOf(3), Integer.valueOf(11));
      TUNIT_MAP.put(Integer.valueOf(4), Integer.valueOf(12));
      TUNIT_MAP.put(Integer.valueOf(5), Integer.valueOf(13));
   }
}
