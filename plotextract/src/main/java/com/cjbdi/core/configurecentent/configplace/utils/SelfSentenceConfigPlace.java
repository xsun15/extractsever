package com.cjbdi.core.configurecentent.configplace.utils;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class SelfSentenceConfigPlace {

   private List share;
   private List traffic;
   private List injury;
   private List affray;
   private List concealci;
   private List drug;
   private List dutyencroachment;
   private List endangerpa;
   private List extortion;
   private List illegaldetension;
   private List findtrouble;
   private List fraud;
   private List rape;
   private List robbery;
   private List seizing;
   private List steal;
   private List drunkDriving;


   public SelfSentenceConfigPlace(String featureName, String sourceName) {
      this.traffic = YamlPropertySourceFactoryUser.loadConfig(featureName + ".traffic", sourceName);
      this.share = YamlPropertySourceFactoryUser.loadConfig(featureName + ".share", sourceName);
      this.injury = YamlPropertySourceFactoryUser.loadConfig(featureName + ".injury", sourceName);
      this.rape = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rape", sourceName);
      this.steal = YamlPropertySourceFactoryUser.loadConfig(featureName + ".steal", sourceName);
      this.illegaldetension = YamlPropertySourceFactoryUser.loadConfig(featureName + ".illegaldetension", sourceName);
      this.fraud = YamlPropertySourceFactoryUser.loadConfig(featureName + ".fraud", sourceName);
      this.seizing = YamlPropertySourceFactoryUser.loadConfig(featureName + ".seizing", sourceName);
      this.dutyencroachment = YamlPropertySourceFactoryUser.loadConfig(featureName + ".dutyencroachment", sourceName);
      this.extortion = YamlPropertySourceFactoryUser.loadConfig(featureName + ".extortion", sourceName);
      this.endangerpa = YamlPropertySourceFactoryUser.loadConfig(featureName + ".endangerpa", sourceName);
      this.affray = YamlPropertySourceFactoryUser.loadConfig(featureName + ".affray", sourceName);
      this.findtrouble = YamlPropertySourceFactoryUser.loadConfig(featureName + ".findtrouble", sourceName);
      this.concealci = YamlPropertySourceFactoryUser.loadConfig(featureName + ".concealci", sourceName);
      this.drug = YamlPropertySourceFactoryUser.loadConfig(featureName + ".drug", sourceName);
      this.robbery = YamlPropertySourceFactoryUser.loadConfig(featureName + ".robbery", sourceName);
      this.drunkDriving = YamlPropertySourceFactoryUser.loadConfig(featureName + ".drunkDriving", sourceName);
   }

   public String getTraffic() {
      return StringUtils.strip(this.traffic.toString(), "[]");
   }

   public void setTraffic(List traffic) {
      this.traffic = traffic;
   }

   public String getInjury() {
      return StringUtils.strip(this.injury.toString(), "[]");
   }

   public void setInjury(List injury) {
      this.injury = injury;
   }

   public String getShare() {
      return StringUtils.strip(this.share.toString(), "[]");
   }

   public void setShare(List share) {
      this.share = share;
   }

   public String getAffray() {
      return StringUtils.strip(this.affray.toString(), "[]");
   }

   public void setAffray(List affray) {
      this.affray = affray;
   }

   public String getDutyencroachment() {
      return StringUtils.strip(this.dutyencroachment.toString(), "[]");
   }

   public void setDutyencroachment(List dutyencroachment) {
      this.dutyencroachment = dutyencroachment;
   }

   public String getConcealci() {
      return StringUtils.strip(this.concealci.toString(), "[]");
   }

   public void setConcealci(List concealci) {
      this.concealci = concealci;
   }

   public String getDrug() {
      return StringUtils.strip(this.drug.toString(), "[]");
   }

   public void setDrug(List drug) {
      this.drug = drug;
   }

   public String getEndangerpa() {
      return StringUtils.strip(this.endangerpa.toString(), "[]");
   }

   public void setEndangerpa(List endangerpa) {
      this.endangerpa = endangerpa;
   }

   public String getExtortion() {
      return StringUtils.strip(this.extortion.toString(), "[]");
   }

   public void setExtortion(List extortion) {
      this.extortion = extortion;
   }

   public String getIllegaldetension() {
      return StringUtils.strip(this.illegaldetension.toString(), "[]");
   }

   public void setIllegaldetension(List illegaldetension) {
      this.illegaldetension = illegaldetension;
   }

   public String getFindtrouble() {
      return StringUtils.strip(this.findtrouble.toString(), "[]");
   }

   public void setFindtrouble(List findtrouble) {
      this.findtrouble = findtrouble;
   }

   public String getFraud() {
      return StringUtils.strip(this.fraud.toString(), "[]");
   }

   public void setFraud(List fraud) {
      this.fraud = fraud;
   }

   public String getRape() {
      return StringUtils.strip(this.rape.toString(), "[]");
   }

   public void setRape(List rape) {
      this.rape = rape;
   }

   public String getRobbery() {
      return StringUtils.strip(this.robbery.toString(), "[]");
   }

   public void setRobbery(List robbery) {
      this.robbery = robbery;
   }

   public String getSeizing() {
      return StringUtils.strip(this.seizing.toString(), "[]");
   }

   public void setSeizing(List seizing) {
      this.seizing = seizing;
   }

   public String getSteal() {
      return StringUtils.strip(this.steal.toString(), "[]");
   }

   public void setSteal(List steal) {
      this.steal = steal;
   }

   public String getDrunkDriving() {return StringUtils.strip(this.drunkDriving.toString(), "[]");}

   public void setDrunkDriving(List drunkDriving) { this.drunkDriving = drunkDriving; }
}
