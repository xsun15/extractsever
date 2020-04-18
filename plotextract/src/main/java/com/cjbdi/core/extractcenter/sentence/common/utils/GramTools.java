package com.cjbdi.core.extractcenter.sentence.common.utils;

import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.GramRatioBasic;
import com.cjbdi.core.extractcenter.sentence.common.utils.GramConfig;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class GramTools {

   public static List matchGram(String text, List pPatternList, List nPatternList, List gramRatioBasicList) {
      ArrayList gramConfigList = new ArrayList();
      Iterator var5;
      Pattern pattern;
      Matcher matcher;
      if(nPatternList != null && !nPatternList.isEmpty()) {
         var5 = nPatternList.iterator();

         while(var5.hasNext()) {
            pattern = (Pattern)var5.next();
            matcher = pattern.matcher(text);
            if(matcher.find()) {
               text = text.replaceAll(matcher.group(), "");
            }
         }
      }

      if(pPatternList != null && !pPatternList.isEmpty()) {
         var5 = pPatternList.iterator();

         while(var5.hasNext()) {
            pattern = (Pattern)var5.next();
            matcher = pattern.matcher(text);

            while(matcher.find()) {
               GramConfig gramConfig = new GramConfig();
               gramConfig.context = matcher.group();
               gramConfig = matchGram(gramConfig, gramRatioBasicList);
               text = text.replaceAll(matcher.group(), "");
               gramConfigList.add(gramConfig);
            }
         }
      }

      return gramConfigList;
   }

   public static GramConfig matchGram(GramConfig gramConfig, List gramRatioBasicList) {
      Iterator var2 = gramRatioBasicList.iterator();

      while(var2.hasNext()) {
         GramRatioBasic gramRatioBasic = (GramRatioBasic)var2.next();
         if(MatchRule.matchRule(gramConfig.context, gramRatioBasic.rule)) {
            gramConfig.target = MatchRule.matchText(gramConfig.context, gramRatioBasic.rule, 0);
            String value = MatchRule.matchText(gramConfig.context, gramRatioBasic.rule, 1);
            if(value != null && StringUtils.isNotEmpty(value)) {
               gramConfig.value = Double.parseDouble(value) * gramRatioBasic.ratio;
            }
         }
      }

      return gramConfig;
   }

   public static double sum(List gramConfigList) {
      double sum = 0.0D;
      GramConfig gramConfig;
      if(gramConfigList != null && !gramConfigList.isEmpty()) {
         for(Iterator var3 = gramConfigList.iterator(); var3.hasNext(); sum += gramConfig.value) {
            gramConfig = (GramConfig)var3.next();
         }
      }

      return sum;
   }

   public static List replaceHolder(List list, String keyword) {
      ArrayList patternList = new ArrayList();
      if(list != null && !list.isEmpty()) {
         Iterator var3 = list.iterator();

         while(var3.hasNext()) {
            Pattern one = (Pattern)var3.next();
            one = Pattern.compile(one.toString().replaceAll("placeholder", keyword));
            patternList.add(one);
         }

         return patternList;
      } else {
         return list;
      }
   }

   public static String list2Regx(List list) {
      String result = "";
      if(list != null && !list.isEmpty()) {
         result = result + "(";

         String one;
         for(Iterator var2 = list.iterator(); var2.hasNext(); result = result + one + "|") {
            one = (String)var2.next();
         }

         result = result.substring(0, result.length() - 1);
         result = result + ")";
      }

      return result;
   }
}
