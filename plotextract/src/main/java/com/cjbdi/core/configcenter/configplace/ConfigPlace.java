package com.cjbdi.core.configcenter.configplace;


import com.cjbdi.core.configcenter.configplace.utils.*;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;

public class ConfigPlace {

   private CheckLabelConfigPlace checkLabelConfigPlace;
   private LeianConfigPlace leianConfigPlace;
   private LxFeatureConfigPlace lxFeatureConfigPlace;
   private OthersConfigPlace othersConfigPlace;
   private SelfSentenceConfigPlace selfSentenceConfigPlace;
   private ZhenganConfigPlace zhenganConfigPlace;
   private EnDecryptConfigPlace enDecryptConfigPlace;

   public ConfigPlace() {
      String place = "/configName.yml";
      Yaml yaml = new Yaml();
      HashMap<String, HashMap<String, Object>> configPlace = yaml.load(ConfigPlace.class.getResourceAsStream(place));
      zhenganConfigPlace = new ZhenganConfigPlace(configPlace);
      selfSentenceConfigPlace = new SelfSentenceConfigPlace(configPlace);
      leianConfigPlace = new LeianConfigPlace(configPlace);
      othersConfigPlace = new OthersConfigPlace(configPlace);
      lxFeatureConfigPlace = new LxFeatureConfigPlace(configPlace);
      enDecryptConfigPlace = new EnDecryptConfigPlace(configPlace);
      checkLabelConfigPlace = new CheckLabelConfigPlace(configPlace);
   }

   public CheckLabelConfigPlace getCheckLabelConfigPlace() {
      return this.checkLabelConfigPlace;
   }

   public void setCheckLabelConfigPlace(CheckLabelConfigPlace checkLabelConfigPlace) {
      this.checkLabelConfigPlace = checkLabelConfigPlace;
   }

   public LeianConfigPlace getLeianConfigPlace() {
      return this.leianConfigPlace;
   }

   public void setLeianConfigPlace(LeianConfigPlace leianConfigPlace) {
      this.leianConfigPlace = leianConfigPlace;
   }

   public LxFeatureConfigPlace getLxFeatureConfigPlace() {
      return this.lxFeatureConfigPlace;
   }

   public void setLxFeatureConfigPlace(LxFeatureConfigPlace lxFeatureConfigPlace) {
      this.lxFeatureConfigPlace = lxFeatureConfigPlace;
   }

   public OthersConfigPlace getOthersConfigPlace() {
      return this.othersConfigPlace;
   }

   public void setOthersConfigPlace(OthersConfigPlace othersConfigPlace) {
      this.othersConfigPlace = othersConfigPlace;
   }

   public SelfSentenceConfigPlace getSelfSentenceConfigPlace() {
      return this.selfSentenceConfigPlace;
   }

   public void setSelfSentenceConfigPlace(SelfSentenceConfigPlace selfSentenceConfigPlace) {
      this.selfSentenceConfigPlace = selfSentenceConfigPlace;
   }

   public EnDecryptConfigPlace getEnDecryptConfigPlace() {
      return this.enDecryptConfigPlace;
   }

   public void setEnDecryptConfigPlace(EnDecryptConfigPlace enDecryptConfigPlace) {
      this.enDecryptConfigPlace = enDecryptConfigPlace;
   }

   public ZhenganConfigPlace getZhenganConfigPlace() {
      return zhenganConfigPlace;
   }

   public void setZhenganConfigPlace(ZhenganConfigPlace zhenganConfigPlace) {
      this.zhenganConfigPlace = zhenganConfigPlace;
   }
}
