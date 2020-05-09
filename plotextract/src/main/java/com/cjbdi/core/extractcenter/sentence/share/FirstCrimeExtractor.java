package com.cjbdi.core.extractcenter.sentence.share;

import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.sentence.utils.SetLabel;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Optional;

public class FirstCrimeExtractor {

   private String code;
   private List pPatternList;
   private List nPatternList;
   private ExtractFeatureBasicConfig extractFeatureBasicConfig;


   public FirstCrimeExtractor(ExtractFeatureBasicConfig extractFeatureBasicConfig) {
      this.extractFeatureBasicConfig = extractFeatureBasicConfig;
      this.pPatternList = extractFeatureBasicConfig.getPositivepurePattern();
      this.nPatternList = extractFeatureBasicConfig.getNegativepurePattern();
      this.code = extractFeatureBasicConfig.getCode();
   }

   protected Label doextract(DefendantModel defendantModel, CasecauseModel casecauseModel) {

      String conclusion = casecauseModel.getOpinion();
      if (StringUtils.isNotEmpty(conclusion)) {
         conclusion = conclusion.replaceAll(defendantModel.getName(), "被告人");
      }
      BoolConfig boolConfig = MatchRule.matchPatternBoolConfig(conclusion, this.pPatternList, this.nPatternList);
      if(boolConfig != null) {
         Label crimeDate1 = SetLabel.run(boolConfig, this.code);
         crimeDate1.setParaText(conclusion);
         crimeDate1.setParaName("本院认为");
         return crimeDate1;
      } else {
         Optional crimeDate = defendantModel.getCrimeDate();
         if(!crimeDate.isPresent() && defendantModel.getArrestDate().isPresent()) {
            crimeDate = defendantModel.getArrestDate();
         } else if(!crimeDate.isPresent() && defendantModel.getProsecuteDate().isPresent()) {
            crimeDate = defendantModel.getProsecuteDate();
         }

         if(crimeDate.isPresent() && defendantModel.getCriminalRecordList().isEmpty()) {
            return null;
         } else {
            Label label = SetLabel.run("true", this.code);
            label.setText(casecauseModel.getDefendant());
            label.setParaText(casecauseModel.getDefendant());
            label.setParaName("被告人段");
            return label;
         }
      }
   }
}
