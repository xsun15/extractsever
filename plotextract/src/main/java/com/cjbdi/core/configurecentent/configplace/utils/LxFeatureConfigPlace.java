package com.cjbdi.core.configurecentent.configplace.utils;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;
import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class LxFeatureConfigPlace {

   private List common;
   private List money;
   private List gram;
   private List courtdecision;
   private List share;
   private List traffic;
   private List injury;
   private List rape;
   private List steal;
   private List fraud;
   private List seizing;
   private List findtrouble;
   private List illegaldetension;
   private List extortion;
   private List endangerpa;
   private List dutyencroachment;
   private List drug;
   private List concealci;
   private List affray;
   private List robbery;
   private List invalidCreditCardFraud;
   private List fraudulentIdentificationFraud;
   private List counterfeitCreditCardFraud;
   private List maliciousOverDraftFraud;
   private List drunkDriving;
   private List chaseRace;
   private List schoolBusBusinessOrPassengerTransportation;
   private List transportationOfHazardousChemical;
   private List companyIllegallyAbsorbingPublicDeposits;
   private List individualIllegallyAbsorbingPublicDeposits;
   private List individualFundRaisingFraud;
   private List companyFundRaisingFraud;
   private List illegalPossessionDrugs;
   private List prostitution;
   private List providingVenuesForDrugUsers;
   private List individualContractFraud;
   private List companyContractFraud;


   public LxFeatureConfigPlace(String featureName, String sourceName) {
      this.common = YamlPropertySourceFactoryUser.loadConfig(featureName + ".common", sourceName);
      this.money = YamlPropertySourceFactoryUser.loadConfig(featureName + ".money", sourceName);
      this.gram = YamlPropertySourceFactoryUser.loadConfig(featureName + ".gram", sourceName);
      this.courtdecision = YamlPropertySourceFactoryUser.loadConfig(featureName + ".courtdecision", sourceName);
      this.traffic = YamlPropertySourceFactoryUser.loadConfig(featureName + ".traffic", sourceName);
      this.share = YamlPropertySourceFactoryUser.loadConfig(featureName + ".share", sourceName);
      this.injury = YamlPropertySourceFactoryUser.loadConfig(featureName + ".injury", sourceName);
      this.rape = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rape", sourceName);
      this.robbery = YamlPropertySourceFactoryUser.loadConfig(featureName + ".robbery", sourceName);
      this.steal = YamlPropertySourceFactoryUser.loadConfig(featureName + ".steal", sourceName);
      this.illegaldetension = YamlPropertySourceFactoryUser.loadConfig(featureName + ".illegaldetension", sourceName);
      this.fraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".fraud", sourceName);
      this.seizing = YamlPropertySourceFactoryUser.loadConfig(featureName + ".sezing", sourceName);
      this.dutyencroachment = YamlPropertySourceFactoryUser.loadConfig(featureName + ".dutyencroachment", sourceName);
      this.extortion = YamlPropertySourceFactoryUser.loadConfig(featureName + ".extortion", sourceName);
      this.endangerpa = YamlPropertySourceFactoryUser.loadConfig(featureName + ".endangerpa", sourceName);
      this.affray = YamlPropertySourceFactoryUser.loadConfig(featureName + ".affray", sourceName);
      this.findtrouble = YamlPropertySourceFactoryUser.loadConfig(featureName + ".findtrouble", sourceName);
      this.concealci = YamlPropertySourceFactoryUser.loadConfig(featureName + ".concealci", sourceName);
      this.invalidCreditCardFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".invalidCreditCardFraud", sourceName);
      this.fraudulentIdentificationFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".fraudulentIdentificationFraud", sourceName);
      this.counterfeitCreditCardFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".counterfeitCreditCardFraud", sourceName);
      this.maliciousOverDraftFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".maliciousOverDraftFraud", sourceName);
      this.drunkDriving = YamlPropertySourceFactoryUser.loadConfig(featureName + ".drunkDriving", sourceName);
      this.chaseRace = YamlPropertySourceFactoryUser.loadConfig(featureName + ".chaseRace", sourceName);
      this.schoolBusBusinessOrPassengerTransportation = YamlPropertySourceFactoryUser.loadConfig(featureName + ".schoolBusBusinessOrPassengerTransportation", sourceName);
      this.transportationOfHazardousChemical = YamlPropertySourceFactoryUser.loadConfig(featureName + ".transportationOfHazardousChemical", sourceName);
      this.companyIllegallyAbsorbingPublicDeposits = YamlPropertySourceFactoryUser.loadConfig(featureName + ".companyIllegallyAbsorbingPublicDeposits", sourceName);
      this.individualIllegallyAbsorbingPublicDeposits = YamlPropertySourceFactoryUser.loadConfig(featureName + ".individualIllegallyAbsorbingPublicDeposits", sourceName);
      this.individualFundRaisingFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".individualFundRaisingFraud", sourceName);
      this.companyFundRaisingFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".companyFundRaisingFraud", sourceName);
      this.illegalPossessionDrugs = YamlPropertySourceFactoryUser.loadConfig(featureName + ".illegalPossessionDrugs", sourceName);
      this.prostitution = YamlPropertySourceFactoryUser.loadConfig(featureName + ".prostitution", sourceName);
      this.providingVenuesForDrugUsers = YamlPropertySourceFactoryUser.loadConfig(featureName + ".providingVenuesForDrugUsers", sourceName);
      this.individualContractFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".individualContractFraud", sourceName);
      this.companyContractFraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".companyContractFraud", sourceName);
      this.drug = YamlPropertySourceFactoryUser.loadConfig(featureName + ".drug", sourceName);
      this.seizing = YamlPropertySourceFactoryUser.loadConfig(featureName + ".seizing", sourceName);
   }

   public String getDrunkDriving() {
      return StringUtils.strip(this.drunkDriving.toString(), "[]");
   }

   public void setDrunkDriving(List drunkDriving) {
      this.drunkDriving = drunkDriving;
   }

   public String getChaseRace() {
      return StringUtils.strip(this.chaseRace.toString(), "[]");
   }

   public void setChaseRace(List chaseRace) {
      this.chaseRace = chaseRace;
   }

   public String getSchoolBusBusinessOrPassengerTransportation() {
      return StringUtils.strip(this.schoolBusBusinessOrPassengerTransportation.toString(), "[]");
   }

   public void setSchoolBusBusinessOrPassengerTransportation(List schoolBusBusinessOrPassengerTransportation) {
      this.schoolBusBusinessOrPassengerTransportation = schoolBusBusinessOrPassengerTransportation;
   }

   public String getTransportationOfHazardousChemical() {
      return StringUtils.strip(this.transportationOfHazardousChemical.toString(), "[]");
   }

   public void setTransportationOfHazardousChemical(List transportationOfHazardousChemical) {
      this.transportationOfHazardousChemical = transportationOfHazardousChemical;
   }

   public String getIndividualIllegallyAbsorbingPublicDeposits() {
      return StringUtils.strip(this.individualIllegallyAbsorbingPublicDeposits.toString(), "[]");
   }

   public void setIndividualIllegallyAbsorbingPublicDeposits(List individualIllegallyAbsorbingPublicDeposits) {
      this.individualIllegallyAbsorbingPublicDeposits = individualIllegallyAbsorbingPublicDeposits;
   }

   public String getIndividualFundRaisingFraud() {
      return StringUtils.strip(this.companyFundRaisingFraud.toString(), "[]");
   }

   public void setIndividualFundRaisingFraud(List individualFundRaisingFraud) {
      this.individualFundRaisingFraud = individualFundRaisingFraud;
   }

   public String getCompanyFundRaisingFraud() {
      return StringUtils.strip(this.companyFundRaisingFraud.toString(), "[]");
   }

   public void setCompanyFundRaisingFraud(List companyFundRaisingFraud) {
      this.companyFundRaisingFraud = companyFundRaisingFraud;
   }

   public String getIndividualContractFraud() {
      return StringUtils.strip(this.individualContractFraud.toString(), "[]");
   }

   public void setIndividualContractFraud(List individualContractFraud) {
      this.individualContractFraud = individualContractFraud;
   }

   public String getCompanyContractFraud() {
      return StringUtils.strip(this.companyContractFraud.toString(), "[]");
   }

   public void setCompanyContractFraud(List companyContractFraud) {
      this.companyContractFraud = companyContractFraud;
   }

   public String getProstitution() {
      return StringUtils.strip(this.prostitution.toString(), "[]");
   }

   public void setProstitution(List prostitution) {
      this.prostitution = prostitution;
   }

   public String getIllegalPossessionDrugs() {
      return StringUtils.strip(this.illegalPossessionDrugs.toString(), "[]");
   }

   public void setIllegalPossessionDrugs(List illegalPossessionDrugs) {
      this.illegalPossessionDrugs = illegalPossessionDrugs;
   }

   public String getInvalidCreditCardFraud() {
      return StringUtils.strip(this.invalidCreditCardFraud.toString(), "[]");
   }

   public void setInvalidCreditCardFraud(List invalidCreditCardFraud) {
      this.invalidCreditCardFraud = invalidCreditCardFraud;
   }

   public String getFraudulentIdentificationFraud() {
      return StringUtils.strip(this.fraudulentIdentificationFraud.toString(), "[]");
   }

   public void setFraudulentIdentificationFraud(List fraudulentIdentificationFraud) {
      this.fraudulentIdentificationFraud = fraudulentIdentificationFraud;
   }

   public String getCounterfeitCreditCardFraud() {
      return StringUtils.strip(this.counterfeitCreditCardFraud.toString(), "[]");
   }

   public void setCounterfeitCreditCardFraud(List counterfeitCreditCardFraud) {
      this.counterfeitCreditCardFraud = counterfeitCreditCardFraud;
   }

   public String getMaliciousOverDraftFraud() {
      return StringUtils.strip(this.maliciousOverDraftFraud.toString(), "[]");
   }

   public void setMaliciousOverDraftFraud(List maliciousOverDraftFraud) {
      this.maliciousOverDraftFraud = maliciousOverDraftFraud;
   }

   public String getGram() {
      return StringUtils.strip(this.gram.toString(), "[]");
   }

   public void setGram(List gram) {
      this.gram = gram;
   }

   public String getProvidingVenuesForDrugUsers() {
      return StringUtils.strip(this.providingVenuesForDrugUsers.toString(), "[]");
   }

   public void setProvidingVenuesForDrugUsers(List providingVenuesForDrugUsers) {
      this.providingVenuesForDrugUsers = providingVenuesForDrugUsers;
   }

   public void setTraffic(List traffic) {
      this.traffic = traffic;
   }

   public String getTraffic() {
      return StringUtils.strip(this.traffic.toString(), "[]");
   }

   public String getCommon() {
      return StringUtils.strip(this.common.toString(), "[]");
   }

   public void setCommon(List common) {
      this.common = common;
   }

   public String getShare() {
      return StringUtils.strip(this.share.toString(), "[]");
   }

   public void setShare(List share) {
      this.share = share;
   }

   public String getInjury() {
      return StringUtils.strip(this.injury.toString(), "[]");
   }

   public void setInjury(List injury) {
      this.injury = injury;
   }

   public String getRape() {
      return StringUtils.strip(this.rape.toString(), "[]");
   }

   public void setRape(List rape) {
      this.rape = rape;
   }

   public String getMoney() {
      return StringUtils.strip(this.money.toString(), "[]");
   }

   public void setMoney(List money) {
      this.money = money;
   }

   public String getSteal() {
      return StringUtils.strip(this.steal.toString(), "[]");
   }

   public void setSteal(List steal) {
      this.steal = steal;
   }

   public String getFraud() {
      return StringUtils.strip(this.fraud.toString(), "[]");
   }

   public void setFraud(List fraud) {
      this.fraud = fraud;
   }

   public String getSeizing() {
      return StringUtils.strip(this.seizing.toString(), "[]");
   }

   public void setSeizing(List seizing) {
      this.seizing = seizing;
   }

   public String getFindtrouble() {
      return StringUtils.strip(this.findtrouble.toString(), "[]");
   }

   public void setFindtrouble(List findtrouble) {
      this.findtrouble = findtrouble;
   }

   public String getIllegaldetension() {
      return StringUtils.strip(this.illegaldetension.toString(), "[]");
   }

   public void setIllegaldetension(List illegaldetension) {
      this.illegaldetension = illegaldetension;
   }

   public String getExtortion() {
      return StringUtils.strip(this.extortion.toString(), "[]");
   }

   public void setExtortion(List extortion) {
      this.extortion = extortion;
   }

   public String getEndangerpa() {
      return StringUtils.strip(this.endangerpa.toString(), "[]");
   }

   public void setEndangerpa(List endangerpa) {
      this.endangerpa = endangerpa;
   }

   public String getDutyencroachment() {
      return StringUtils.strip(this.dutyencroachment.toString(), "[]");
   }

   public void setDutyencroachment(List dutyencroachment) {
      this.dutyencroachment = dutyencroachment;
   }

   public String getDrug() {
      return StringUtils.strip(this.drug.toString(), "[]");
   }

   public void setDrug(List drug) {
      this.drug = drug;
   }

   public String getConcealci() {
      return StringUtils.strip(this.concealci.toString(), "[]");
   }

   public void setConcealci(List concealci) {
      this.concealci = concealci;
   }

   public String getAffray() {
      return StringUtils.strip(this.affray.toString(), "[]");
   }

   public void setAffray(List affray) {
      this.affray = affray;
   }

   public String getRobbery() {
      return StringUtils.strip(this.robbery.toString(), "[]");
   }

   public void setRobbery(List robbery) {
      this.robbery = robbery;
   }

   public String getCourtdecision() {
      return StringUtils.strip(this.courtdecision.toString(), "[]");
   }

   public void setCourtdecision(List courtdecision) {
      this.courtdecision = courtdecision;
   }

   public String getCompanyIllegallyAbsorbingPublicDeposits() {
      return StringUtils.strip(this.companyIllegallyAbsorbingPublicDeposits.toString(), "[]");
   }

   public void setCompanyIllegallyAbsorbingPublicDeposits(List companyIllegallyAbsorbingPublicDeposits) {
      this.companyIllegallyAbsorbingPublicDeposits = companyIllegallyAbsorbingPublicDeposits;
   }

   public Map getCasecauseLevel() {
      HashMap map = new HashMap();
      map.put("总则量刑情节", StringUtils.strip(this.share.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drunkDrivingextract")).getName(), StringUtils.strip(this.drunkDriving.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("transportationOfHazardousChemicalsextract")).getName(), StringUtils.strip(this.transportationOfHazardousChemical.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("chaseRaceextract")).getName(), StringUtils.strip(this.chaseRace.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("schoolBusBusinessOrPassengerTransportationextract")).getName(), StringUtils.strip(this.schoolBusBusinessOrPassengerTransportation.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("trafficextract")).getName(), StringUtils.strip(this.traffic.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("injuryextract")).getName(), StringUtils.strip(this.injury.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("rapeextract")).getName(), StringUtils.strip(this.rape.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("falseimprisonextract")).getName(), StringUtils.strip(this.illegaldetension.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("robberyextract")).getName(), StringUtils.strip(this.robbery.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("stealextract")).getName(), StringUtils.strip(this.steal.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudextract")).getName(), StringUtils.strip(this.fraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("seizingextract")).getName(), StringUtils.strip(this.seizing.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("dutyencroachmentextract")).getName(), StringUtils.strip(this.dutyencroachment.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("extortionextract")).getName(), StringUtils.strip(this.extortion.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("endangerpaextract")).getName(), StringUtils.strip(this.endangerpa.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("affaryextract")).getName(), StringUtils.strip(this.affray.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudulentIdentificationFraudextract")).getName(), StringUtils.strip(this.fraudulentIdentificationFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("maliciousOverDraftFraudextract")).getName(), StringUtils.strip(this.maliciousOverDraftFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("invalidFraudextract")).getName(), StringUtils.strip(this.invalidCreditCardFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("counterfeitFraudextract")).getName(), StringUtils.strip(this.counterfeitCreditCardFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("illegalPossessionDrugsextract")).getName(), StringUtils.strip(this.illegalPossessionDrugs.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("prostitutionextract")).getName(), StringUtils.strip(this.prostitution.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("providingVenuesForDrugUsersextract")).getName(), StringUtils.strip(this.providingVenuesForDrugUsers.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualContractFraudextract")).getName(), StringUtils.strip(this.individualContractFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyContractFraudextract")).getName(), StringUtils.strip(this.companyContractFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualFundRaisingFraudextract")).getName(), StringUtils.strip(this.individualFundRaisingFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyFundRaisingFraudextract")).getName(), StringUtils.strip(this.companyFundRaisingFraud.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualIllegallyAbsorbingPublicDepositsextract")).getName(), StringUtils.strip(this.individualIllegallyAbsorbingPublicDeposits.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyIllegallyAbsorbingPublicDepositsextract")).getName(), StringUtils.strip(this.individualIllegallyAbsorbingPublicDeposits.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drugextract")).getName(), StringUtils.strip(this.drug.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("findtroubleextract")).getName(), StringUtils.strip(this.findtrouble.toString(), "[]"));
      map.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("concealextract")).getName(), StringUtils.strip(this.concealci.toString(), "[]"));
      return map;
   }
}
