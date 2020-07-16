package com.cjbdi.core.configurecentent.extractfeature;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Affray;
import com.cjbdi.core.configurecentent.extractfeature.sentence.ChaseRace;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Common;
import com.cjbdi.core.configurecentent.extractfeature.sentence.CompanyContractFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.CompanyFundRaisingFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.CompanyIllegallyAbsorbingPublicDeposits;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Concealci;
import com.cjbdi.core.configurecentent.extractfeature.sentence.CounterfeitFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.CourtDecision;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Drug;
import com.cjbdi.core.configurecentent.extractfeature.sentence.DrunkDriving;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Dutyencroachment;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Endangerpa;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Extortion;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Falseimprison;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Findtrouble;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Fraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.FraudulentIdentificationFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Gram;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IllegalPossessionDrugs;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IndividualContractFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IndividualFundRaisingFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.IndividualIllegallyAbsorbingPublicDeposits;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Injury;
import com.cjbdi.core.configurecentent.extractfeature.sentence.InvalidFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.MaliciousOverDraftFraud;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Money;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Prostitution;
import com.cjbdi.core.configurecentent.extractfeature.sentence.ProvidingVenuesForDrugUsers;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Rape;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Robbery;
import com.cjbdi.core.configurecentent.extractfeature.sentence.SchoolBusBusinessOrPassengerTransportation;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Seizing;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Share;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Steal;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Traffic;
import com.cjbdi.core.configurecentent.extractfeature.sentence.TransportationOfHazarDousChemical;
import java.util.HashMap;
import java.util.Map;

public class ExtractFeatureConfig {

   private Common common = new Common();
   private Money money = new Money();
   private Gram gram = new Gram();
   private Share share = new Share();
   private Traffic traffic = new Traffic();
   private Injury injury = new Injury();
   private Rape rape = new Rape();
   private Steal steal = new Steal();
   private Affray affray = new Affray();
   private Drug drug = new Drug();
   private Dutyencroachment dutyencroachment = new Dutyencroachment();
   private Endangerpa endangerpa = new Endangerpa();
   private Extortion extortion = new Extortion();
   private Falseimprison falseimprison = new Falseimprison();
   private Fraud fraud = new Fraud();
   private Robbery robbery = new Robbery();
   private Seizing seizing = new Seizing();
   private Concealci concealci = new Concealci();
   private Findtrouble findtrouble = new Findtrouble();
   private DrunkDriving drunkDriving = new DrunkDriving();
   private CourtDecision courtDecision = new CourtDecision();



   public DrunkDriving getDrunkDriving() {
      return this.drunkDriving;
   }

   public Concealci getConcealci() {
      return this.concealci;
   }

   public Findtrouble getFindtrouble() {
      return this.findtrouble;
   }

   public Traffic getTraffic() {
      return this.traffic;
   }

   public Common getCommon() {
      return this.common;
   }

   public Share getShare() {
      return this.share;
   }

   public Injury getInjury() {
      return this.injury;
   }

   public Rape getRape() {
      return this.rape;
   }

   public Money getMoney() {
      return this.money;
   }

   public Steal getSteal() {
      return this.steal;
   }

   public Affray getAffray() {
      return this.affray;
   }

   public Drug getDrug() {
      return this.drug;
   }

   public Dutyencroachment getDutyencroachment() {
      return this.dutyencroachment;
   }

   public Endangerpa getEndangerpa() {
      return this.endangerpa;
   }

   public Extortion getExtortion() {
      return this.extortion;
   }

   public Falseimprison getFalseimprison() {
      return this.falseimprison;
   }

   public Fraud getFraud() {
      return this.fraud;
   }

   public Robbery getRobbery() {
      return this.robbery;
   }

   public Seizing getSeizing() {
      return this.seizing;
   }

   public CourtDecision getCourtDecision() {
      return this.courtDecision;
   }

   public Gram getGram() {
      return this.gram;
   }

   public Map getCasecause() {
      HashMap map = new HashMap();
      map.put("总则量刑情节", this.share);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drunkDrivingextract")).getName(), this.drunkDriving);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("trafficextract")).getName(), this.traffic);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("injuryextract")).getName(), this.injury);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("rapeextract")).getName(), this.rape);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("falseimprisonextract")).getName(), this.falseimprison);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("robberyextract")).getName(), this.robbery);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("stealextract")).getName(), this.steal);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudextract")).getName(), this.fraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("seizingextract")).getName(), this.seizing);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("dutyencroachmentextract")).getName(), this.dutyencroachment);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("extortionextract")).getName(), this.extortion);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("endangerpaextract")).getName(), this.endangerpa);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("affaryextract")).getName(), this.affray);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drugextract")).getName(), this.drug);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("findtroubleextract")).getName(), this.findtrouble);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("concealextract")).getName(), this.concealci);
      return map;
   }
}
