package com.cjbdi.core.extractcenter.utils;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DefendantModel {

   private String name;
   private Set casecauseSet;
   private Set defendantNameSet;
   private Map casecauseModelMap;
   private Optional gender;
   private Optional idcard;
   private Optional birthday;
   private Optional age = Optional.empty();
   private Optional crimeDate;
   private Optional arrestDate;
   private Optional prosecuteDate;
   private List criminalRecordList;
   private Map criminalActMap;
   private Map casecauseFeatureMap;
   private Map shareFeatureMap;
   private Map courtDecisionMap;
   private Set victimNameSet;
   private Set witnessNameSet;


   public DefendantModel(JSONObject jsonObject) {
      if(!jsonObject.isEmpty()) {
         this.name = jsonObject.getString("name");
         this.casecauseSet = new HashSet((Collection)jsonObject.getObject("casecauseSet", List.class));
         this.defendantNameSet = new HashSet((Collection)jsonObject.getObject("nameSet", List.class));
         this.casecauseModelMap = new HashMap();
         JSONObject casecauseModelMapJson = jsonObject.getJSONObject("casecauseModelMap");
         Iterator var3 = casecauseModelMapJson.keySet().iterator();

         while(var3.hasNext()) {
            String casecause = (String)var3.next();
            this.casecauseModelMap.put(casecause, casecauseModelMapJson.getObject(casecause, CasecauseModel.class));
         }

         this.casecauseFeatureMap = new HashMap();
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

   public void setCasecauseSet(Set casecauseSet) {
      this.casecauseSet = casecauseSet;
   }

   public Map getCasecauseModelMap() {
      return this.casecauseModelMap;
   }

   public void setCasecauseModelMap(Map casecauseModelMap) {
      this.casecauseModelMap = casecauseModelMap;
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

   public Optional getIdcard() {
      return this.idcard;
   }

   public void setIdcard(Optional idcard) {
      this.idcard = idcard;
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

   public Map getCasecauseFeatureMap() {
      return this.casecauseFeatureMap;
   }

   public void setCasecauseFeatureMap(Map casecauseFeatureMap) {
      this.casecauseFeatureMap = casecauseFeatureMap;
   }

   public void setCasecauseFeatureMap(String casecause, List labelList) {
      this.casecauseFeatureMap.put(casecause, labelList);
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
