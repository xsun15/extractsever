package com.cjbdi.core.extractcenter.utils.caseportrait;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.extractcenter.basicinfo.CriminalRecord;

import java.util.*;

public class DefendantModel {

   private String name;
   private Set<String> casecauseSet;
   private Set<String> defendantNameSet;
   private Map<String, CasecauseModel> caseCauseModelMap;
   private Optional gender;
   private Optional idCard;
   private Optional birthday;
   private Optional age = Optional.empty();
   private Optional crimeDate;
   private Optional arrestDate;
   private Optional prosecuteDate;
   private List<CriminalRecord> criminalRecordList;
   private Map criminalActMap;
   private Map caseCauseFeatureMap;
   private Map shareFeatureMap;
   private Map courtDecisionMap;
   private Set victimNameSet;
   private Set witnessNameSet;


   public DefendantModel(JSONObject jsonObject) {
      if(!jsonObject.isEmpty()) {
         this.name = jsonObject.getString("name");
         this.casecauseSet = new HashSet((Collection)jsonObject.getObject("casecauseSet", List.class));
         this.defendantNameSet = new HashSet((Collection)jsonObject.getObject("nameSet", List.class));
         this.caseCauseModelMap = new HashMap();
         JSONObject caseCauseModelMapJson = jsonObject.getJSONObject("casecauseModelMap");
         Iterator var3 = caseCauseModelMapJson.keySet().iterator();

         while(var3.hasNext()) {
            String casecause = (String)var3.next();
            this.caseCauseModelMap.put(casecause, caseCauseModelMapJson.getObject(casecause, CasecauseModel.class));
         }
         this.caseCauseModelMap = new HashMap();
         this.shareFeatureMap = new HashMap();
         this.courtDecisionMap = new HashMap();
         this.victimNameSet = new HashSet();
         this.witnessNameSet = new HashSet();
      }
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Set getCasecauseSet() {
      return this.casecauseSet;
   }

   public void setCasecauseSet(Set<String> casecauseSet) {
      this.casecauseSet = casecauseSet;
   }

   public Map<String, CasecauseModel> getCaseCauseModelMap() {
      return caseCauseModelMap;
   }

   public void setCaseCauseModelMap(Map<String, CasecauseModel> caseCauseModelMap) {
      this.caseCauseModelMap = caseCauseModelMap;
   }

   public Optional getIdCard() {
      return idCard;
   }

   public void setIdCard(Optional idCard) {
      this.idCard = idCard;
   }

   public Map getCaseCauseFeatureMap() {
      return caseCauseFeatureMap;
   }

   public void setCaseCauseFeatureMap(Map caseCauseFeatureMap) {
      this.caseCauseFeatureMap = caseCauseFeatureMap;
   }

   public Set getDefendantNameSet() {
      return this.defendantNameSet;
   }

   public void setDefendantNameSet(Set defendantNameSet) {
      this.defendantNameSet = defendantNameSet;
   }

   public Optional getGender() {
      return this.gender;
   }

   public void setGender(Optional gender) {
      this.gender = gender;
   }

   public Optional getBirthday() {
      return this.birthday;
   }

   public void setBirthday(Optional birthday) {
      this.birthday = birthday;
   }

   public Optional getAge() {
      return this.age;
   }

   public void setAge(Optional age) {
      this.age = age;
   }

   public Optional getCrimeDate() {
      return this.crimeDate;
   }

   public void setCrimeDate(Optional crimeDate) {
      this.crimeDate = crimeDate;
   }

   public Optional getArrestDate() {
      return this.arrestDate;
   }

   public void setArrestDate(Optional arrestDate) {
      this.arrestDate = arrestDate;
   }

   public Optional getProsecuteDate() {
      return this.prosecuteDate;
   }

   public void setProsecuteDate(Optional prosecuteDate) {
      this.prosecuteDate = prosecuteDate;
   }

   public List getCriminalRecordList() {
      return this.criminalRecordList;
   }

   public void setCriminalRecordList(List criminalRecordList) {
      this.criminalRecordList = criminalRecordList;
   }

   public Map getShareFeatureMap() {
      return this.shareFeatureMap;
   }

   public void setShareFeatureMap(Map shareFeatureMap) {
      this.shareFeatureMap = shareFeatureMap;
   }

   public void setShareFeatureMap(String casecause, List labelList) {
      this.shareFeatureMap.put(casecause, labelList);
   }

   public Map getCourtDecisionMap() {
      return this.courtDecisionMap;
   }

   public void setCourtDecisionMap(Map courtDecisionMap) {
      this.courtDecisionMap = courtDecisionMap;
   }

   public void setCourtDecisionMap(String casecause, List labelList) {
      this.courtDecisionMap.put(casecause, labelList);
   }

   public Map getCriminalActMap() {
      return this.criminalActMap;
   }

   public void setCriminalActMap(Map criminalActMap) {
      this.criminalActMap = criminalActMap;
   }

   public Set getVictimNameSet() {
      return this.victimNameSet;
   }

   public void setVictimNameSet(Set victimNameSet) {
      this.victimNameSet = victimNameSet;
   }

   public Set getWitnessNameSet() {
      return this.witnessNameSet;
   }

   public void setWitnessNameSet(Set witnessNameSet) {
      this.witnessNameSet = witnessNameSet;
   }
}
