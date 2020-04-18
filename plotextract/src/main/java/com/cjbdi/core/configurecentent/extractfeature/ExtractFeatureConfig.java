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
   private TransportationOfHazarDousChemical transportationOfHazarDousChemical = new TransportationOfHazarDousChemical();
   private SchoolBusBusinessOrPassengerTransportation schoolBusBusinessOrPassengerTransportation = new SchoolBusBusinessOrPassengerTransportation();
   private ChaseRace chaseRace = new ChaseRace();
   private CourtDecision courtDecision = new CourtDecision();
   private IndividualIllegallyAbsorbingPublicDeposits individualIllegallyAbsorbingPublicDeposits = new IndividualIllegallyAbsorbingPublicDeposits();
   private CompanyIllegallyAbsorbingPublicDeposits companyIllegallyAbsorbingPublicDeposits = new CompanyIllegallyAbsorbingPublicDeposits();
   private CompanyFundRaisingFraud companyFundRaisingFraud = new CompanyFundRaisingFraud();
   private CounterfeitFraud counterfeitFraud = new CounterfeitFraud();
   private FraudulentIdentificationFraud fraudulentIdentificationFraud = new FraudulentIdentificationFraud();
   private InvalidFraud invalidFraud = new InvalidFraud();
   private MaliciousOverDraftFraud maliciousOverDraftFraud = new MaliciousOverDraftFraud();
   private IllegalPossessionDrugs illegalPossessionDrugs = new IllegalPossessionDrugs();
   private Prostitution prostitution = new Prostitution();
   private IndividualFundRaisingFraud individualFundRaisingFraud = new IndividualFundRaisingFraud();
   private CompanyContractFraud companyContractFraud = new CompanyContractFraud();
   private IndividualContractFraud individualContractFraud = new IndividualContractFraud();
   private ProvidingVenuesForDrugUsers providingVenuesForDrugUsers = new ProvidingVenuesForDrugUsers();


   public DrunkDriving getDrunkDriving() {
      return this.drunkDriving;
   }

   public TransportationOfHazarDousChemical getTransportationOfHazarDousChemical() {
      return this.transportationOfHazarDousChemical;
   }

   public SchoolBusBusinessOrPassengerTransportation getSchoolBusBusinessOrPassengerTransportation() {
      return this.schoolBusBusinessOrPassengerTransportation;
   }

   public ChaseRace getChaseRace() {
      return this.chaseRace;
   }

   public IndividualIllegallyAbsorbingPublicDeposits getIndividualIllegallyAbsorbingPublicDeposits() {
      return this.individualIllegallyAbsorbingPublicDeposits;
   }

   public CompanyIllegallyAbsorbingPublicDeposits getCompanyIllegallyAbsorbingPublicDeposits() {
      return this.companyIllegallyAbsorbingPublicDeposits;
   }

   public IndividualFundRaisingFraud getIndividualFundRaisingFraud() {
      return this.individualFundRaisingFraud;
   }

   public CompanyFundRaisingFraud getCompanyFundRaisingFraud() {
      return this.companyFundRaisingFraud;
   }

   public CompanyContractFraud getCompanyContractFraud() {
      return this.companyContractFraud;
   }

   public IndividualContractFraud getIndividualContractFraud() {
      return this.individualContractFraud;
   }

   public ProvidingVenuesForDrugUsers getProvidingVenuesForDrugUsers() {
      return this.providingVenuesForDrugUsers;
   }

   public IllegalPossessionDrugs getIllegalPossessionDrugs() {
      return this.illegalPossessionDrugs;
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

   public CounterfeitFraud getCounterfeitFraud() {
      return this.counterfeitFraud;
   }

   public FraudulentIdentificationFraud getFraudulentIdentificationFraud() {
      return this.fraudulentIdentificationFraud;
   }

   public InvalidFraud getInvalidFraud() {
      return this.invalidFraud;
   }

   public Prostitution getProstitution() {
      return this.prostitution;
   }

   public MaliciousOverDraftFraud getMaliciousOverDraftFraud() {
      return this.maliciousOverDraftFraud;
   }

   public Gram getGram() {
      return this.gram;
   }

   public Map getCasecause() {
      HashMap map = new HashMap();
      map.put("总则量刑情节", this.share);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drunkDrivingextract")).getName(), this.drunkDriving);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("transportationOfHazardousChemicalsextract")).getName(), this.transportationOfHazarDousChemical);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("chaseRaceextract")).getName(), this.chaseRace);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("schoolBusBusinessOrPassengerTransportationextract")).getName(), this.schoolBusBusinessOrPassengerTransportation);
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
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudulentIdentificationFraudextract")).getName(), this.fraudulentIdentificationFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("maliciousOverDraftFraudextract")).getName(), this.maliciousOverDraftFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("invalidFraudextract")).getName(), this.invalidFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("counterfeitFraudextract")).getName(), this.counterfeitFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("illegalPossessionDrugsextract")).getName(), this.illegalPossessionDrugs);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("prostitutionextract")).getName(), this.prostitution);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("providingVenuesForDrugUsersextract")).getName(), this.providingVenuesForDrugUsers);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualContractFraudextract")).getName(), this.individualContractFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyContractFraudextract")).getName(), this.companyContractFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualFundRaisingFraudextract")).getName(), this.individualFundRaisingFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyFundRaisingFraudextract")).getName(), this.companyFundRaisingFraud);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualIllegallyAbsorbingPublicDepositsextract")).getName(), this.individualIllegallyAbsorbingPublicDeposits);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyIllegallyAbsorbingPublicDepositsextract")).getName(), this.companyIllegallyAbsorbingPublicDeposits);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drugextract")).getName(), this.drug);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("findtroubleextract")).getName(), this.findtrouble);
      map.put(((BasicConfig) BeanFactoryConfig.predCasecauseConfig.getCasecause().get("concealextract")).getName(), this.concealci);
      return map;
   }
}
