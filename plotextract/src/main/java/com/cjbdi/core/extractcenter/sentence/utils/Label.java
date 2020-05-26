package com.cjbdi.core.extractcenter.sentence.utils;


import java.util.ArrayList;
import java.util.List;

public class Label {

   long flag;
   List<Integer> startpos = new ArrayList<>();
   List<String> rawText = new ArrayList<>();
   List<String> paras = new ArrayList<>();
   String value;
   String text;
   String paraText;
   String paraName;
   String casecause;
   String usedRegx;
   String prioritylevel;
   String chiname;


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

   public List<Integer> getStartpos() {
      return startpos;
   }

   public void setStartpos(List<Integer> startpos) {
      this.startpos = startpos;
   }

   public List<String> getRawText() {
      return rawText;
   }

   public void setRawText(List<String> rawText) {
      this.rawText = rawText;
   }

   public List<String> getParas() {
      return paras;
   }

   public void setParas(List<String> paras) {
      this.paras = paras;
   }
}
