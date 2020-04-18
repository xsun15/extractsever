package com.cjbdi.core.extractcenter.sentence.providingvenuesfordrugusers;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class People extends BasicSentenceFeatureClass {

   private String name;
   private String code = "1002";
   private List positiveRule;
   private List negativeRule;
   private Segment segment = HanLP.newSegment().enableNameRecognize(true);


   public People() {
      this.name = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getProvidingVenuesForDrugUsers().getFeatures().get(this.code)).getName();
      this.positiveRule = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getProvidingVenuesForDrugUsers().getFeatures().get(this.code)).getPositivepurePattern();
      this.negativeRule = ((ExtractFeatureBasicConfig) BeanFactoryConfig.extractFeatureConfig.getProvidingVenuesForDrugUsers().getFeatures().get(this.code)).getNegativepurePattern();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      new Label();
      String fact = casecauseModel.getJustice();
      HashSet nameSet = new HashSet();
      if(fact.length() > 0) {
         ArrayList minimumSentences = new ArrayList(Arrays.asList(fact.split("。|！|？|；|，|\n")));
         Iterator var8 = minimumSentences.iterator();

         while(var8.hasNext()) {
            String minimumSentence = (String)var8.next();
            int currentPostion = 0;
            int matchStartPosition = MatchRule.matchStartPositionPattern(minimumSentence, this.positiveRule);
            if(matchStartPosition >= 0) {
               List termList = this.segment.seg(minimumSentence);

               Term term;
               for(Iterator var13 = termList.iterator(); var13.hasNext(); currentPostion += term.word.length()) {
                  term = (Term)var13.next();
                  if(term.nature.name().equals("nr") && currentPostion > matchStartPosition) {
                     nameSet.add(term.word);
                  }
               }
            }
         }

         if(nameSet.size() > 0) {
            Label label = SetLabel.run(String.valueOf(nameSet.contains(defendantModel.getName())?nameSet.size() - 1:nameSet.size()), this.code);
            return label;
         }
      }

      return null;
   }
}
