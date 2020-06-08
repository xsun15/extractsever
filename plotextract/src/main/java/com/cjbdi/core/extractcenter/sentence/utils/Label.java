package com.cjbdi.core.extractcenter.sentence.utils;


import com.cjbdi.core.servercenter.utils.TraceSourceModel;

import java.util.Map;

public class Label {

   long flag;
   String value;
   String text;
   String paraText;
   String paraName;
   String casecause;
   String usedRegx;
   String prioritylevel;
   String chiname;

   // 提取溯源
   Map<String, TraceSourceModel> traceSourceMap;


   public long getFlag() {
      return this.flag;
   }

   public void setFlag(long flag) {
      this.flag = flag;
   }

   public String getValue() {
      return this.value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getText() {
      return this.text;
   }

   public void setText(String text) {
      this.text = text;
   }

   public String getCasecause() {
      return this.casecause;
   }

   public void setCasecause(String casecause) {
      this.casecause = casecause;
   }

   public String getUsedRegx() {
      return this.usedRegx;
   }

   public void setUsedRegx(String usedRegx) {
      this.usedRegx = usedRegx;
   }

   public String getPrioritylevel() {
      return this.prioritylevel;
   }

   public void setPrioritylevel(String prioritylevel) {
      this.prioritylevel = prioritylevel;
   }

   public String getChiname() {
      return this.chiname;
   }

   public void setChiname(String chiname) {
      this.chiname = chiname;
   }

   public boolean equal(Label label) {
      return label != null?this.value.equals(label.value):false;
   }

   public String getParaText() {
      return this.paraText;
   }

   public void setParaText(String paraText) {
      this.paraText = paraText;
   }

   public String getParaName() {
      return this.paraName;
   }

   public void setParaName(String paraName) {
      this.paraName = paraName;
   }

   public Map<String, TraceSourceModel> getTraceSourceMap() {
      return traceSourceMap;
   }

   public void setTraceSourceMap(Map<String, TraceSourceModel> traceSourceMap) {
      this.traceSourceMap = traceSourceMap;
   }
}
