package com.cjbdi.core.servercenter.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.extractcenter.sentence.utils.DateTimeExtractor;
import com.cjbdi.core.extractcenter.utils.CleanText;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class Tools {

   public static JSONObject httpRequestParaSplit(String fullText, String docType) {
      JSONObject resultJson = new JSONObject();
      if(StringUtils.isNotEmpty(fullText)) {
         fullText = CleanText.run(fullText);
         JSONObject reqPara = new JSONObject();
         reqPara.put("docType", docType);
         reqPara.put("fullText", fullText);
         String result = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocsplit(), reqPara);
         if(StringUtils.isNotEmpty(result)) {
            resultJson = JSONObject.parseObject(result);
         }
      }

      return resultJson;
   }

   public static JSONObject httpRequestPortrait(String fullText, String docType) {
      JSONObject resultJson = new JSONObject();
      if(StringUtils.isNotEmpty(fullText)) {
         fullText = CleanText.run(fullText);
         JSONObject reqPara = new JSONObject();
         reqPara.put("docType", docType);
         reqPara.put("fullText", fullText);
         String result = HttpRequest.sendPost(BeanFactoryConfig.interfaceConfig.getInterfacePortrait().getDocportray(), reqPara);
         if(StringUtils.isNotEmpty(result)) {
            resultJson = JSONObject.parseObject(result);
         }
      }

      return resultJson;
   }

   public static String extractDocType(String fullText) {
      if(StringUtils.isNotEmpty(fullText)) {
         ArrayList rules = new ArrayList();
         rules.add("审结报告");
         rules.add("审查报告");
         rules.add("刑事判决书");
         rules.add("起诉书");
         rules.add("起诉意见书");
         List fullTextList = Arrays.asList(fullText.split("\n"));
         Iterator var3 = fullTextList.iterator();

         while(var3.hasNext()) {
            String line = (String)var3.next();
            Iterator var5 = rules.iterator();

            while(var5.hasNext()) {
               String rule = (String)var5.next();
               if(MatchRule.matchRule(line, rule)) {
                  return rule;
               }
            }
         }
      }

      return "";
   }

   public static JSONObject packingResult(String status, String message) {
      JSONObject response = new JSONObject();
      response.put("status", status);
      response.put("data", message);
      return response;
   }

   public static JSONObject packingResult(String status, JSONArray message) {
      JSONObject response = new JSONObject();
      response.put("status", status);
      response.put("data", message);
      return response;
   }

   public static Set string2Set(String str) {
      return StringUtils.isNotEmpty(str)?new HashSet(Arrays.asList(str.split(","))):null;
   }

   public static List string2List(String str) {
      return StringUtils.isNotEmpty(str)?Arrays.asList(str.split(",")):null;
   }

   public static Optional extractDate(String text, boolean isFirst) {
      Optional localDate = Optional.empty();
      if (StringUtils.isNotEmpty(text)) {
         if (StringUtils.isNotEmpty(text)) {
            String rule = "\\d{4}年(\\d{1,2}月)?(\\d{1,2}日)?";
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
               String matchText = matcher.group();
               Optional ldt = DateTimeExtractor.extract(matchText);
               if (ldt.isPresent() && isFirst) {
                  return Optional.of(((LocalDateTime) ldt.get()).toLocalDate());
               }

               if (ldt.isPresent()) {
                  localDate = Optional.of(((LocalDateTime) ldt.get()).toLocalDate());
               }
            }
         }
      }
      return localDate;
   }

   public static String deleteDigitDot(String string) {
      string = string.replaceAll(" ", "");
      String regxExp = "\\d[，,]\\d";
      Pattern pattern = Pattern.compile(regxExp);

      String matchedString;
      String matchedString1;
      for(Matcher matcher = pattern.matcher(string); matcher.find(); string = string.replaceAll(matchedString, matchedString1)) {
         matchedString = matcher.group();
         matchedString1 = matchedString.replace("，", "");
         matchedString1 = matchedString1.replace(",", "");
      }

      return string;
   }

   public static String clean(String content) {
      content = content.replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5.，,、。？“”：:*×\\n《》（）Oo〇]+", "");
      String[] contentList = content.split("\n");
      String result = "";
      String[] var3 = contentList;
      int var4 = contentList.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String line = var3[var5];
         if(!"".equals(line.trim().replaceAll("\\s+", ""))) {
            result = result + line + "\n";
         }
      }

      return result.trim();
   }

   public static String list2Str(List list) {
      return list != null && !list.isEmpty()?list.toString().replaceAll("\\[", "").replaceAll("\\]", ""):"";
   }

   public static String normalizing(String content) {
      if(content != null && !content.isEmpty()) {
         String regx = "(\\d+(\\.\\d+)?)(余|多)?(千万|百万|十万|万|千|百)";
         Pattern pattern = Pattern.compile(regx);

         String target;
         String newTarget;
         for(Matcher matcher = pattern.matcher(content); matcher.find(); content = content.replaceFirst(target, newTarget)) {
            target = matcher.group();
            newTarget = target;
            if(!target.contains("元")) {
               newTarget = target + "元";
            }
         }
      }

      return content;
   }
}
