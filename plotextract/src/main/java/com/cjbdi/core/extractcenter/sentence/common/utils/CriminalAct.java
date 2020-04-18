package com.cjbdi.core.extractcenter.sentence.common.utils;

import java.time.LocalDate;

public class CriminalAct {

   private LocalDate localDate;
   private String location;
   private String action;


   public LocalDate getLocalDate() {
      return this.localDate;
   }

   public void setLocalDate(LocalDate localDate) {
      this.localDate = localDate;
   }

   public String getLocation() {
      return this.location;
   }

   public void setLocation(String location) {
      this.location = location;
   }

   public String getAction() {
      return this.action;
   }

   public void setAction(String action) {
      this.action = action;
   }
}
