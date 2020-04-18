package com.cjbdi.core.extractcenter.sentence.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CriminalAct {

   private LocalDateTime time;
   private String defendant;
   private List victims;
   private String location;
   private String conclusionText;
   private String actText;
   private String actTextss;
   private Map actTexts = new HashMap();


   public Optional getTime() {
      return Optional.ofNullable(this.time);
   }

   public void setTime(LocalDateTime time) {
      this.time = time;
   }

   public String getDefendant() {
      return this.defendant;
   }

   public void setDefendant(String defendant) {
      this.defendant = defendant;
   }

   public List getVictims() {
      return this.victims;
   }

   public void setVictims(List victims) {
      this.victims = victims;
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public List getActTexts() {
      return new ArrayList(this.actTexts.values());
   }

   public void addActText(String actText) {
      this.actTexts.put(actText, actText);
   }

   public String getActTextss() {
      StringBuilder sb = new StringBuilder();
      Iterator iterator = this.actTexts.values().iterator();

      while(iterator.hasNext()) {
         sb.append((String)iterator.next());
      }

      return sb.toString();
   }

   public void addActTextss(String actTextss) {
      this.actTexts.put(this.actText, this.actText);
   }

   public String getConclusionText() {
      return this.conclusionText;
   }

   public void setConclusionText(String conclusionText) {
      this.conclusionText = conclusionText;
   }

   public String getActText() {
      return this.actText;
   }

   public void setActText(String actText) {
      this.actText = actText;
   }
}
