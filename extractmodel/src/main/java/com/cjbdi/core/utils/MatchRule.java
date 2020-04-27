package com.cjbdi.core.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

   public static boolean IsMatch(String text, String rule) {
      Pattern pattern = Pattern.compile(rule);
      Matcher matcher = pattern.matcher(text);
      return matcher.find();
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

}
