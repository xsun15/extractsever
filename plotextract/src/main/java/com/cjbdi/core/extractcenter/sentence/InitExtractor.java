package com.cjbdi.core.extractcenter.sentence;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.casecause.BasicConfig;
import com.cjbdi.core.developcenter.utils.CommonTools;
import com.cjbdi.core.extractcenter.sentence.affray.AffrayExtractor;
import com.cjbdi.core.extractcenter.sentence.concealci.ConcealciExtractor;
import com.cjbdi.core.extractcenter.sentence.courtdecision.CourtDecisionExtracor;
import com.cjbdi.core.extractcenter.sentence.drug.DrugExtractor;
import com.cjbdi.core.extractcenter.sentence.drunkdriving.DrunkDrivingExtractor;
import com.cjbdi.core.extractcenter.sentence.dutyencroachment.DutyencroachmentExtractor;
import com.cjbdi.core.extractcenter.sentence.endangerpa.EndangerExtractor;
import com.cjbdi.core.extractcenter.sentence.extortion.ExtortionExtractor;
import com.cjbdi.core.extractcenter.sentence.falseimprison.FalseimprisonExtractor;
import com.cjbdi.core.extractcenter.sentence.findtrouble.FindtroubleExtractor;
import com.cjbdi.core.extractcenter.sentence.fraud.FraudExtractor;
import com.cjbdi.core.extractcenter.sentence.injury.InjuryExtractor;
import com.cjbdi.core.extractcenter.sentence.rape.RapeExtractor;
import com.cjbdi.core.extractcenter.sentence.robbery.RobberyExtractor;
import com.cjbdi.core.extractcenter.sentence.seizing.SeizingExtractor;
import com.cjbdi.core.extractcenter.sentence.share.ShareExtractor;
import com.cjbdi.core.extractcenter.sentence.steal.StealExtractor;
import com.cjbdi.core.extractcenter.sentence.traffic.TrafficExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.*;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.servercenter.utils.Tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InitExtractor {

   Map<String, LabelExtractor> sentenceExtractors = new HashMap();

   private DrunkDrivingExtractor drunkDrivingExtractor;
   private ShareExtractor shareExtractor;
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
      this.shareExtractor = new ShareExtractor(BeanFactoryConfig.extractFeatureConfig.getShare());
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
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("drugextract")).getName(), this.drugExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("findtroubleextract")).getName(), this.findtroubleExtractor);
      this.sentenceExtractors.put(((BasicConfig)BeanFactoryConfig.predCasecauseConfig.getCasecause().get("concealextract")).getName(), this.concealciExtractor);
      this.sentenceExtractors.put("判决结果", this.courtDecisionExtracor);
   }

   public DrunkDrivingExtractor getDrunkDrivingExtractor() {
      return drunkDrivingExtractor;
   }

   public ShareExtractor getShareExtractor() {
      return shareExtractor;
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

   public Map<String, LabelExtractor> getSentenceExtractors() {
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
