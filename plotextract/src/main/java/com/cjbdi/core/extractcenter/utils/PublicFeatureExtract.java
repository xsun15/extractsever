package com.cjbdi.core.extractcenter.utils;

import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.utils.Label;
import com.cjbdi.core.extractcenter.utils.CommonTools;
import com.cjbdi.core.extractcenter.utils.LabelExtractor;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PublicFeatureExtract extends LabelExtractor {

   private List positivePattern;
   private List negativePattern;
   private String code;
   private String name;


   public String getCode() {
      return this.code;
   }

   public PublicFeatureExtract(List positivePattern, List negativePattern, String code, String name) {
      this.positivePattern = positivePattern;
      this.negativePattern = negativePattern;
      this.code = code;
      this.name = name;
   }

   public Label doExtract(String lineText) {
      BoolConfig boolConfig = MatchRule.matchPatternBoolConfig(lineText, this.positivePattern, this.negativePattern);
      Label label = null;
      if(boolConfig != null) {
         label = new Label();
         label.setUsedRegx(boolConfig.rule);
         label.setFlag((long)Integer.parseInt(this.code));
         label.setText(boolConfig.colorTarget);
         label.setValue("true");
         label.setChiname(this.name);
      }

      return label;
   }

   public Label doExtract(String defendant, String casecause, Set casecauseList, String content) {
      if(casecause.contains("隐瞒")) {
         casecause = "隐瞒";
      } else if(casecause.contains("毒品")) {
         casecause = "毒品";
      }

      if(content.contains(defendant) && (content.contains(casecause) || casecause.equals("隐瞒") && content.contains("掩饰"))) {
         String str1 = content.substring(0, content.indexOf(defendant));
         String str2 = content.substring(str1.length() + defendant.length());
         if(casecause.equals("隐瞒") && str2.indexOf(casecause) == -1) {
            casecause = "掩饰";
         }

         String str3 = str2.substring(0, str2.indexOf(casecause));
         String str4 = str2.substring(str3.length() + casecause.length());
         Iterator extractResult = casecauseList.iterator();

         while(extractResult.hasNext()) {
            String label = (String)extractResult.next();
            if(str4.contains(label) && !label.equals(casecause)) {
               str4 = str4.substring(0, str4.indexOf(label));
            }
         }

         Map extractResult1 = MatchRule.mathPatternYearMonthDay(str4, this.positivePattern, this.negativePattern);
         Label label1 = null;
         if(!extractResult1.isEmpty()) {
            label1 = new Label();
            String month = CommonTools.convertYear2Month(extractResult1);
            label1.setUsedRegx((String)extractResult1.get("rule"));
            label1.setFlag((long)Integer.parseInt(this.code));
            label1.setText((String)extractResult1.get("text"));
            label1.setValue(month);
            label1.setChiname(this.name);
         }

         return label1;
      } else {
         return null;
      }
   }
}
