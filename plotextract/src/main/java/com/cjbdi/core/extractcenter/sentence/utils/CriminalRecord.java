package com.cjbdi.core.extractcenter.sentence.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CriminalRecord {

   private LocalDate time;
   private LocalDate releaseTime;
   private String cause;
   private String text;
   private int startPos;
   private int endPos;


   public Optional getTime() {
      return Optional.ofNullable(this.time);
   }

   public void setTime(LocalDate time) {
      this.time = time;
   }

   public Optional getReleaseTime() {
      return Optional.ofNullable(this.releaseTime);
   }

   public void setReleaseTime(LocalDate releaseTime) {
      this.releaseTime = releaseTime;
   }

   public String getCause() {
      return this.cause;
   }

   public void setCause(String cause) {
      this.cause = cause;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public int getStartPos() {
      return this.startPos;
   }

   public void setStartPos(int startPos) {
      this.startPos = startPos;
   }

   public int getEndPos() {
      return this.endPos;
   }

   public void setEndPos(int endPos) {
      this.endPos = endPos;
   }

   public Map toMap() {
      HashMap recordMap = new HashMap(4);
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String timeStr = this.getTime().isPresent()?dtf.format((TemporalAccessor)this.getTime().get()):"";
      String releasetimeStr = this.getReleaseTime().isPresent()?dtf.format((TemporalAccessor)this.getReleaseTime().get()):"";
      recordMap.put("time", timeStr);
      recordMap.put("releaseTime", releasetimeStr);
      recordMap.put("casecause", this.cause);
      recordMap.put("text", this.text);
      return recordMap;
   }
}
