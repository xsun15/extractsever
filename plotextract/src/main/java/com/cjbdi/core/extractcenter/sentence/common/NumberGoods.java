package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.ColorText;
import com.cjbdi.core.extractcenter.utils.ColorTextConfig;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.ExtractNumber;
import com.cjbdi.core.extractcenter.utils.IsDigit;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.cjbdi.core.extractcenter.utils.WordfigureToNumber;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class NumberGoods {

   private List positivePurePattern;
   private List negativePurePattern;


   public NumberGoods(List positivePurePattern, List negativePurePattern) {
      this.positivePurePattern = positivePurePattern;
      this.negativePurePattern = negativePurePattern;
   }

   public NumberConfig doextract(DefendantModel defendantModel, CasecauseModel casecauseModel, String keyword, String noiseword) {
      String conclusion = casecauseModel.getOpinion();
      NumberConfig numberConfig = this.runSummarize(conclusion, keyword, noiseword);
      if(numberConfig != null) {
         return numberConfig;
      } else {
         String factText = casecauseModel.getJustice();
         numberConfig = this.runSummarize(factText, keyword, noiseword);
         return numberConfig != null?numberConfig:null;
      }
   }

   public NumberConfig runSummarize(String text, String keyword, String noiseword) {
      new HashMap();
      if(StringUtils.isNotEmpty(text)) {
         List contentList = Arrays.asList(text.split(noiseword));
         ArrayList targetContent = new ArrayList();
         Iterator injuryNumber = contentList.iterator();

         while(injuryNumber.hasNext()) {
            String numberConfigList = (String)injuryNumber.next();
            if(MatchRule.matchRule(numberConfigList, keyword)) {
               targetContent.add(numberConfigList);
            }
         }

         if(targetContent != null && targetContent.size() > 0) {
            int injuryNumber1 = 0;
            ArrayList numberConfigList1 = new ArrayList();
            Iterator result = targetContent.iterator();

            while(result.hasNext()) {
               String colorTextConfig = (String)result.next();
               NumberConfig colorText = MatchRule.matchPatternNumberConfig(colorTextConfig, this.positivePurePattern, this.negativePurePattern);
               if(colorText != null) {
                  String numberConfig = ExtractNumber.run(WordfigureToNumber.run(colorText.target), "^[0-9][个件]");
                  if(StringUtils.isNotEmpty(numberConfig) && IsDigit.isInteger(numberConfig)) {
                     injuryNumber1 += Integer.parseInt(numberConfig);
                     colorText.startcolor += text.indexOf(colorTextConfig);
                     colorText.endcolor += text.indexOf(colorTextConfig);
                     colorText.colorTarget = colorText.startcolor + "," + colorText.endcolor + ";";
                     numberConfigList1.add(colorText);
                  }
               }
            }

            if(injuryNumber1 > 0) {
               NumberConfig result1 = new NumberConfig();
               ColorTextConfig colorTextConfig1 = new ColorTextConfig();
               colorTextConfig1.text = text;

               NumberConfig numberConfig1;
               for(Iterator colorText1 = numberConfigList1.iterator(); colorText1.hasNext(); result1.rule = result1.rule + numberConfig1.rule) {
                  numberConfig1 = (NumberConfig)colorText1.next();
                  colorTextConfig1.effectText = colorTextConfig1.effectText + numberConfig1.colorTarget;
               }

               ColorText colorText2 = new ColorText();
               result1.value = injuryNumber1;
               result1.colorTarget = colorText2.run(colorTextConfig1);
               return result1;
            }
         }
      }

      return null;
   }
}
