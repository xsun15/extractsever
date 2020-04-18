package com.cjbdi.core.extractcenter.sentence.illegalPossessionDrugs;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class DrugsTypes extends BasicSentenceFeatureClass {

   private String name;
   private String code = "1004";
   private List drugType;


   public DrugsTypes() {
      this.name = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getIllegalPossessionDrugs().getFeatures().get(this.code)).getName();
      this.drugType = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getIllegalPossessionDrugs().getFeatures().get(this.code)).getPositivepurePattern();
   }

   public List runlabels(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      ArrayList labels = new ArrayList();
      String conclusionText = casecauseModel.getOpinion();
      String factText = casecauseModel.getJustice();
      Iterator var7;
      Pattern type;
      Label label;
      if(conclusionText != null && !conclusionText.isEmpty()) {
         var7 = this.drugType.iterator();

         while(var7.hasNext()) {
            type = (Pattern)var7.next();
            if(conclusionText.contains(type.toString())) {
               label = SetLabel.run(type.toString(), this.code);
               labels.add(label);
            }
         }
      }

      if(factText != null && !factText.isEmpty()) {
         var7 = this.drugType.iterator();

         while(var7.hasNext()) {
            type = (Pattern)var7.next();
            if(factText.contains(type.toString())) {
               label = SetLabel.run(type.toString(), this.code);
               boolean flag = false;
               Iterator var11 = labels.iterator();

               while(var11.hasNext()) {
                  Label exits = (Label)var11.next();
                  if(label.equal(exits)) {
                     flag = true;
                  }
               }

               if(!flag) {
                  labels.add(label);
               }
            }
         }
      }

      return labels.size() > 0?labels:null;
   }
}
