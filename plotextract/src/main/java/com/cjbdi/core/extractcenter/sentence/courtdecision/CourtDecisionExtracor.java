package com.cjbdi.core.extractcenter.sentence.courtdecision;

import com.cjbdi.core.configurecentent.extractfeature.sentence.CourtDecision;
import com.cjbdi.core.extractcenter.sentence.courtdecision.InitExtractor;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.PublicFeatureExtract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class CourtDecisionExtracor extends LabelExtractor {

   private CourtDecision courtDecision;
   private InitExtractor initExtractor = new InitExtractor();


   public CourtDecisionExtracor(CourtDecision courtDecision) {
      this.courtDecision = courtDecision;
   }

   public List extractPrivate(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      ArrayList label = new ArrayList();
      if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
         String conlusion = casecauseModel.getOpinion();
         if(conlusion.contains("判决如下")) {
            String str1 = conlusion.substring(0, conlusion.indexOf("判决如下"));
            String partconlusion = conlusion.substring(str1.length() + 1);
            Iterator var7 = this.initExtractor.basicPureRuleExtractors.keySet().iterator();

            while(var7.hasNext()) {
               String feature = (String)var7.next();
               PublicFeatureExtract publicFeatureExtract = (PublicFeatureExtract)this.initExtractor.basicPureRuleExtractors.get(feature);
               Label label1 = publicFeatureExtract.doExtract(defendantModel.getName(), casecauseModel.getCasecause(), defendantModel.getCasecauseSet(), partconlusion);
               if(label1 != null) {
                  label.add(label1);
               }
            }
         }
      }

      return label;
   }
}
