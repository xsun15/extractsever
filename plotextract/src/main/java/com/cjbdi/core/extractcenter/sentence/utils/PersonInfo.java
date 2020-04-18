package com.cjbdi.core.extractcenter.sentence.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonInfo {

   public boolean canSize = false;
   public String name;
   public String casecause;
   public Optional birthdate;
   public Optional birthdate1;
   public Optional age;
   public Optional criminalRecords;
   public Optional courtDate;
   public Optional courtDate1;
   public Optional IdCardNum;
   public Optional allDenfendant;
   public List casecauseList;
   public Optional GenDer;
   public List criminalActs;
   public Optional criminalTime;
   public List criminalActsNew;
   public Optional factText;
   public Optional factHeader;
   public Optional conclusionText;
   public Optional factHail;
   public String originalText;


   public PersonInfo(String name) {
      this.name = name;
      this.casecause = "";
      this.birthdate = Optional.empty();
      this.age = Optional.empty();
      this.criminalRecords = Optional.empty();
      this.courtDate = Optional.empty();
      this.factText = Optional.empty();
      this.allDenfendant = Optional.empty();
      this.conclusionText = Optional.empty();
      this.factHeader = Optional.empty();
      this.factHail = Optional.empty();
      this.originalText = "";
      this.IdCardNum = Optional.empty();
      this.GenDer = Optional.empty();
      this.criminalTime = Optional.empty();
      this.casecauseList = new ArrayList();
   }
}
