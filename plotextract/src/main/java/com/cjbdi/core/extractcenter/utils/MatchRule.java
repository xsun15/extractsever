package com.cjbdi.core.extractcenter.utils;

import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyRatioBasic;
import com.cjbdi.core.developcenter.utils.CommonTools;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.BoolConfig;
import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.utils.ColorText;
import com.cjbdi.core.extractcenter.utils.ColorTextConfig;
import com.cjbdi.core.extractcenter.utils.ExtractNumber;
import com.cjbdi.core.extractcenter.utils.IsDigit;
import com.cjbdi.core.extractcenter.utils.WordfigureToNumber;
import com.cjbdi.core.servercenter.utils.Tools;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class MatchRule {

   public static Map matchRule(String text, List pruleList, List nruleList) {
      HashMap map = new HashMap();
      Iterator var4 = nruleList.iterator();

      String rule;
      Pattern pattern;
      Matcher matcher;
      while(var4.hasNext()) {
         rule = (String)var4.next();
         pattern = Pattern.compile(rule);
         matcher = pattern.matcher(text);
         if(matcher.find()) {
            text = text.replaceAll(matcher.group(), "");
         }
      }

      var4 = pruleList.iterator();

      do {
         if(!var4.hasNext()) {
            return map;
         }

         rule = (String)var4.next();
         pattern = Pattern.compile(rule);
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      map.put("rule", rule);
      map.put("text", matcher.group());
      return map;
   }

   public static Map matchPattern(String text, List pruleList, List nruleList) {
      HashMap map = new HashMap();
      Iterator var4 = nruleList.iterator();

      Pattern pattern;
      Matcher matcher;
      while(var4.hasNext()) {
         pattern = (Pattern)var4.next();
         matcher = pattern.matcher(text);
         if(matcher.find()) {
            text = text.replaceAll(matcher.group(), "");
         }
      }

      var4 = pruleList.iterator();

      do {
         if(!var4.hasNext()) {
            return map;
         }

         pattern = (Pattern)var4.next();
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      map.put("rule", pattern.toString());
      map.put("text", matcher.group());
      return map;
   }

   public static boolean isContainNR(String text) {
      if(text != null && !text.isEmpty()) {
         Segment segment = HanLP.newSegment();
         List termList = segment.seg(text);
         Iterator var3 = termList.iterator();

         while(var3.hasNext()) {
            Term term = (Term)var3.next();
            if(term.nature.name().equals("nr")) {
               return true;
            }
         }
      }

      return false;
   }

   public static BoolConfig matchRuleBoolConfig(String text, List pruleList, List nruleList) {
      Iterator var3;
      String rule;
      Pattern pattern;
      Matcher matcher;
      if(text != null && !text.isEmpty()) {
         var3 = nruleList.iterator();

         while(var3.hasNext()) {
            rule = (String)var3.next();
            pattern = Pattern.compile(rule);
            matcher = pattern.matcher(text);
            if(matcher.find()) {
               text = text.replaceAll(matcher.group(), "");
            }
         }
      }

      if(text != null && !text.isEmpty()) {
         var3 = pruleList.iterator();

         while(var3.hasNext()) {
            rule = (String)var3.next();
            pattern = Pattern.compile(rule);
            matcher = pattern.matcher(text);
            if(matcher.find()) {
               BoolConfig boolConfig = new BoolConfig();
               boolConfig.rule = rule;
               boolConfig.target = matcher.group();
               boolConfig.startcolor = text.indexOf(boolConfig.target);
               boolConfig.endcolor = boolConfig.startcolor + boolConfig.target.length();
               ColorTextConfig colorTextConfig = new ColorTextConfig();
               colorTextConfig.text = text;
               colorTextConfig.effectText = boolConfig.startcolor + "," + boolConfig.endcolor + ";";
               ColorText colorText = new ColorText();
               boolConfig.colorTarget = colorText.run(colorTextConfig);
               return boolConfig;
            }
         }
      }

      return null;
   }

   public static BoolConfig matchPatternBoolConfig(String text, List pruleList, List nruleList) {
      Iterator var3;
      Pattern pattern;
      Matcher matcher;
      if(text != null && !text.isEmpty()) {
         var3 = nruleList.iterator();

         while(var3.hasNext()) {
            pattern = (Pattern)var3.next();
            matcher = pattern.matcher(text);
            if(matcher.find()) {
               text = text.replaceAll(matcher.group(), CommonTools.copystr("x", matcher.group().length()));
            }
         }
      }

      if(text != null && !text.isEmpty()) {
         var3 = pruleList.iterator();
         while(var3.hasNext()) {
            pattern = (Pattern)var3.next();
            matcher = pattern.matcher(text);
            if(matcher.find()) {
               BoolConfig boolConfig = new BoolConfig();
               boolConfig.rule = pattern.toString();
               boolConfig.target = matcher.group();
               boolConfig.startcolor = text.indexOf(boolConfig.target);
               boolConfig.endcolor = boolConfig.startcolor + boolConfig.target.length();
               ColorTextConfig colorTextConfig = new ColorTextConfig();
               colorTextConfig.text = text;
               colorTextConfig.effectText = boolConfig.startcolor + "," + boolConfig.endcolor + ";";
               ColorText colorText = new ColorText();
               boolConfig.colorTarget = colorText.run(colorTextConfig);
               return boolConfig;
            }
         }
      }

      return null;
   }

   public static BoolConfig matchRuleBoolConfig(String text, List pruleList) {
      if(pruleList != null && !pruleList.isEmpty()) {
         Iterator var2 = pruleList.iterator();

         while(var2.hasNext()) {
            String rule = (String)var2.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               BoolConfig boolConfig = new BoolConfig();
               boolConfig.rule = rule;
               boolConfig.target = matcher.group();
               boolConfig.startcolor = text.indexOf(boolConfig.target);
               boolConfig.endcolor = boolConfig.startcolor + boolConfig.target.length();
               ColorTextConfig colorTextConfig = new ColorTextConfig();
               colorTextConfig.text = text;
               colorTextConfig.effectText = boolConfig.startcolor + "," + boolConfig.endcolor + ";";
               ColorText colorText = new ColorText();
               boolConfig.colorTarget = colorText.run(colorTextConfig);
               return boolConfig;
            }
         }
      }

      return null;
   }

   public static BoolConfig matchPatternBoolConfig(String text, List pruleList) {
      if(pruleList != null && !pruleList.isEmpty()) {
         Iterator var2 = pruleList.iterator();

         while(var2.hasNext()) {
            Pattern pattern = (Pattern)var2.next();
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               BoolConfig boolConfig = new BoolConfig();
               boolConfig.rule = pattern.toString();
               boolConfig.target = matcher.group();
               boolConfig.startcolor = text.indexOf(boolConfig.target);
               boolConfig.endcolor = boolConfig.startcolor + boolConfig.target.length();
               ColorTextConfig colorTextConfig = new ColorTextConfig();
               colorTextConfig.text = text;
               colorTextConfig.effectText = boolConfig.startcolor + "," + boolConfig.endcolor + ";";
               ColorText colorText = new ColorText();
               boolConfig.colorTarget = colorText.run(colorTextConfig);
               return boolConfig;
            }
         }
      }

      return null;
   }

   public static BoolConfig matchRuleBoolConfig(String target, String text) {
      if(text != null && !text.isEmpty() && target != null && !target.isEmpty()) {
         BoolConfig boolConfig = new BoolConfig();
         boolConfig.target = target;
         boolConfig.startcolor = text.indexOf(boolConfig.target);
         boolConfig.endcolor = boolConfig.startcolor + boolConfig.target.length();
         ColorTextConfig colorTextConfig = new ColorTextConfig();
         colorTextConfig.text = text;
         colorTextConfig.effectText = boolConfig.startcolor + "," + boolConfig.endcolor + ";";
         ColorText colorText = new ColorText();
         boolConfig.colorTarget = colorText.run(colorTextConfig);
         return boolConfig;
      } else {
         return null;
      }
   }

   public static NumberConfig matchRuleNumberConfig(String text, List pruleList, List nruleList) {
      Iterator var3 = nruleList.iterator();

      String rule;
      Pattern pattern;
      Matcher matcher;
      while(var3.hasNext()) {
         rule = (String)var3.next();
         pattern = Pattern.compile(rule);
         matcher = pattern.matcher(text);
         if(matcher.find()) {
            text = text.replaceAll(matcher.group(), "");
         }
      }

      var3 = pruleList.iterator();

      do {
         if(!var3.hasNext()) {
            return null;
         }

         rule = (String)var3.next();
         pattern = Pattern.compile(rule);
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      NumberConfig numberConfig = new NumberConfig();
      numberConfig.rule = rule;
      numberConfig.target = matcher.group();
      numberConfig.startcolor = text.indexOf(numberConfig.target);
      numberConfig.endcolor = numberConfig.startcolor + numberConfig.target.length();
      numberConfig.colorTarget = numberConfig.startcolor + "," + numberConfig.endcolor + ";";
      return numberConfig;
   }

   public static NumberConfig matchPatternNumberConfig(String text, List pruleList, List nruleList) {
      if(text != null && !text.isEmpty()) {
         Iterator var3;
         Pattern pattern;
         Matcher matcher;
         if(nruleList != null && !nruleList.isEmpty()) {
            var3 = nruleList.iterator();

            while(var3.hasNext()) {
               pattern = (Pattern)var3.next();
               matcher = pattern.matcher(text);
               if(matcher.find()) {
                  text = text.replaceAll(matcher.group(), "");
               }
            }
         }

         if(pruleList != null && !pruleList.isEmpty()) {
            var3 = pruleList.iterator();

            while(var3.hasNext()) {
               pattern = (Pattern)var3.next();
               matcher = pattern.matcher(text);
               if(matcher.find()) {
                  NumberConfig numberConfig = new NumberConfig();
                  numberConfig.rule = pattern.toString();
                  numberConfig.target = matcher.group();
                  numberConfig.startcolor = text.indexOf(numberConfig.target);
                  numberConfig.endcolor = numberConfig.startcolor + numberConfig.target.length();
                  numberConfig.colorTarget = numberConfig.startcolor + "," + numberConfig.endcolor + ";";
                  return numberConfig;
               }
            }
         }
      }

      return null;
   }

   public static NumberConfig matchRuleShortSentence(String text, List pruleList, List nruleList) {
      if(StringUtils.isNotEmpty(text)) {
         Iterator var3 = nruleList.iterator();

         String rule;
         Pattern pattern;
         Matcher matcher;
         while(var3.hasNext()) {
            rule = (String)var3.next();
            pattern = Pattern.compile(rule);
            matcher = pattern.matcher(text);
            if(matcher.find()) {
               text = text.replaceAll(matcher.group(), "");
            }
         }

         var3 = pruleList.iterator();

         while(var3.hasNext()) {
            rule = (String)var3.next();
            pattern = Pattern.compile(rule);
            matcher = pattern.matcher(text);
            if(matcher.find()) {
               String target = matcher.group();
               NumberConfig numberConfig = new NumberConfig();
               numberConfig.target = target;
               numberConfig.rule = rule;
               numberConfig.startcolor = text.indexOf(target);
               numberConfig.endcolor = numberConfig.startcolor + target.length();
               numberConfig.colorTarget = numberConfig.startcolor + "," + numberConfig.endcolor + ";";
               return numberConfig;
            }
         }
      }

      return null;
   }

   public static List matchTextList(String text, List pruleList, List nruleList) {
      if(StringUtils.isNotEmpty(text)) {
         Iterator numberConfigList = nruleList.iterator();

         while(numberConfigList.hasNext()) {
            String rule = (String)numberConfigList.next();
            Pattern rule1 = Pattern.compile(rule);
            Matcher pattern = rule1.matcher(text);
            if(pattern.find()) {
               text = text.replaceAll(pattern.group(), "");
            }
         }

         ArrayList numberConfigList1 = new ArrayList();
         Iterator rule2 = pruleList.iterator();

         while(rule2.hasNext()) {
            String rule3 = (String)rule2.next();
            Pattern pattern1 = Pattern.compile(rule3);
            Matcher matcher = pattern1.matcher(text);
            if(matcher.find()) {
               String target = matcher.group();
               NumberConfig numberConfig = new NumberConfig();
               numberConfig.target = target;
               numberConfig.rule = rule3;
               numberConfig.startcolor = text.indexOf(target);
               numberConfig.endcolor = numberConfig.startcolor + target.length();
               numberConfig.colorTarget = numberConfig.startcolor + "," + numberConfig.endcolor + ";";
               numberConfigList1.add(numberConfig);
            }
         }

         return numberConfigList1;
      } else {
         return null;
      }
   }

   public static List matchTextListPattern(String text, List pruleList, List nruleList) {
      if(text != null && !text.isEmpty()) {
         if(nruleList != null && !nruleList.isEmpty()) {
            Iterator numberConfigList = nruleList.iterator();

            while(numberConfigList.hasNext()) {
               Pattern pattern = (Pattern)numberConfigList.next();
               Matcher pattern1 = pattern.matcher(text);
               if(pattern1.find()) {
                  text = text.replaceAll(pattern1.group(), "");
               }
            }
         }

         if(pruleList != null && !pruleList.isEmpty()) {
            ArrayList numberConfigList1 = new ArrayList();
            Iterator pattern2 = pruleList.iterator();

            while(pattern2.hasNext()) {
               Pattern pattern3 = (Pattern)pattern2.next();
               Matcher matcher = pattern3.matcher(text);

               while(matcher.find()) {
                  String target = matcher.group();
                  NumberConfig numberConfig = new NumberConfig();
                  numberConfig.target = target;
                  numberConfig.rule = pattern3.toString();
                  numberConfig.startcolor = text.indexOf(target);
                  numberConfig.endcolor = numberConfig.startcolor + target.length();
                  numberConfig.colorTarget = numberConfig.startcolor + "," + numberConfig.endcolor + ";";
                  numberConfigList1.add(numberConfig);
               }
            }

            return numberConfigList1;
         }
      }

      return null;
   }

   public static Map mathYearMonthDay(String text, List pruleList, List nruleList) {
      HashMap map = new HashMap();
      if(text != null && !text.isEmpty()) {
         Iterator var4;
         String rule;
         Pattern pattern;
         Matcher matcher;
         if(nruleList != null && !nruleList.isEmpty()) {
            var4 = nruleList.iterator();

            while(var4.hasNext()) {
               rule = (String)var4.next();
               pattern = Pattern.compile(rule);
               matcher = pattern.matcher(text);
               if(matcher.find()) {
                  text = text.replaceAll(matcher.group(), "");
               }
            }
         }

         if(pruleList != null && !pruleList.isEmpty()) {
            var4 = pruleList.iterator();

            while(var4.hasNext()) {
               rule = (String)var4.next();
               pattern = Pattern.compile(rule);
               matcher = pattern.matcher(text);
               if(matcher.find()) {
                  map.put("text", matcher.group());
                  map.put("rule", rule);
                  if(matcher.groupCount() == 6) {
                     map.put("year", matcher.group(2));
                     map.put("month", matcher.group(4));
                     map.put("day", matcher.group(6));
                  } else if(matcher.groupCount() == 9) {
                     map.put("亿", matcher.group(1));
                     map.put("千万", matcher.group(2));
                     map.put("百万", matcher.group(3));
                     map.put("十万", matcher.group(4));
                     map.put("万", matcher.group(5));
                     map.put("千", matcher.group(6));
                     map.put("百", matcher.group(7));
                     map.put("十", matcher.group(8));
                     map.put("个", matcher.group(9));
                  }

                  return map;
               }
            }
         }
      }

      return map;
   }

   public static Map mathPatternYearMonthDay(String text, List pruleList, List nruleList) {
      HashMap map = new HashMap();
      if(text != null && !text.isEmpty()) {
         Iterator var4;
         Pattern pattern;
         Matcher matcher;
         if(nruleList != null && !nruleList.isEmpty()) {
            var4 = nruleList.iterator();

            while(var4.hasNext()) {
               pattern = (Pattern)var4.next();
               matcher = pattern.matcher(text);
               if(matcher.find()) {
                  text = text.replaceAll(matcher.group(), "");
               }
            }
         }

         if(pruleList != null && !pruleList.isEmpty()) {
            var4 = pruleList.iterator();

            while(var4.hasNext()) {
               pattern = (Pattern)var4.next();
               matcher = pattern.matcher(text);
               if(matcher.find()) {
                  map.put("text", matcher.group());
                  map.put("rule", pattern.toString());
                  if(matcher.groupCount() == 6) {
                     map.put("year", matcher.group(2));
                     map.put("month", matcher.group(4));
                     map.put("day", matcher.group(6));
                  } else if(matcher.groupCount() == 9) {
                     map.put("亿", matcher.group(1));
                     map.put("千万", matcher.group(2));
                     map.put("百万", matcher.group(3));
                     map.put("十万", matcher.group(4));
                     map.put("万", matcher.group(5));
                     map.put("千", matcher.group(6));
                     map.put("百", matcher.group(7));
                     map.put("十", matcher.group(8));
                     map.put("个", matcher.group(9));
                  }

                  return map;
               }
            }
         }
      }

      return map;
   }

   public static Map matchRuleGroup1(String text, List pruleList, List nruleList) {
      HashMap map = new HashMap();
      Iterator var4 = nruleList.iterator();

      Matcher matcher;
      do {
         String rule;
         Pattern pattern;
         if(!var4.hasNext()) {
            var4 = pruleList.iterator();

            do {
               if(!var4.hasNext()) {
                  return map;
               }

               rule = (String)var4.next();
               pattern = Pattern.compile(rule);
               matcher = pattern.matcher(text);
            } while(!matcher.find());

            map.put("rule", rule);
            map.put("text", matcher.group(1));
            return map;
         }

         rule = (String)var4.next();
         pattern = Pattern.compile(rule);
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      return map;
   }

   public static Map matchRule(String text, List ruleList) {
      HashMap map = new HashMap();
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var3 = ruleList.iterator();

         while(var3.hasNext()) {
            String rule = (String)var3.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               map.put("rule", rule);
               map.put("text", matcher.group());
               return map;
            }
         }
      }

      return map;
   }

   public static Map matchPattern(String text, List ruleList) {
      HashMap map = new HashMap();
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var3 = ruleList.iterator();

         while(var3.hasNext()) {
            Pattern pattern = (Pattern)var3.next();
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               map.put("rule", pattern.toString());
               map.put("text", matcher.group());
               return map;
            }
         }
      }

      return map;
   }

   public static boolean matchRulesBool(String text, List ruleList) {
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var2 = ruleList.iterator();

         while(var2.hasNext()) {
            String rule = (String)var2.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean matchPatternsBool(String text, List ruleList) {
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var2 = ruleList.iterator();

         while(var2.hasNext()) {
            Pattern pattern = (Pattern)var2.next();
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               return true;
            }
         }
      }

      return false;
   }

   public static int matchStartPosition(String text, List ruleList) {
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var2 = ruleList.iterator();

         while(var2.hasNext()) {
            String rule = (String)var2.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               return text.indexOf(matcher.group());
            }
         }
      }

      return -1;
   }

   public static int matchStartPositionPattern(String text, List ruleList) {
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var2 = ruleList.iterator();

         while(var2.hasNext()) {
            Pattern pattern = (Pattern)var2.next();
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               return text.indexOf(matcher.group());
            }
         }
      }

      return -1;
   }

   public static String matchText(String text, List ruleList, int part) {
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var3 = ruleList.iterator();

         while(var3.hasNext()) {
            String rule = (String)var3.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               String ret = "";

               try {
                  ret = matcher.group(part);
               } catch (Exception var9) {
                  ret = matcher.group();
               }

               return ret;
            }
         }
      }

      return null;
   }

   public static String matchTextPattern(String text, List ruleList, int part) {
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var3 = ruleList.iterator();

         while(var3.hasNext()) {
            Pattern pattern = (Pattern)var3.next();
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               String ret = "";

               try {
                  ret = matcher.group(part);
               } catch (Exception var8) {
                  ret = matcher.group();
               }

               return ret;
            }
         }
      }

      return null;
   }

   public static String matchTextPattern(String text, List ruleList) {
      if(text != null && !text.isEmpty() && ruleList != null && !ruleList.isEmpty()) {
         Iterator var2 = ruleList.iterator();

         while(var2.hasNext()) {
            Pattern pattern = (Pattern)var2.next();
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               String ret = matcher.group();
               return ret;
            }
         }
      }

      return null;
   }

   public static String matchText(String text, String rule, int part) {
      if(text != null && !text.isEmpty() && rule != null && !rule.isEmpty()) {
         Pattern pattern = Pattern.compile(rule);
         Matcher matcher = pattern.matcher(text);
         if(matcher.find()) {
            String ret = "";

            try {
               ret = matcher.group(part);
            } catch (Exception var7) {
               ret = matcher.group();
            }

            return ret;
         }
      }

      return null;
   }

   public static Map matchMoney(String text, LinkedHashMap ruleList) {
      double sum = 0.0D;
      HashMap result = new HashMap();
      ArrayList targetList = new ArrayList();
      Iterator var6 = ruleList.keySet().iterator();

      while(var6.hasNext()) {
         String rule = (String)var6.next();
         double ratio = Double.parseDouble((String)ruleList.get(rule));
         Pattern pattern = Pattern.compile(rule);

         for(Matcher matcher = pattern.matcher(text); matcher.find(); text = text.replace(matcher.group(), "")) {
            double value = Double.parseDouble(matcher.group(1));
            targetList.add(matcher.group(1));
            sum += value * ratio;
         }
      }

      if(sum > 0.001D) {
         result.put("value", String.valueOf((new Double(sum)).intValue()));
         result.put("text", targetList.toString());
         return result;
      } else {
         return null;
      }
   }

   public static MoneyConfig matchTotalMoney(String text, String target, List ruleList) {
      double sum = 0.0D;
      MoneyConfig moneyConfig = new MoneyConfig();
      Iterator var6 = ruleList.iterator();

      while(var6.hasNext()) {
         MoneyRatioBasic moneyRatioBasic = (MoneyRatioBasic)var6.next();
         String rule = moneyRatioBasic.rule;
         double ratio = moneyRatioBasic.ratio;
         Pattern pattern = Pattern.compile(rule);
         Matcher matcher = pattern.matcher(target);
         if(matcher.find()) {
            String matchmoney = matcher.group(1);
            if(IsDigit.isDouble(matchmoney)) {
               double value = Double.parseDouble(matchmoney);
               sum = value * ratio;
               int start = text.indexOf(target) + target.indexOf(matchmoney);
               int end = start + matchmoney.length();
               moneyConfig.value = sum;
               moneyConfig.start = start;
               moneyConfig.end = end;
               moneyConfig.target = matchmoney;
               moneyConfig.context = target;
               List sentenceList = Arrays.asList(text.split("(，|。|；|:|、)"));
               Iterator approximate = sentenceList.iterator();

               while(approximate.hasNext()) {
                  String pattern1 = (String)approximate.next();
                  if(pattern1.contains(target)) {
                     moneyConfig.context = pattern1;
                     moneyConfig.sentence = pattern1;
                     break;
                  }
               }

               String approximate1 = "(余|多|不足|以上)";
               Pattern pattern11 = Pattern.compile(approximate1);
               Matcher matcher1 = pattern11.matcher(target);
               if(matcher1.find()) {
                  moneyConfig.isaccurate = "约数";
               }

               return moneyConfig;
            }
         }
      }

      return moneyConfig;
   }

   public static List matchInvalid(String text, List invalidRules) {
      ArrayList moneyConfigs = new ArrayList();
      if(invalidRules != null && invalidRules.size() > 0) {
         int count = 1;
         Iterator var4 = invalidRules.iterator();

         while(var4.hasNext()) {
            String rule = (String)var4.next();
            Pattern pattern = Pattern.compile(rule);

            for(Matcher matcher = pattern.matcher(text); matcher.find(); ++count) {
               String target = matcher.group();
               MoneyConfig moneyConfig = new MoneyConfig();
               moneyConfig.sentence = target;
               moneyConfig.startColor = text.indexOf(target);
               moneyConfig.endColor = moneyConfig.startColor + target.length();
               moneyConfigs.add(moneyConfig);
               text = text.replaceFirst(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()));
               matcher = pattern.matcher(text);
            }
         }
      }

      return moneyConfigs;
   }

   public static List matchMoney(String text, List moneyRatioBasics) {
      ArrayList moneyConfigs = new ArrayList();
      if(moneyRatioBasics != null && moneyRatioBasics.size() > 0 && StringUtils.isNotEmpty(text)) {
         byte count = 1;
         Iterator var4 = moneyRatioBasics.iterator();

         while(var4.hasNext()) {
            MoneyRatioBasic moneyRatioBasic = (MoneyRatioBasic)var4.next();
            String rule = moneyRatioBasic.rule;
            double ratio = moneyRatioBasic.ratio;
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);

            while(matcher.find()) {
               MoneyConfig moneyConfig = new MoneyConfig();
               String target = matcher.group();
               String rawNumber = matcher.group(1);
               String number = ExtractNumber.run(WordfigureToNumber.run(rawNumber));
               int start = text.indexOf(target) + target.indexOf(rawNumber);
               int end = start + rawNumber.length();
               moneyConfig.value = Double.parseDouble(number) * ratio;
               moneyConfig.start = start;
               moneyConfig.end = end;
               moneyConfig.startRegx = text.indexOf(target);
               moneyConfig.endRegx = moneyConfig.startRegx + target.length();
               moneyConfig.context = target;
               moneyConfig.target = rawNumber;
               moneyConfig.indexinfact = count;
               moneyConfig.ratio = ratio;
               List sentenceList = Arrays.asList(text.split("(，|；|。|（|）)"));
               Iterator var18 = sentenceList.iterator();

               while(true) {
                  String sentence;
                  if(var18.hasNext()) {
                     sentence = (String)var18.next();
                     if(!sentence.contains(target)) {
                        continue;
                     }

                     moneyConfig.startColor = text.indexOf(sentence) + sentence.indexOf(target);
                     moneyConfig.endColor = moneyConfig.startColor + target.length();
                     moneyConfig.sentence = sentence;
                     sentence = sentence.replaceFirst(moneyConfig.target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 6));
                     Iterator approximate = moneyRatioBasics.iterator();

                     while(approximate.hasNext()) {
                        MoneyRatioBasic pattern1 = (MoneyRatioBasic)approximate.next();
                        String matcher1 = pattern1.rule;
                        Pattern pattern11 = Pattern.compile(matcher1);
                        Matcher matcher11 = pattern11.matcher(sentence);

                        while(matcher11.find()) {
                           String rawNumber1 = matcher11.group(1);
                           if(!rawNumber1.equals(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 6))) {
                              sentence = sentence.replaceFirst(rawNumber1, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", 6));
                           }
                        }
                     }

                     moneyConfig.oneMoneySentence = sentence;
                     String approximate1 = "(余|多|不足|以上)";
                     Pattern pattern12 = Pattern.compile(approximate1);
                     Matcher matcher12 = pattern12.matcher(moneyConfig.sentence);
                     if(matcher12.find()) {
                        moneyConfig.isaccurate = "约数";
                     }
                  }

                  sentenceList = Arrays.asList(text.split("。"));
                  var18 = sentenceList.iterator();

                  while(var18.hasNext()) {
                     sentence = (String)var18.next();
                     if(sentence.contains(target)) {
                        moneyConfig.longSentence = sentence;
                        break;
                     }
                  }

                  text = text.replaceFirst(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()));
                  moneyConfigs.add(moneyConfig);
                  break;
               }
            }
         }
      }

      return moneyConfigs;
   }

   public static List matchMoneyForCombine(String text, List moneyRatioBasics) {
      ArrayList moneyConfigs = new ArrayList();
      if(moneyRatioBasics != null && moneyRatioBasics.size() > 0 && StringUtils.isNotEmpty(text)) {
         byte count = 1;
         Iterator var4 = moneyRatioBasics.iterator();

         while(var4.hasNext()) {
            MoneyRatioBasic moneyRatioBasic = (MoneyRatioBasic)var4.next();
            String rule = moneyRatioBasic.rule;
            double ratio = moneyRatioBasic.ratio;
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);

            while(matcher.find()) {
               MoneyConfig moneyConfig = new MoneyConfig();
               String target = matcher.group();
               String rawNumber = matcher.group(1);
               String number = ExtractNumber.run(WordfigureToNumber.run(rawNumber));
               int start = text.indexOf(target) + target.indexOf(rawNumber);
               int end = start + rawNumber.length();
               moneyConfig.value = Double.parseDouble(number) * ratio;
               moneyConfig.start = start;
               moneyConfig.end = end;
               moneyConfig.context = target;
               moneyConfig.target = rawNumber;
               moneyConfig.indexinfact = count;
               moneyConfig.ratio = ratio;
               List sentenceList = Arrays.asList(text.split("(。|；)"));
               Iterator var18 = sentenceList.iterator();

               while(true) {
                  String sentence;
                  if(var18.hasNext()) {
                     sentence = (String)var18.next();
                     if(!sentence.contains(target)) {
                        continue;
                     }

                     moneyConfig.startColor = text.indexOf(sentence);
                     moneyConfig.endColor = moneyConfig.startColor + sentence.length();
                     sentence = sentence.replaceFirst(moneyConfig.target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10));
                     List sentenceListSub = Arrays.asList(sentence.split("，"));
                     int count1 = 0;
                     Iterator approximate = moneyRatioBasics.iterator();

                     while(approximate.hasNext()) {
                        MoneyRatioBasic pattern1 = (MoneyRatioBasic)approximate.next();
                        String matcher1 = pattern1.rule;
                        Pattern pattern11 = Pattern.compile(matcher1);
                        Matcher matcher11 = pattern11.matcher(sentence);

                        while(matcher11.find()) {
                           String targetsub = matcher11.group();
                           if(!targetsub.contains("8888888888")) {
                              Iterator var28 = sentenceListSub.iterator();

                              while(var28.hasNext()) {
                                 String shortsentence = (String)var28.next();
                                 if(shortsentence.contains(targetsub) && !shortsentence.contains(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10))) {
                                    if(sentence.indexOf(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10)) >= sentence.indexOf(targetsub)) {
                                       moneyConfig.sentence = sentence.substring(sentence.indexOf(shortsentence) + shortsentence.length());
                                    } else if(sentence.indexOf(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10)) < sentence.indexOf(targetsub)) {
                                       moneyConfig.sentence = sentence.substring(0, sentence.indexOf(shortsentence));
                                    }
                                 } else if(shortsentence.contains(targetsub) && shortsentence.contains(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10))) {
                                    if(sentence.indexOf(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10)) < sentence.indexOf(targetsub)) {
                                       moneyConfig.sentence = sentence.substring(0, sentence.indexOf(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10)) + com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10).length());
                                    }
                                 } else if(!shortsentence.contains(targetsub) && shortsentence.contains(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10))) {
                                    moneyConfig.sentence = shortsentence;
                                 }
                              }

                              ++count1;
                           }
                        }
                     }

                     if(count1 == 1) {
                        moneyConfig.sentence = sentence;
                     }

                     if(sentence.contains("8888888888")) {
                        start = 0;
                        approximate = sentenceListSub.iterator();

                        while(approximate.hasNext()) {
                           String var31 = (String)approximate.next();
                           if(var31.contains("8888888888")) {
                              start = sentence.indexOf(var31) + var31.length();
                           }
                        }

                        if(start < moneyConfig.sentence.length()) {
                           moneyConfig.sentence = moneyConfig.sentence.substring(start);
                        }
                     }

                     moneyConfig.oneMoneySentence = sentence;
                     String var30 = "(余|多|不足|以上)";
                     Pattern var32 = Pattern.compile(var30);
                     Matcher var33 = var32.matcher(moneyConfig.sentence);
                     if(var33.find()) {
                        moneyConfig.isaccurate = "约数";
                     }
                  }

                  sentenceList = Arrays.asList(text.split("。"));
                  var18 = sentenceList.iterator();

                  while(var18.hasNext()) {
                     sentence = (String)var18.next();
                     if(sentence.contains(target)) {
                        moneyConfig.longSentence = sentence;
                        break;
                     }
                  }

                  text = text.replaceFirst(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("8888888888元", 1));
                  moneyConfigs.add(moneyConfig);
                  break;
               }
            }
         }
      }

      return moneyConfigs;
   }

   public static List matchMoneyTrain(String text, List moneyRatioBasics) {
      ArrayList moneyConfigs = new ArrayList();
      if(moneyRatioBasics != null && moneyRatioBasics.size() > 0 && StringUtils.isNotEmpty(text)) {
         byte count = 1;
         Iterator var4 = moneyRatioBasics.iterator();

         while(var4.hasNext()) {
            MoneyRatioBasic moneyRatioBasic = (MoneyRatioBasic)var4.next();
            String rule = moneyRatioBasic.rule;
            double ratio = moneyRatioBasic.ratio;
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);

            while(matcher.find()) {
               MoneyConfig moneyConfig = new MoneyConfig();
               String target = matcher.group();
               String rawNumber = matcher.group(1);
               String number = ExtractNumber.run(WordfigureToNumber.run(rawNumber));
               int start = text.indexOf(target) + target.indexOf(rawNumber);
               int end = start + rawNumber.length();
               moneyConfig.value = Double.parseDouble(number) * ratio;
               moneyConfig.start = start;
               moneyConfig.end = end;
               moneyConfig.context = target;
               moneyConfig.target = rawNumber;
               moneyConfig.indexinfact = count;
               List sentenceList = Arrays.asList(text.split("(，|。|；)"));
               Iterator var18 = sentenceList.iterator();

               while(true) {
                  if(var18.hasNext()) {
                     String sentence = (String)var18.next();
                     if(!sentence.contains(target)) {
                        continue;
                     }

                     moneyConfig.startColor = text.indexOf(sentence);
                     moneyConfig.endColor = moneyConfig.startColor + sentence.length();
                     sentence = sentence.replaceFirst(moneyConfig.target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10));
                     Iterator var20 = moneyRatioBasics.iterator();

                     while(var20.hasNext()) {
                        MoneyRatioBasic moneyRatioBasic1 = (MoneyRatioBasic)var20.next();
                        String rule1 = moneyRatioBasic1.rule;
                        Pattern pattern1 = Pattern.compile(rule1);
                        Matcher matcher1 = pattern1.matcher(sentence);

                        while(matcher1.find()) {
                           String rawNumber1 = matcher1.group(1);
                           if(!rawNumber1.equals(com.cjbdi.core.developcenter.utils.CommonTools.copystr("6", 10))) {
                              sentence = sentence.replaceFirst(rawNumber1, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", 10));
                           }
                        }
                     }

                     moneyConfig.sentence = sentence;
                  }

                  text = text.replaceFirst(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()));
                  moneyConfigs.add(moneyConfig);
                  break;
               }
            }
         }
      }

      return moneyConfigs;
   }

   public static List matchMoneyPosition(String text, List ruleList) {
      ArrayList result = new ArrayList();
      List textList = Arrays.asList(text.split("\n"));
      int count = 0;
      int offset = 0;

      for(Iterator var6 = textList.iterator(); var6.hasNext(); ++count) {
         String line = (String)var6.next();
         Iterator var9 = ruleList.iterator();

         while(var9.hasNext()) {
            MoneyRatioBasic moneyRatioBasic = (MoneyRatioBasic)var9.next();
            String rule = moneyRatioBasic.rule;
            double ratio = moneyRatioBasic.ratio;
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(line);

            while(matcher.find()) {
               MoneyConfig moneyConfig = new MoneyConfig();
               String target = matcher.group();
               String rawNumber = matcher.group(1);
               String number = ExtractNumber.run(WordfigureToNumber.run(rawNumber));
               int start = offset + line.indexOf(target) + target.indexOf(rawNumber);
               int end = start + rawNumber.length();
               moneyConfig.value = Double.parseDouble(number) * ratio;
               moneyConfig.start = start;
               moneyConfig.end = end;
               moneyConfig.context = target;
               moneyConfig.target = rawNumber;
               moneyConfig.indexinfact = count;
               List sentenceList = Arrays.asList(text.split("(，|。|；)"));
               Iterator var23 = sentenceList.iterator();

               while(true) {
                  if(var23.hasNext()) {
                     String sentence = (String)var23.next();
                     if(!sentence.contains(target)) {
                        continue;
                     }

                     moneyConfig.sentence = sentence;
                     moneyConfig.startColor = text.indexOf(sentence);
                     moneyConfig.endColor = moneyConfig.startColor + sentence.length();
                  }

                  line = line.replaceFirst(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()));
                  result.add(moneyConfig);
                  break;
               }
            }
         }

         offset += line.length();
      }

      return result;
   }

   public static boolean matchRule(String text, String rule) {
      Pattern pattern = Pattern.compile(rule);
      Matcher matcher = pattern.matcher(text);
      return matcher.find();
   }

   public static boolean IsMatch(String text, List rules) {
      Iterator var2 = rules.iterator();

      Matcher matcher;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         String rule = (String)var2.next();
         Pattern pattern = Pattern.compile(rule);
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      return true;
   }

   public static boolean IsMatchPattern(String text, List rules) {
      Iterator var2 = rules.iterator();

      Matcher matcher;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         Pattern pattern = (Pattern)var2.next();
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      return true;
   }

   public static boolean IsMatchMoney(String text, List rules) {
      Iterator var2 = rules.iterator();

      Matcher matcher;
      do {
         if(!var2.hasNext()) {
            return false;
         }

         MoneyRatioBasic moneyRatioBasic = (MoneyRatioBasic)var2.next();
         Pattern pattern = Pattern.compile(moneyRatioBasic.rule);
         matcher = pattern.matcher(text);
      } while(!matcher.find());

      return true;
   }

   public static int matchNum(String text, List rules) {
      if(rules != null && rules.size() > 0) {
         int count = 0;
         Iterator var3 = rules.iterator();

         while(var3.hasNext()) {
            MoneyRatioBasic moneyRatioBasic = (MoneyRatioBasic)var3.next();
            Pattern pattern = Pattern.compile(moneyRatioBasic.rule);

            for(Matcher matcher = pattern.matcher(text); matcher.find(); ++count) {
               String target = matcher.group();
               text = text.replaceFirst(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()));
            }
         }

         return count;
      } else {
         return 0;
      }
   }

   public static boolean IsMatch(String text, String rule) {
      Pattern pattern = Pattern.compile(rule);
      Matcher matcher = pattern.matcher(text);
      return matcher.find();
   }

   public static String cleanMatchText(String text, List ruleList) {
      Iterator var2 = ruleList.iterator();

      while(var2.hasNext()) {
         String rule = (String)var2.next();
         Pattern pattern = Pattern.compile(rule);

         String target;
         for(Matcher matcher = pattern.matcher(text); matcher.find(); text = text.replace(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()))) {
            target = matcher.group();
         }
      }

      return text;
   }

   public static String cleanMatchTextPattern(String text, List ruleList) {
      Iterator var2 = ruleList.iterator();

      while(var2.hasNext()) {
         Pattern pattern = (Pattern)var2.next();

         String target;
         for(Matcher matcher = pattern.matcher(text); matcher.find(); text = text.replace(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()))) {
            target = matcher.group();
         }
      }

      return text;
   }

   public static int countMatchText(String text, List ruleList) {
      int count = 0;
      Iterator var3 = ruleList.iterator();

      while(var3.hasNext()) {
         String rule = (String)var3.next();
         Pattern pattern = Pattern.compile(rule);

         for(Matcher matcher = pattern.matcher(text); matcher.find(); ++count) {
            String target = matcher.group();
            text = text.replace(target, "");
         }
      }

      return count;
   }

   public static List matchRules(String text, List ruleList) {
      ArrayList list = new ArrayList();
      if(ruleList != null && ruleList.size() > 0) {
         Iterator var3 = ruleList.iterator();

         while(var3.hasNext()) {
            String rule = (String)var3.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               list.add(rule);
            }
         }
      }

      return list;
   }

   public static List matchText(String text, List ruleList) {
      ArrayList list = new ArrayList();
      if(ruleList != null && ruleList.size() > 0) {
         Iterator var3 = ruleList.iterator();

         while(var3.hasNext()) {
            String rule = (String)var3.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);

            while(matcher.find()) {
               String target = matcher.group();
               text = text.replace(target, "");
               list.add(target);
            }
         }
      }

      return list;
   }

   public static boolean matchBool(String text, List ruleList) {
      if(ruleList != null && ruleList.size() > 0) {
         Iterator var2 = ruleList.iterator();

         while(var2.hasNext()) {
            String rule = (String)var2.next();
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()) {
               String target = matcher.group();
               return true;
            }
         }
      }

      return false;
   }

   public static String matchLastText(String text, List ruleList) {
      String target = "";
      Iterator var3 = ruleList.iterator();

      while(var3.hasNext()) {
         String rule = (String)var3.next();
         Pattern pattern = Pattern.compile(rule);

         for(Matcher matcher = pattern.matcher(text); matcher.find(); text = text.replace(target, "")) {
            target = matcher.group();
         }
      }

      return target;
   }

   public static Map matchTextPosition(String text, List ruleList) {
      HashMap result = new HashMap();
      ArrayList position = new ArrayList();
      ArrayList content = new ArrayList();
      Iterator var5 = ruleList.iterator();

      while(var5.hasNext()) {
         String rule = (String)var5.next();
         Pattern pattern = Pattern.compile(rule);

         String target;
         for(Matcher matcher = pattern.matcher(text); matcher.find(); text = text.replaceFirst(target, com.cjbdi.core.developcenter.utils.CommonTools.copystr("x", target.length()))) {
            target = matcher.group();
            position.add(String.valueOf(text.indexOf(target)));
            content.add(target);
         }
      }

      result.put("text", content);
      result.put("position", position);
      return result;
   }
}
