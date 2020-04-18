package com.cjbdi.core.extractcenter.sentence.rape;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.Rape;
import com.cjbdi.core.extractcenter.sentence.rape.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RapeExtractor extends LabelExtractor {

   private Rape rape;
   private InitExtractor initExtractor = new InitExtractor();


   public RapeExtractor(Rape rape) {
      this.modelUrl = BeanFactoryConfig.interfaceConfig.getInterfaceModel().getPredictfeaturepbnew();
      this.basicPureRuleExtracts = this.initExtractor.getBasicPureRuleExtractors();
      this.basicModelRuleExtracts = this.initExtractor.getBasicModelRuleExtractors();
      this.rape = rape;
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList labelList = new ArrayList();
      Iterator var4 = this.initExtractor.basicPrivateExtractors.iterator();

      while(var4.hasNext()) {
         BasicSentenceFeatureClass basicSentenceFeatureClass = (BasicSentenceFeatureClass)var4.next();
         Label label = basicSentenceFeatureClass.run(defendantModel, casecauseModel, this.rape);
         if(label != null) {
            labelList.add(label);
         }
      }

      List labelList1 = this.checkLabel(labelList);
      return labelList1;
   }

   public List checkLabel(List labelList) {
      Label label;
      if(!labelList.isEmpty()) {
         label = null;
         Label girl = null;
         Iterator label1 = labelList.iterator();

         while(label1.hasNext()) {
            Label girlText = (Label)label1.next();
            if(girlText.getFlag() == 1002L) {
               label = girlText;
            } else if(girlText.getFlag() == 1003L) {
               girl = girlText;
            }
         }

         if(label != null && girl != null) {
            String var11 = label.getText().replaceAll("\\[|\\]", "");
            String var13 = girl.getText().replaceAll("\\[|\\]", "");
            String[] womenTextArray = var11.split(",");
            String[] girlTextArray = var13.split(",");
            ArrayList womenTextList = new ArrayList();
            ArrayList girlTextList = new ArrayList();

            int i;
            for(i = 0; i < womenTextArray.length; ++i) {
               womenTextList.add(womenTextArray[i]);
            }

            for(i = 0; i < girlTextArray.length; ++i) {
               girlTextList.add(girlTextArray[i]);
            }

            if(!womenTextList.isEmpty() && !girlTextList.isEmpty()) {
               womenTextList.removeAll(girlTextList);
               if(womenTextList.isEmpty()) {
                  labelList.remove(label);
               } else {
                  label.setValue(String.valueOf(womenTextList.size()));
               }
            }
         }

         if(label == null && girl == null) {
            Label var12 = new Label();
            var12.setFlag(1002L);
            var12.setValue("1");
            labelList.add(var12);
         }
      } else {
         label = new Label();
         label.setFlag(1002L);
         label.setValue("1");
         labelList.add(label);
      }

      return labelList;
   }
}
