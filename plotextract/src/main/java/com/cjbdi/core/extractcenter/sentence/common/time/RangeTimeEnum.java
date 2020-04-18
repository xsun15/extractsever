package com.cjbdi.core.extractcenter.sentence.common.time;


public enum RangeTimeEnum {

   day_break("day_break", 0, 3),
   early_morning("early_morning", 1, 8),
   morning("morning", 2, 10),
   noon("noon", 3, 12),
   afternoon("afternoon", 4, 15),
   night("night", 5, 18),
   lateNight("lateNight", 6, 20),
   midNight("midNight", 7, 23);
   private int hourTime = 0;
   // $FF: synthetic field
   private static final RangeTimeEnum[] $VALUES = new RangeTimeEnum[]{day_break, early_morning, morning, noon, afternoon, night, lateNight, midNight};


   private RangeTimeEnum(String var1, int var2, int hourTime) {
      this.setHourTime(hourTime);
   }

   public int getHourTime() {
      return this.hourTime;
   }

   public void setHourTime(int hourTime) {
      this.hourTime = hourTime;
   }

}
