package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureBasicConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.GramBasicConfig;
import com.cjbdi.core.decryptcenter.BasicCaseClass;
import com.cjbdi.core.extractcenter.sentence.common.utils.GramTools;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.cjbdi.core.servercenter.utils.Tools;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class Gram extends BasicSentenceFeatureClass {

   private String chiname;
   private String code;
   private String keyword;
   private String noiseword;
   private List noisewordList;
   private List firstPatternList;
   private List secondPatternList;
   private List thirdPatternList;
   private List gramRatioBasicList;
   private List firstInvalidPatternList;


   public Gram(ExtractFeatureBasicConfig extractFeatureBasicConfig) {
      this.keyword = ((GramBasicConfig)BeanFactoryConfig.extractFeatureConfig.getGram().getFeatures().get("gramextract")).getKeyword();
      this.noiseword = ((GramBasicConfig)BeanFactoryConfig.extractFeatureConfig.getGram().getFeatures().get("gramextract")).getNoiseword();
      this.firstPatternList = (List)((GramBasicConfig)BeanFactoryConfig.extractFeatureConfig.getGram().getFeatures().get("gramextract")).getPositivepurePattern().get("firstrule");
      this.secondPatternList = (List)((GramBasicConfig)BeanFactoryConfig.extractFeatureConfig.getGram().getFeatures().get("gramextract")).getPositivepurePattern().get("secondrule");
      this.thirdPatternList = (List)((GramBasicConfig)BeanFactoryConfig.extractFeatureConfig.getGram().getFeatures().get("gramextract")).getPositivepurePattern().get("thirdrule");
      this.gramRatioBasicList = ((GramBasicConfig)BeanFactoryConfig.extractFeatureConfig.getGram().getFeatures().get("ratioextract")).getGramRatioBasic();
      this.firstInvalidPatternList = (List)((GramBasicConfig)BeanFactoryConfig.extractFeatureConfig.getGram().getFeatures().get("gramextract")).getNegativepurePattern().get("firstrule");
      this.keyword = this.keyword.replaceAll("placeholder", extractFeatureBasicConfig.getName());
      this.thirdPatternList = GramTools.replaceHolder(this.thirdPatternList, extractFeatureBasicConfig.getName());
      this.noisewordList = new ArrayList(Arrays.asList(this.noiseword.split("@")));
      if(this.noisewordList.contains(this.keyword)) {
         this.noisewordList.remove(this.keyword);
         if(this.keyword.equals("甲基苯丙胺")) {
            this.noisewordList.remove("苯丙胺");
         } else if(this.keyword.equals("大麻油") || this.keyword.equals("大麻脂") || this.keyword.equals("大麻叶") || this.keyword.equals("大麻烟")) {
            this.noisewordList.remove("大麻");
         }
      }

      this.noiseword = GramTools.list2Regx(this.noisewordList);
      this.chiname = extractFeatureBasicConfig.getName();
      this.code = extractFeatureBasicConfig.getCode();
   }

   public Label run(DefendantModel defendantModel, CasecauseModel casecauseModel, BasicCaseClass caseClass) {
      String casecause = casecauseModel.getCasecause();
      String opinion = casecauseModel.getOpinion();
      String justice = casecauseModel.getJustice();
      this.init();
      List gramConfigList;
      if(StringUtils.isNotEmpty(opinion)) {
         opinion = Tools.deleteDigitDot(opinion);
         gramConfigList = this.run(opinion, this.firstPatternList, this.firstInvalidPatternList);
         if(gramConfigList != null && gramConfigList.size() == 1) {
            return this.packingResult(gramConfigList);
         }
      }

      if(StringUtils.isNotEmpty(justice)) {
         justice = Tools.deleteDigitDot(justice);
         gramConfigList = this.run(justice, this.thirdPatternList, this.firstInvalidPatternList);
         if(gramConfigList == null || gramConfigList.size() == 0) {
            return new Label();
         }

         FactTextConfig factTextConfig = FactTextSplit.run(justice);
         if(StringUtils.isNotBlank(factTextConfig.header) && StringUtils.isNotEmpty(factTextConfig.header)) {
            gramConfigList = this.run(factTextConfig.header, this.firstPatternList, this.firstInvalidPatternList);
            if(gramConfigList != null && gramConfigList.size() == 1) {
               return this.packingResult(gramConfigList);
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.tail) && StringUtils.isNotEmpty(factTextConfig.tail)) {
            gramConfigList = this.run(factTextConfig.tail, this.firstPatternList, this.firstInvalidPatternList);
            if(gramConfigList != null && gramConfigList.size() == 1) {
               return this.packingResult(gramConfigList);
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
            gramConfigList = this.run(factTextConfig.body, this.thirdPatternList, this.firstInvalidPatternList);
            return this.packingResult(gramConfigList);
         }
      }

      return null;
   }

   public List run(String text, List validRules, List invalidRules) {
      ArrayList gramConfigList = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         List contentList = Arrays.asList(text.split(this.noiseword));
         ArrayList targetContentList = new ArrayList();
         Iterator var7 = contentList.iterator();

         String targetContent;
         while(var7.hasNext()) {
            targetContent = (String)var7.next();
            if(MatchRule.matchRule(targetContent, this.keyword)) {
               targetContentList.add(targetContent);
            }
         }

         if(targetContentList != null && targetContentList.size() > 0) {
            var7 = targetContentList.iterator();

            while(var7.hasNext()) {
               targetContent = (String)var7.next();
               gramConfigList.addAll(GramTools.matchGram(targetContent, validRules, invalidRules, this.gramRatioBasicList));
            }
         }
      }

      return gramConfigList;
   }

   public Label packingResult(List gramConfigList) {
      if(gramConfigList != null && !gramConfigList.isEmpty()) {
         Label label = new Label();
         label.setFlag((long)Integer.parseInt(this.code));
         label.setChiname(this.chiname);
         label.setUsedRegx("");
         label.setValue(String.format("%.3f", new Object[]{Double.valueOf(GramTools.sum(gramConfigList))}));
         return label;
      } else {
         return null;
      }
   }
}
