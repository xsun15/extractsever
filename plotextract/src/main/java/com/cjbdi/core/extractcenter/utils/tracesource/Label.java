package com.cjbdi.core.extractcenter.utils.tracesource;

import java.util.ArrayList;
import java.util.List;

public class Label {
   private String code;
   private String chiName;
   private String value;
   private String caseCause;
   private List<MatchModel> matchModelList = new ArrayList<>();

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

   public String getChiName() {
      return chiName;
   }

   public void setChiName(String chiName) {
      this.chiName = chiName;
   }

   public String getValue() {
      return value;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getCaseCause() {
      return caseCause;
   }

   public void setCaseCause(String caseCause) {
      this.caseCause = caseCause;
   }

   public void addMatchModel(MatchModel matchModel) {
      matchModelList.add(matchModel);
   }

   public List<MatchModel> getMatchModelList() {
      return matchModelList;
   }

   public void setMatchModelList(List<MatchModel> matchModelList) {
      this.matchModelList = matchModelList;
   }
}
