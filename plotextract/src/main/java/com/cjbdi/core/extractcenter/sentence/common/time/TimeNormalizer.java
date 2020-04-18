package com.cjbdi.core.extractcenter.sentence.common.time;

import com.cjbdi.core.extractcenter.sentence.common.time.DateUtil;
import com.cjbdi.core.extractcenter.sentence.common.time.StringPreHandlingModule;
import com.cjbdi.core.extractcenter.sentence.common.time.TimePoint;
import com.cjbdi.core.extractcenter.sentence.common.time.TimeUnit;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeNormalizer implements Serializable {

   private static final long serialVersionUID = 463541045644656392L;
   private static final Logger LOGGER = LoggerFactory.getLogger(TimeNormalizer.class);
   private String timeBase;
   private String oldTimeBase;
   private static Pattern patterns = null;
   private String target;
   private TimeUnit[] timeToken = new TimeUnit[0];
   private boolean isPreferFuture = true;


   public TimeNormalizer() {
      if(patterns == null) {
         try {
            InputStream e = this.getClass().getResourceAsStream("/TimeExp.m");
            ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new GZIPInputStream(e)));
            patterns = this.readModel(objectInputStream);
         } catch (Exception var3) {
            var3.printStackTrace();
            System.err.print("Read model error!");
         }
      }

   }

   public TimeNormalizer(String path) {
      if(patterns == null) {
         try {
            patterns = this.readModel(path);
         } catch (Exception var3) {
            var3.printStackTrace();
            System.err.print("Read model error!");
         }
      }

   }

   public TimeNormalizer(String path, boolean isPreferFuture) {
      this.isPreferFuture = isPreferFuture;
      if(patterns == null) {
         try {
            patterns = this.readModel(path);
            LOGGER.debug("loaded pattern:{}", patterns.pattern());
         } catch (Exception var4) {
            var4.printStackTrace();
            System.err.print("Read model error!");
         }
      }

   }

   public TimeUnit[] parse(String target, String timeBase) {
      this.target = target;
      this.timeBase = timeBase;
      this.oldTimeBase = timeBase;
      this.preHandling();
      this.timeToken = this.TimeEx(this.target, timeBase);
      return this.timeToken;
   }

   public TimeUnit[] parse(String target) {
      this.target = target;
      this.timeBase = (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")).format(Calendar.getInstance().getTime());
      this.oldTimeBase = this.timeBase;
      this.preHandling();
      this.timeToken = this.TimeEx(this.target, this.timeBase);
      return this.timeToken;
   }

   public String getTimeBase() {
      return this.timeBase;
   }

   public String getOldTimeBase() {
      return this.oldTimeBase;
   }

   public boolean isPreferFuture() {
      return this.isPreferFuture;
   }

   public void setPreferFuture(boolean isPreferFuture) {
      this.isPreferFuture = isPreferFuture;
   }

   public void setTimeBase(String s) {
      this.timeBase = s;
   }

   public void resetTimeBase() {
      this.timeBase = this.oldTimeBase;
   }

   public TimeUnit[] getTimeUnit() {
      return this.timeToken;
   }

   private void preHandling() {
      this.target = StringPreHandlingModule.delKeyword(this.target, "\\s+");
      this.target = StringPreHandlingModule.delKeyword(this.target, "[的]+");
      this.target = StringPreHandlingModule.numberTranslator(this.target);
   }

   private TimeUnit[] TimeEx(String tar, String timebase) {
      boolean startline = true;
      int endline = -1;
      String[] temp = new String[99];
      int rpointer = 0;
      TimeUnit[] Time_Result = null;
      Matcher match = patterns.matcher(tar);

      for(boolean startmark = true; match.find(); ++rpointer) {
         int var12 = match.start();
         if(endline == var12) {
            --rpointer;
            temp[rpointer] = temp[rpointer] + match.group();
         } else {
            if(!startmark) {
               --rpointer;
               ++rpointer;
            }

            startmark = false;
            temp[rpointer] = match.group();
         }

         endline = match.end();
      }

      if(rpointer > 0) {
         --rpointer;
         ++rpointer;
      }

      Time_Result = new TimeUnit[rpointer];
      TimePoint contextTp = new TimePoint();

      for(int j = 0; j < rpointer; ++j) {
         Time_Result[j] = new TimeUnit(temp[j], this, contextTp);
         contextTp = Time_Result[j]._tp;
      }

      Time_Result = filterTimeUnit(Time_Result);
      return Time_Result;
   }

   public static TimeUnit[] filterTimeUnit(TimeUnit[] timeUnit) {
      if(timeUnit != null && timeUnit.length >= 1) {
         ArrayList list = new ArrayList();
         TimeUnit[] newT = timeUnit;
         int var3 = timeUnit.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TimeUnit t = newT[var4];
            if(t.getTime().getTime() != -28800000L) {
               list.add(t);
            }
         }

         newT = new TimeUnit[list.size()];
         newT = (TimeUnit[])list.toArray(newT);
         return newT;
      } else {
         return timeUnit;
      }
   }

   private Pattern readModel(String file) throws Exception {
      ObjectInputStream in;
      if(!file.startsWith("jar:file") && !file.startsWith("file:")) {
         in = new ObjectInputStream(new BufferedInputStream(new GZIPInputStream(new FileInputStream(file))));
      } else {
         in = new ObjectInputStream(new BufferedInputStream(new GZIPInputStream((new URL(file)).openStream())));
      }

      return this.readModel(in);
   }

   private Pattern readModel(ObjectInputStream in) throws Exception {
      Pattern p = (Pattern)in.readObject();
      LOGGER.debug("model pattern:{}", p.pattern());
      return Pattern.compile(p.pattern());
   }

   public static void writeModel(Object p, String path) throws Exception {
      ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(path))));
      out.writeObject(p);
      out.close();
   }

   public static void main(String[] args) throws Exception {
      String path = TimeNormalizer.class.getResource("").getPath();
      String classPath = path.substring(0, path.indexOf("/edu/fudan"));
      LOGGER.debug(classPath + "/TimeExp1.zip");
      TimeNormalizer normalizer = new TimeNormalizer(classPath + "/TimeExp1.zip");
      normalizer.parse("本周日到下周日出差");
      TimeUnit[] unit = normalizer.getTimeUnit();
      LOGGER.debug(DateUtil.formatDateDefault(unit[0].getTime()));
      LOGGER.debug(DateUtil.formatDateDefault(unit[1].getTime()));
   }

}
