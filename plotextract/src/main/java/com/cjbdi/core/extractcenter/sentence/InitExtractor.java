package com.cjbdi.core.extractcenter.sentence;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;
import com.cjbdi.core.developcenter.utils.CommonTools;
import com.cjbdi.core.extractcenter.sentence.affray.AffrayExtractor;
import com.cjbdi.core.extractcenter.sentence.chaseRace.ChaseRaceExtractor;
import com.cjbdi.core.extractcenter.sentence.companycontractfraud.CompanyContractFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.companyfundraisingfraud.CompanyFundRaisingFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.companyillegallyabsorbingpublicdeposits.CompanyIllegallyAbsorbingPublicDepositsExtractor;
import com.cjbdi.core.extractcenter.sentence.concealci.ConcealciExtractor;
import com.cjbdi.core.extractcenter.sentence.counterfeitFraud.CounterfeitFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.courtdecision.CourtDecisionExtracor;
import com.cjbdi.core.extractcenter.sentence.drug.DrugExtractor;
import com.cjbdi.core.extractcenter.sentence.drunkdriving.DrunkDrivingExtractor;
import com.cjbdi.core.extractcenter.sentence.dutyencroachment.DutyencroachmentExtractor;
import com.cjbdi.core.extractcenter.sentence.endangerpa.EndangerExtractor;
import com.cjbdi.core.extractcenter.sentence.extortion.ExtortionExtractor;
import com.cjbdi.core.extractcenter.sentence.falseimprison.FalseimprisonExtractor;
import com.cjbdi.core.extractcenter.sentence.findtrouble.FindtroubleExtractor;
import com.cjbdi.core.extractcenter.sentence.fraud.FraudExtractor;
import com.cjbdi.core.extractcenter.sentence.fraudulentIdentificationFraud.FraudulentIdentificationFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs.IllegalPossessionDrugExtractor;
import com.cjbdi.core.extractcenter.sentence.individualcontractfraud.IndividualContractFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.individualfundraisingfraud.IndividualFundRaisingFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.individualillegallyabsorbingpublicdeposits.IndividualIllegallyAbsorbingPublicDepositsExtractor;
import com.cjbdi.core.extractcenter.sentence.injury.InjuryExtractor;
import com.cjbdi.core.extractcenter.sentence.invalidFraud.InvalidFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.maliciousOverDraftFraud.MaliciousOverDraftFraudExtractor;
import com.cjbdi.core.extractcenter.sentence.prostitution.ProstitutionExtractor;
import com.cjbdi.core.extractcenter.sentence.providingvenuesfordrugusers.ProvidingVenuesForDrugUsersExtractor;
import com.cjbdi.core.extractcenter.sentence.rape.RapeExtractor;
import com.cjbdi.core.extractcenter.sentence.robbery.RobberyExtractor;
import com.cjbdi.core.extractcenter.sentence.schoolbusbusinessorpassengertransportation.SchoolBusBusinessOrPassengerTransportationExtractor;
import com.cjbdi.core.extractcenter.sentence.seizing.SeizingExtractor;
import com.cjbdi.core.extractcenter.sentence.share.ShareExtractor;
import com.cjbdi.core.extractcenter.sentence.steal.StealExtractor;
import com.cjbdi.core.extractcenter.sentence.traffic.TrafficExtractor;
import com.cjbdi.core.extractcenter.sentence.transportationofhazardouschemical.TransportationOfHazardousChemicalExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Age;
import com.cjbdi.core.extractcenter.sentence.utils.Birthday;
import com.cjbdi.core.extractcenter.sentence.utils.ExtractCrimeRecord;
import com.cjbdi.core.extractcenter.sentence.utils.GenderExTractor;
import com.cjbdi.core.extractcenter.sentence.utils.IdCardNumExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.VictimExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.servercenter.utils.Tools;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InitExtractor {

   Map sentenceExtractors = new HashMap();

   private DrunkDrivingExtractor drunkDrivingExtractor;
   private SchoolBusBusinessOrPassengerTransportationExtractor schoolBusBusinessOrPassengerTransportationExtractor;
   private TransportationOfHazardousChemicalExtractor transportationOfHazardousChemicalExtractor;
   private ChaseRaceExtractor chaseRaceExtractor;
   private IndividualFundRaisingFraudExtractor individualFundRaisingFraudExtractor;
   private CompanyFundRaisingFraudExtractor companyFundRaisingFraudExtractor;
   private FraudulentIdentificationFraudExtractor fraudulentIdentificationFraudExtractor;
   private MaliciousOverDraftFraudExtractor maliciousOverDraftFraudExtractor;
   private CounterfeitFraudExtractor counterfeitFraudExtractor;
   private InvalidFraudExtractor invalidFraudExtractor;
   private ProvidingVenuesForDrugUsersExtractor providingVenuesForDrugUsersExtractor;
   private ProstitutionExtractor prostitutionExtractor;
   private IllegalPossessionDrugExtractor illegalPossessionDrugExtractor;
   private ShareExtractor shareExtractor;
   private IndividualContractFraudExtractor individualContractFraudExtractor;
   private CompanyContractFraudExtractor companyContractFraudExtractor;
   private IndividualIllegallyAbsorbingPublicDepositsExtractor individualIllegallyAbsorbingPublicDepositsExtractor;
   private CompanyIllegallyAbsorbingPublicDepositsExtractor companyIllegallyAbsorbingPublicDepositsExtractor;
   private TrafficExtractor trafficExtractor;
   private InjuryExtractor injuryExtractor;
   private RapeExtractor rapeExtractor;
   private StealExtractor stealExtractor;
   private SeizingExtractor seizingExtractor;
   private DrugExtractor drugExtractor;
   private FindtroubleExtractor findtroubleExtractor;
   private ConcealciExtractor concealciExtractor;
   private RobberyExtractor robberyExtractor;
   private FalseimprisonExtractor falseimprisonExtractor;
   private FraudExtractor fraudExtractor;
   private ExtortionExtractor extortionExtractor;
   private EndangerExtractor endangerExtractor;
   private DutyencroachmentExtractor dutyencroachmentExtractor;
   private AffrayExtractor affrayExtractor;
   private CourtDecisionExtracor courtDecisionExtracor;


   public InitExtractor() {
      this.drunkDrivingExtractor = new DrunkDrivingExtractor(BeanFactoryConfig.extractFeatureConfig.getDrunkDriving());
      this.schoolBusBusinessOrPassengerTransportationExtractor = new SchoolBusBusinessOrPassengerTransportationExtractor(BeanFactoryConfig.extractFeatureConfig.getSchoolBusBusinessOrPassengerTransportation());
      this.transportationOfHazardousChemicalExtractor = new TransportationOfHazardousChemicalExtractor(BeanFactoryConfig.extractFeatureConfig.getTransportationOfHazarDousChemical());
      this.chaseRaceExtractor = new ChaseRaceExtractor(BeanFactoryConfig.extractFeatureConfig.getChaseRace());
      this.individualFundRaisingFraudExtractor = new IndividualFundRaisingFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getIndividualFundRaisingFraud());
      this.companyFundRaisingFraudExtractor = new CompanyFundRaisingFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getCompanyFundRaisingFraud());
      this.fraudulentIdentificationFraudExtractor = new FraudulentIdentificationFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getFraudulentIdentificationFraud());
      this.maliciousOverDraftFraudExtractor = new MaliciousOverDraftFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getMaliciousOverDraftFraud());
      this.counterfeitFraudExtractor = new CounterfeitFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getCounterfeitFraud());
      this.invalidFraudExtractor = new InvalidFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getInvalidFraud());
      this.providingVenuesForDrugUsersExtractor = new ProvidingVenuesForDrugUsersExtractor(BeanFactoryConfig.extractFeatureConfig.getProvidingVenuesForDrugUsers());
      this.prostitutionExtractor = new ProstitutionExtractor(BeanFactoryConfig.extractFeatureConfig.getProstitution());
      this.illegalPossessionDrugExtractor = new IllegalPossessionDrugExtractor(BeanFactoryConfig.extractFeatureConfig.getIllegalPossessionDrugs());
      this.shareExtractor = new ShareExtractor(BeanFactoryConfig.extractFeatureConfig.getShare());
      this.individualContractFraudExtractor = new IndividualContractFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getIndividualContractFraud());
      this.companyContractFraudExtractor = new CompanyContractFraudExtractor(BeanFactoryConfig.extractFeatureConfig.getCompanyContractFraud());
      this.individualIllegallyAbsorbingPublicDepositsExtractor = new IndividualIllegallyAbsorbingPublicDepositsExtractor(BeanFactoryConfig.extractFeatureConfig.getIndividualIllegallyAbsorbingPublicDeposits());
      this.companyIllegallyAbsorbingPublicDepositsExtractor = new CompanyIllegallyAbsorbingPublicDepositsExtractor(BeanFactoryConfig.extractFeatureConfig.getCompanyIllegallyAbsorbingPublicDeposits());
      this.trafficExtractor = new TrafficExtractor(BeanFactoryConfig.extractFeatureConfig.getTraffic());
      this.injuryExtractor = new InjuryExtractor(BeanFactoryConfig.extractFeatureConfig.getInjury());
      this.rapeExtractor = new RapeExtractor(BeanFactoryConfig.extractFeatureConfig.getRape());
      this.stealExtractor = new StealExtractor(BeanFactoryConfig.extractFeatureConfig.getSteal());
      this.seizingExtractor = new SeizingExtractor(BeanFactoryConfig.extractFeatureConfig.getSeizing());
      this.drugExtractor = new DrugExtractor(BeanFactoryConfig.extractFeatureConfig.getDrug());
      this.findtroubleExtractor = new FindtroubleExtractor(BeanFactoryConfig.extractFeatureConfig.getFindtrouble());
      this.concealciExtractor = new ConcealciExtractor(BeanFactoryConfig.extractFeatureConfig.getConcealci());
      this.robberyExtractor = new RobberyExtractor(BeanFactoryConfig.extractFeatureConfig.getRobbery());
      this.falseimprisonExtractor = new FalseimprisonExtractor(BeanFactoryConfig.extractFeatureConfig.getFalseimprison());
      this.fraudExtractor = new FraudExtractor(BeanFactoryConfig.extractFeatureConfig.getFraud());
      this.extortionExtractor = new ExtortionExtractor(BeanFactoryConfig.extractFeatureConfig.getExtortion());
      this.endangerExtractor = new EndangerExtractor(BeanFactoryConfig.extractFeatureConfig.getEndangerpa());
      this.dutyencroachmentExtractor = new DutyencroachmentExtractor(BeanFactoryConfig.extractFeatureConfig.getDutyencroachment());
      this.affrayExtractor = new AffrayExtractor(BeanFactoryConfig.extractFeatureConfig.getAffray());
      this.courtDecisionExtracor = new CourtDecisionExtracor(BeanFactoryConfig.extractFeatureConfig.getCourtDecision());
      this.sentenceExtractors.put("总则量刑情节", this.shareExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drunkDrivingextract")).getName(), this.drunkDrivingExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("transportationOfHazardousChemicalsextract")).getName(), this.transportationOfHazardousChemicalExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("chaseRaceextract")).getName(), this.chaseRaceExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("schoolBusBusinessOrPassengerTransportationextract")).getName(), this.schoolBusBusinessOrPassengerTransportationExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("trafficextract")).getName(), this.trafficExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("injuryextract")).getName(), this.injuryExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("rapeextract")).getName(), this.rapeExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("falseimprisonextract")).getName(), this.falseimprisonExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("robberyextract")).getName(), this.robberyExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("stealextract")).getName(), this.stealExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudextract")).getName(), this.fraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("seizingextract")).getName(), this.seizingExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("dutyencroachmentextract")).getName(), this.dutyencroachmentExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("extortionextract")).getName(), this.extortionExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("endangerpaextract")).getName(), this.endangerExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("affaryextract")).getName(), this.affrayExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("fraudulentIdentificationFraudextract")).getName(), this.fraudulentIdentificationFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("maliciousOverDraftFraudextract")).getName(), this.maliciousOverDraftFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("invalidFraudextract")).getName(), this.invalidFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("counterfeitFraudextract")).getName(), this.counterfeitFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("illegalPossessionDrugsextract")).getName(), this.illegalPossessionDrugExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("prostitutionextract")).getName(), this.prostitutionExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("providingVenuesForDrugUsersextract")).getName(), this.providingVenuesForDrugUsersExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualContractFraudextract")).getName(), this.individualContractFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyContractFraudextract")).getName(), this.companyContractFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualFundRaisingFraudextract")).getName(), this.individualFundRaisingFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyFundRaisingFraudextract")).getName(), this.companyFundRaisingFraudExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("individualIllegallyAbsorbingPublicDepositsextract")).getName(), this.individualIllegallyAbsorbingPublicDepositsExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("companyIllegallyAbsorbingPublicDepositsextract")).getName(), this.companyIllegallyAbsorbingPublicDepositsExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drugextract")).getName(), this.drugExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("findtroubleextract")).getName(), this.findtroubleExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("concealextract")).getName(), this.concealciExtractor);
      this.sentenceExtractors.put("判决结果", this.courtDecisionExtracor);
   }

   public DrunkDrivingExtractor getDrunkDrivingExtractor() {
      return drunkDrivingExtractor;
   }

   public SchoolBusBusinessOrPassengerTransportationExtractor getSchoolBusBusinessOrPassengerTransportationExtractor() {
      return schoolBusBusinessOrPassengerTransportationExtractor;
   }

   public TransportationOfHazardousChemicalExtractor getTransportationOfHazardousChemicalExtractor() {
      return transportationOfHazardousChemicalExtractor;
   }

   public ChaseRaceExtractor getChaseRaceExtractor() {
      return chaseRaceExtractor;
   }

   public IndividualFundRaisingFraudExtractor getIndividualFundRaisingFraudExtractor() {
      return individualFundRaisingFraudExtractor;
   }

   public CompanyFundRaisingFraudExtractor getCompanyFundRaisingFraudExtractor() {
      return companyFundRaisingFraudExtractor;
   }

   public FraudulentIdentificationFraudExtractor getFraudulentIdentificationFraudExtractor() {
      return fraudulentIdentificationFraudExtractor;
   }

   public MaliciousOverDraftFraudExtractor getMaliciousOverDraftFraudExtractor() {
      return maliciousOverDraftFraudExtractor;
   }

   public CounterfeitFraudExtractor getCounterfeitFraudExtractor() {
      return counterfeitFraudExtractor;
   }

   public InvalidFraudExtractor getInvalidFraudExtractor() {
      return invalidFraudExtractor;
   }

   public ProvidingVenuesForDrugUsersExtractor getProvidingVenuesForDrugUsersExtractor() {
      return providingVenuesForDrugUsersExtractor;
   }

   public ProstitutionExtractor getProstitutionExtractor() {
      return prostitutionExtractor;
   }

   public IllegalPossessionDrugExtractor getIllegalPossessionDrugExtractor() {
      return illegalPossessionDrugExtractor;
   }

   public ShareExtractor getShareExtractor() {
      return shareExtractor;
   }

   public IndividualContractFraudExtractor getIndividualContractFraudExtractor() {
      return individualContractFraudExtractor;
   }

   public CompanyContractFraudExtractor getCompanyContractFraudExtractor() {
      return companyContractFraudExtractor;
   }

   public IndividualIllegallyAbsorbingPublicDepositsExtractor getIndividualIllegallyAbsorbingPublicDepositsExtractor() {
      return individualIllegallyAbsorbingPublicDepositsExtractor;
   }

   public CompanyIllegallyAbsorbingPublicDepositsExtractor getCompanyIllegallyAbsorbingPublicDepositsExtractor() {
      return companyIllegallyAbsorbingPublicDepositsExtractor;
   }

   public TrafficExtractor getTrafficExtractor() {
      return trafficExtractor;
   }

   public InjuryExtractor getInjuryExtractor() {
      return injuryExtractor;
   }

   public RapeExtractor getRapeExtractor() {
      return rapeExtractor;
   }

   public StealExtractor getStealExtractor() {
      return stealExtractor;
   }

   public SeizingExtractor getSeizingExtractor() {
      return seizingExtractor;
   }

   public DrugExtractor getDrugExtractor() {
      return drugExtractor;
   }

   public FindtroubleExtractor getFindtroubleExtractor() {
      return findtroubleExtractor;
   }

   public ConcealciExtractor getConcealciExtractor() {
      return concealciExtractor;
   }

   public RobberyExtractor getRobberyExtractor() {
      return robberyExtractor;
   }

   public FalseimprisonExtractor getFalseimprisonExtractor() {
      return falseimprisonExtractor;
   }

   public FraudExtractor getFraudExtractor() {
      return fraudExtractor;
   }

   public ExtortionExtractor getExtortionExtractor() {
      return extortionExtractor;
   }

   public EndangerExtractor getEndangerExtractor() {
      return endangerExtractor;
   }

   public DutyencroachmentExtractor getDutyencroachmentExtractor() {
      return dutyencroachmentExtractor;
   }

   public AffrayExtractor getAffrayExtractor() {
      return affrayExtractor;
   }

   public CourtDecisionExtracor getCourtDecisionExtracor() {
      return courtDecisionExtracor;
   }

   public Map getSentenceExtractors() {
      return this.sentenceExtractors;
   }

   public void basicInfo(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      String defendantName = defendantModel.getName();
      Set defendantNameSet = defendantModel.getDefendantNameSet();
      String accuse = casecauseModel.getAccuse();
      String justice = casecauseModel.getJustice();
      String transfer = casecauseModel.getTransfer();
      String background = casecauseModel.getDefendant();
      justice = CommonTools.jointAccuseJustice(accuse, justice);
      justice = CommonTools.jointAccuseJustice(accuse, justice);
      defendantModel.setCrimeDate(Tools.extractDate(justice, true));
      defendantModel.setCriminalRecordList(ExtractCrimeRecord.run(background, defendantModel.getCrimeDate(), defendantName));
      defendantModel.setArrestDate(Tools.extractDate(background, false));
      defendantModel.setProsecuteDate(Tools.extractDate(transfer, true));
      defendantModel.setGender(GenderExTractor.extract(background, defendantName));
      defendantModel.setIdcard(IdCardNumExtractor.extract(background, defendantName));
      defendantModel.setBirthday(Birthday.run(background, defendantName, defendantModel.getIdcard()));
      defendantModel.setAge(Age.run(defendantModel));
      defendantModel.setVictimNameSet(VictimExtractor.run(casecauseModel.getJustice(), defendantNameSet));
   }
}
