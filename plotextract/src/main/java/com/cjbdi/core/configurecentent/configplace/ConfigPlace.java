package com.cjbdi.core.configurecentent.configplace;

import com.cjbdi.core.configurecentent.configplace.utils.CheckLabelConfigPlace;
import com.cjbdi.core.configurecentent.configplace.utils.EnDecryptConfigPlace;
import com.cjbdi.core.configurecentent.configplace.utils.LeianV1ConfigPlace;
import com.cjbdi.core.configurecentent.configplace.utils.LxFeatureConfigPlace;
import com.cjbdi.core.configurecentent.configplace.utils.OthersConfigPlace;
import com.cjbdi.core.configurecentent.configplace.utils.SelfSentenceConfigPlace;

public class ConfigPlace {

   private CheckLabelConfigPlace checkLabelConfigPlace;
   private LeianV1ConfigPlace leianV1ConfigPlace;
   private LxFeatureConfigPlace lxFeatureConfigPlace;
   private OthersConfigPlace othersConfigPlace;
   private SelfSentenceConfigPlace selfSentenceConfigPlace;
   private EnDecryptConfigPlace enDecryptConfigPlace;


   public ConfigPlace() {
      String place = "configurecenter/configname.yml";
      this.checkLabelConfigPlace = new CheckLabelConfigPlace("checklabel", place);
      this.leianV1ConfigPlace = new LeianV1ConfigPlace("convertlabel.leianv1", place);
      this.selfSentenceConfigPlace = new SelfSentenceConfigPlace("convertlabel.selfsentence", place);
      this.lxFeatureConfigPlace = new LxFeatureConfigPlace("lxfeature", place);
      this.othersConfigPlace = new OthersConfigPlace("others", place);
      this.enDecryptConfigPlace = new EnDecryptConfigPlace("endecrypt", place);
   }

   public CheckLabelConfigPlace getCheckLabelConfigPlace() {
      return this.checkLabelConfigPlace;
   }

   public void setCheckLabelConfigPlace(CheckLabelConfigPlace checkLabelConfigPlace) {
      this.checkLabelConfigPlace = checkLabelConfigPlace;
   }

   public LeianV1ConfigPlace getLeianV1ConfigPlace() {
      return this.leianV1ConfigPlace;
   }

   public void setLeianV1ConfigPlace(LeianV1ConfigPlace leianV1ConfigPlace) {
      this.leianV1ConfigPlace = leianV1ConfigPlace;
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
}
