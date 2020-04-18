package com.cjbdi.core.extractcenter.sentence.common.money;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.extractfeature.sentence.utils.MoneyBasicConfig;
import com.cjbdi.core.developcenter.utils.CommonTools;
import com.cjbdi.core.developcenter.utils.MoneyTools;
import com.cjbdi.core.extractcenter.sentence.common.money.MoneyConfig;
import com.cjbdi.core.extractcenter.sentence.utils.BasicSentenceFeatureClass;
import com.cjbdi.core.extractcenter.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.ColorText;
import com.cjbdi.core.extractcenter.utils.ColorTextConfig;
import com.cjbdi.core.extractcenter.utils.DefendantModel;
import com.cjbdi.core.extractcenter.utils.HttpRequest;
import com.cjbdi.core.extractcenter.utils.MatchRule;
import com.cjbdi.core.servercenter.utils.Tools;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;

public class MoneyExtractor extends BasicSentenceFeatureClass {

   protected List mustRule;
   protected List firstPureRuleList;
   protected List secondPureRuleList;
   protected List thirdPureRuleList;
   protected List firstInvalidPureRuleList;
   protected List secondInvalidPureRuleList;
   protected List firstInvalidModelRuleList;
   protected List secondInvalidModelRuleList;
   protected List invalidPureRuleList;
   protected List validPureRuleList;
   protected List dockRuleList;
   protected List convertRuleList;
   protected List modelRuleList;
   protected List moneyRatioList;
   protected List moneyRuleList;
   protected List extractUrl;


   public void init(String casecause, String extractUrl) {
      String ecasecause = (String)BeanFactoryConfig.predCasecauseConfig.getCasecauseName().get(casecause);
      this.mustRule = new ArrayList();
      List list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getPositivepurePattern().get(ecasecause)).get("mustrule");
      if(list != null && !list.isEmpty()) {
         this.mustRule.addAll(list);
      }

      this.firstPureRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getPositivepurePattern().get(ecasecause)).get("firstrule");
      if(list != null && !list.isEmpty()) {
         this.firstPureRuleList.addAll(list);
      }

      this.secondPureRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getPositivepurePattern().get(ecasecause)).get("secondrule");
      if(list != null && !list.isEmpty()) {
         this.secondPureRuleList.addAll(list);
      }

      this.thirdPureRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getPositivepurePattern().get(ecasecause)).get("secondrule");
      if(list != null && !list.isEmpty()) {
         this.thirdPureRuleList.addAll(list);
      }

      this.validPureRuleList = new ArrayList();
      this.validPureRuleList.addAll(this.firstPureRuleList);
      this.validPureRuleList.addAll(this.secondPureRuleList);
      this.validPureRuleList.addAll(this.thirdPureRuleList);
      this.firstInvalidPureRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getNegativepurePattern().get(ecasecause)).get("firstrule");
      if(list != null && !list.isEmpty()) {
         this.firstInvalidPureRuleList.addAll(list);
      }

      this.secondInvalidPureRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getNegativepurePattern().get(ecasecause)).get("secondrule");
      if(list != null && !list.isEmpty()) {
         this.secondInvalidPureRuleList.addAll(list);
      }

      this.invalidPureRuleList = new ArrayList();
      this.invalidPureRuleList.addAll(this.firstInvalidPureRuleList);
      this.invalidPureRuleList.addAll(this.secondInvalidPureRuleList);
      this.firstInvalidModelRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getNegativepurePattern().get(ecasecause)).get("firstrule");
      if(list != null && !list.isEmpty()) {
         this.firstInvalidModelRuleList.addAll(list);
      }

      this.secondInvalidModelRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getNegativepurePattern().get(ecasecause)).get("secondrule");
      if(list != null && !list.isEmpty()) {
         this.secondInvalidModelRuleList.addAll(list);
      }

      this.dockRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getDockpurePattern().get(ecasecause)).get("firstrule");
      if(list != null && !list.isEmpty()) {
         this.dockRuleList.addAll(list);
      }

      this.convertRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getNegativemodelPattern().get(ecasecause)).get("convertrule");
      if(list != null && !list.isEmpty()) {
         this.convertRuleList.addAll(list);
      }

      this.modelRuleList = new ArrayList();
      list = (List)((LinkedHashMap)((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getPositivemodelPattern().get(ecasecause)).get("firstrule");
      if(list != null && !list.isEmpty()) {
         this.modelRuleList.addAll(list);
      }

      this.moneyRatioList = ((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneycovertionextract")).getMoneyRatio();
      this.moneyRuleList = ((MoneyBasicConfig)BeanFactoryConfig.extractFeatureConfig.getMoney().getFeatures().get("moneyextract")).getMoney();
      this.extractUrl = new ArrayList();
      this.extractUrl.add(extractUrl);
   }

   public JSONObject run(DefendantModel defendantModel, CasecauseModel casecauseModel) {
      String casecause = casecauseModel.getCasecause();
      String opinion = casecauseModel.getOpinion();
      String justice = casecauseModel.getJustice();
      ColorTextConfig colorTextConfig = new ColorTextConfig();
      JSONObject result = new JSONObject();
      List allmoney;
      if(StringUtils.isNotEmpty(opinion)) {
         opinion = Tools.deleteDigitDot(opinion);
         allmoney = this.extractMoneyUsedModel(opinion, casecause, this.modelRuleList, this.firstInvalidModelRuleList, this.secondInvalidModelRuleList, this.convertRuleList, this.moneyRatioList);
         if(this.isOneEffectMoney(allmoney, opinion, colorTextConfig, result)) {
            return result;
         }
      }

      if(StringUtils.isNotEmpty(justice)) {
         justice = Tools.deleteDigitDot(justice);
         allmoney = this.extractMoneyUsedModel(justice, casecause, this.modelRuleList, this.firstInvalidModelRuleList, this.secondInvalidModelRuleList, this.convertRuleList, this.moneyRatioList);
         if(allmoney == null || allmoney.size() == 0) {
            return result;
         }

         FactTextConfig factTextConfig = FactTextSplit.run(justice);
         if(StringUtils.isNotBlank(factTextConfig.header) && StringUtils.isNotEmpty(factTextConfig.header)) {
            allmoney = this.extractMoneyUsedModel(factTextConfig.header, casecause, this.modelRuleList, this.firstInvalidModelRuleList, this.secondInvalidModelRuleList, this.convertRuleList, this.moneyRatioList);
            if(this.isOneEffectMoney(allmoney, factTextConfig.header, colorTextConfig, result)) {
               return result;
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.tail) && StringUtils.isNotEmpty(factTextConfig.tail)) {
            allmoney = this.extractMoneyUsedModel(factTextConfig.tail, casecause, this.modelRuleList, this.firstInvalidModelRuleList, this.secondInvalidModelRuleList, this.convertRuleList, this.moneyRatioList);
            if(this.isOneEffectMoney(allmoney, factTextConfig.tail, colorTextConfig, result)) {
               return result;
            }
         }

         if(StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
            allmoney = this.extractMoneyUsedModel(factTextConfig.body, casecause, this.modelRuleList, this.firstInvalidModelRuleList, this.secondInvalidModelRuleList, this.convertRuleList, this.moneyRatioList);
            return this.extractMoneyFromJusticBody(allmoney, factTextConfig.body, justice, colorTextConfig, result);
         }
      }

      return result;
   }

   public JSONObject extractMoneyFromJusticBody(List allmoney, String factBody, String justice, ColorTextConfig colorTextConfig, JSONObject result) {
      if(allmoney.size() >= 3 && this.combineEffectMoney(factBody, allmoney, colorTextConfig)) {
         this.packingResult(allmoney, colorTextConfig, result);
         return result;
      } else {
         double sum = 0.0D;
         ArrayList effectMoney = new ArrayList();
         ArrayList invalidMoney = new ArrayList();
         List justiceList = Arrays.asList(factBody.split("\n"));

         for(int i = 0; i < justiceList.size(); ++i) {
            String line = (String)justiceList.get(i);
            List effectMoneyConfigList = selectFixParaIndexMoneyConfigList(allmoney, i, "有效");
            List invalidMoneyConfigList = selectFixParaIndexMoneyConfigList(allmoney, i, "无效");
            if(effectMoneyConfigList != null && effectMoneyConfigList.size() != 0) {
               Iterator var15;
               MoneyConfig moneyConfig;
               if(effectMoneyConfigList.size() != 1) {
                  sum += this.addContainTotalMoney(effectMoneyConfigList, line, colorTextConfig, effectMoney, invalidMoney);
                  var15 = invalidMoneyConfigList.iterator();

                  while(var15.hasNext()) {
                     moneyConfig = (MoneyConfig)var15.next();
                     colorTextConfig.invalidText = colorTextConfig.invalidText + moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                     invalidMoney.add(moneyConfig);
                  }
               } else {
                  sum += ((MoneyConfig)effectMoneyConfigList.get(0)).value;
                  colorTextConfig.effectText = colorTextConfig.effectText + ((MoneyConfig)effectMoneyConfigList.get(0)).startColor + "," + ((MoneyConfig)effectMoneyConfigList.get(0)).endColor + ";";
                  effectMoney.add(effectMoneyConfigList.get(0));
                  var15 = invalidMoneyConfigList.iterator();

                  while(var15.hasNext()) {
                     moneyConfig = (MoneyConfig)var15.next();
                     colorTextConfig.invalidText = colorTextConfig.invalidText + moneyConfig.startColor + "," + moneyConfig.endColor + ";";
                     invalidMoney.add(moneyConfig);
                  }
               }
            } else {
               sum += this.convertMoney(i, allmoney, colorTextConfig, effectMoney, invalidMoney);
            }
         }

         colorTextConfig.text = justice;
         this.packingResult(sum, colorTextConfig, result);
         return result;
      }
   }

   public double addContainTotalMoney(List lineMoneyConfigList, String content, ColorTextConfig colorTextConfig, List effectMoney, List invalidMoney) {
      double sum = 0.0D;
      MoneyConfig totalMoney = extractTotalMoneyPattern(lineMoneyConfigList, this.secondPureRuleList, this.moneyRatioList);
      if(totalMoney == null) {
         sum += this.sumMoney(lineMoneyConfigList, colorTextConfig, effectMoney);
      } else if(this.isTotalMoney(totalMoney, lineMoneyConfigList)) {
         sum += totalMoney.value;
         colorTextConfig.effectText = colorTextConfig.effectText + totalMoney.startColor + "," + totalMoney.endColor + ";";
         effectMoney.add(totalMoney);
         Iterator sentence = lineMoneyConfigList.iterator();

         while(sentence.hasNext()) {
            MoneyConfig segment = (MoneyConfig)sentence.next();
            colorTextConfig.invalidText = colorTextConfig.invalidText + segment.startColor + "," + segment.endColor + ";";
            invalidMoney.add(segment);
         }
      } else {
         String sentence1 = totalMoney.sentence;
         Segment segment1 = HanLP.newSegment();
         List termList = segment1.seg(sentence1);
         String connectword = "";
         Iterator flagfront = termList.iterator();

         while(flagfront.hasNext()) {
            Term list1 = (Term)flagfront.next();
            if(list1.nature.toString().equals("cc") || list1.nature.toString().equals("c")) {
               connectword = connectword + list1.word + "|";
            }
         }

         if(StringUtils.isNotEmpty(connectword)) {
            connectword = connectword.substring(0, connectword.length() - 1);
         }

         boolean flagfront1 = false;
         Object list11 = new ArrayList();
         if(StringUtils.isNotEmpty(connectword)) {
            list11 = Arrays.asList(sentence1.split(connectword));
         }

         if(((List)list11).isEmpty()) {
            ((List)list11).add(sentence1);
         }

         Iterator flagtail = ((List)list11).iterator();

         label100:
         while(flagtail.hasNext()) {
            String str = (String)flagtail.next();
            if(str.contains(totalMoney.context) && !str.contains("款物") && !str.contains("财物") && (!str.contains("现金") || !str.contains("物品")) && (!str.contains("赃款") || !str.contains("赃物"))) {
               termList = segment1.seg(str);
               String moneyConfig = "n or nz";
               Iterator var18 = termList.iterator();

               while(true) {
                  if(!var18.hasNext()) {
                     break label100;
                  }

                  Term term = (Term)var18.next();
                  if(moneyConfig.contains(term.nature.name()) && !MatchRule.IsMatch(term.word, "(价值|人民币)")) {
                     flagfront1 = true;
                  }
               }
            }
         }

         boolean flagtail1 = true;
         if(content.lastIndexOf("其中") > totalMoney.start) {
            flagtail1 = false;
         }

         Iterator str1 = lineMoneyConfigList.iterator();

         while(str1.hasNext()) {
            MoneyConfig moneyConfig1 = (MoneyConfig)str1.next();
            if(moneyConfig1.start == totalMoney.start) {
               sum += totalMoney.value;
               colorTextConfig.effectText = colorTextConfig.effectText + moneyConfig1.startColor + "," + moneyConfig1.endColor + ";";
               effectMoney.add(moneyConfig1);
            } else if(flagfront1 && moneyConfig1.start < totalMoney.start) {
               sum += moneyConfig1.value;
               colorTextConfig.effectText = colorTextConfig.effectText + moneyConfig1.startColor + "," + moneyConfig1.endColor + ";";
               effectMoney.add(moneyConfig1);
            } else if(flagtail1 && moneyConfig1.start > totalMoney.start) {
               sum += moneyConfig1.value;
               colorTextConfig.effectText = colorTextConfig.effectText + moneyConfig1.startColor + "," + moneyConfig1.endColor + ";";
               effectMoney.add(moneyConfig1);
            } else {
               colorTextConfig.invalidTextColor = colorTextConfig.invalidTextColor + moneyConfig1.startColor + "," + moneyConfig1.endColor + ";";
               invalidMoney.add(moneyConfig1);
               moneyConfig1.moneyType = "无效";
            }
         }
      }

      return sum;
   }

   public boolean isTotalMoney(MoneyConfig totalMoney, List lineMoneyConfigList) {
      if(totalMoney != null) {
         double sumt = 0.0D;
         Iterator var5 = lineMoneyConfigList.iterator();

         while(var5.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var5.next();
            if(moneyConfig.start != totalMoney.start) {
               sumt += moneyConfig.value;
            }
         }

         if(Math.abs(totalMoney.value - sumt) < 0.01D) {
            return true;
         }
      }

      return false;
   }

   public double convertMoney(int i, List allmoney, ColorTextConfig colorTextConfig, List effectMoney, List invalidMoney) {
      double sum = 0.0D;
      List convertMoneyList = selectFixParaIndexMoneyConfigList(allmoney, i, "无效", true);
      if(convertMoneyList != null && !convertMoneyList.isEmpty()) {
         Iterator unconvertMoneyList = convertMoneyList.iterator();

         while(unconvertMoneyList.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)unconvertMoneyList.next();
            sum += moneyConfig.value;
            colorTextConfig.effectText = colorTextConfig.effectText + moneyConfig.startColor + "," + moneyConfig.endColor + ";";
            effectMoney.add(moneyConfig);
         }
      }

      List unconvertMoneyList1 = selectFixParaIndexMoneyConfigList(allmoney, i, "无效", false);
      if(unconvertMoneyList1 != null && !unconvertMoneyList1.isEmpty()) {
         Iterator moneyConfig2 = unconvertMoneyList1.iterator();

         while(moneyConfig2.hasNext()) {
            MoneyConfig moneyConfig1 = (MoneyConfig)moneyConfig2.next();
            colorTextConfig.invalidText = colorTextConfig.invalidText + moneyConfig1.startColor + "," + moneyConfig1.endColor + ";";
            invalidMoney.add(moneyConfig1);
         }
      }

      return sum;
   }

   public boolean isOneEffectMoney(List allmoney, String content, ColorTextConfig colorTextConfig, JSONObject result) {
      List selectMoneyList = selectMoneyConfigList(allmoney, "有效");
      if(selectMoneyList != null && selectMoneyList.size() == 1) {
         Iterator var6 = allmoney.iterator();

         while(var6.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var6.next();
            if(moneyConfig.moneyType.equals("有效")) {
               colorTextConfig.effectText = colorTextConfig.effectText + moneyConfig.startColor + "," + moneyConfig.endColor + ";";
            } else if(moneyConfig.moneyType.equals("无效")) {
               colorTextConfig.invalidText = colorTextConfig.invalidText + moneyConfig.startColor + "," + moneyConfig.endColor + ";";
            }
         }

         colorTextConfig.text = content;
         this.packingResult((MoneyConfig)selectMoneyList.get(0), colorTextConfig, result);
         return true;
      } else {
         return false;
      }
   }

   public boolean combineEffectMoney(String text, List allmoney, ColorTextConfig colorTextConfig) {
      if(allmoney != null && allmoney.size() > 0) {
         List effectMoney = selectMoneyConfigList(allmoney, "有效");
         List invalidMoney = selectMoneyConfigList(allmoney, "无效");
         MoneyConfig maxMoneyConfig = CommonTools.max(effectMoney);
         if(maxMoneyConfig != null) {
            double sum = 0.0D;
            Iterator var9 = effectMoney.iterator();

            MoneyConfig moneyConfig;
            while(var9.hasNext()) {
               moneyConfig = (MoneyConfig)var9.next();
               if(moneyConfig.start != maxMoneyConfig.start) {
                  sum += moneyConfig.value;
               }
            }

            if(Math.abs(maxMoneyConfig.value - sum) < 0.01D) {
               var9 = effectMoney.iterator();

               while(var9.hasNext()) {
                  moneyConfig = (MoneyConfig)var9.next();
                  if(moneyConfig.start != maxMoneyConfig.start) {
                     invalidMoney.add(moneyConfig);
                  }
               }

               colorTextConfig.effectText = colorTextConfig.effectText + maxMoneyConfig.startColor + "," + maxMoneyConfig.endColor + ";";

               for(var9 = invalidMoney.iterator(); var9.hasNext(); colorTextConfig.invalidText = colorTextConfig.invalidText + moneyConfig.startColor + "," + moneyConfig.endColor + ";") {
                  moneyConfig = (MoneyConfig)var9.next();
               }

               colorTextConfig.text = text;
               return true;
            }
         }
      }

      return false;
   }

   public void packingResult(List allmoney, ColorTextConfig colorTextConfig, JSONObject result) {
      if(allmoney != null && allmoney.size() > 0) {
         double sum = 0.0D;
         Iterator colorText = allmoney.iterator();

         while(colorText.hasNext()) {
            MoneyConfig text = (MoneyConfig)colorText.next();
            if(text.moneyType.equals("有效")) {
               sum += text.value;
            }
         }

         ColorText colorText1 = new ColorText();
         String text1 = colorText1.run(colorTextConfig);
         result.put("text", text1);
         result.put("value", Double.valueOf(sum));
      }

   }

   public static List selectFixParaIndexMoneyConfigList(List allmoney, int index, String moneyType, boolean isconvert) {
      ArrayList moneyConfigList = new ArrayList();
      if(allmoney != null && allmoney.size() > 0) {
         Iterator var5 = allmoney.iterator();

         while(var5.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var5.next();
            if(moneyConfig.paraindex == index && moneyConfig.moneyType.equals(moneyType)) {
               if(moneyType.equals("无效")) {
                  if(moneyConfig.isconvert == isconvert) {
                     moneyConfigList.add(moneyConfig);
                  }
               } else {
                  moneyConfigList.add(moneyConfig);
               }
            }
         }
      }

      return moneyConfigList;
   }

   public static List selectFixParaIndexMoneyConfigList(List allmoney, int index, String moneyType) {
      ArrayList moneyConfigList = new ArrayList();
      if(allmoney != null && allmoney.size() > 0) {
         Iterator var4 = allmoney.iterator();

         while(var4.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var4.next();
            if(moneyConfig.paraindex == index && moneyConfig.moneyType.equals(moneyType)) {
               if(moneyType.equals("无效")) {
                  moneyConfigList.add(moneyConfig);
               } else {
                  moneyConfigList.add(moneyConfig);
               }
            }
         }
      }

      return moneyConfigList;
   }

   public static List selectMoneyConfigList(List allmoney, String moneyType) {
      ArrayList moneyConfigList = new ArrayList();
      if(allmoney != null && allmoney.size() > 0) {
         Iterator var3 = allmoney.iterator();

         while(var3.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var3.next();
            if(moneyConfig.moneyType.equals(moneyType)) {
               moneyConfigList.add(moneyConfig);
            }
         }
      }

      return moneyConfigList;
   }

   public List extractMoneyUsedModel(String text, String casecause, List modelRuleList, List invalidRuleList, List secondInvalidRuleList, List convertRuleList, List moneyRules) {
      String extractUrl = (String)this.extractUrl.get(0);
      ArrayList allmoney = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));
         String lastLine = "";

         for(int content = 0; content < textList.size(); ++content) {
            String param = (String)textList.get(content);
            List resultModel = extractMoney(param, moneyRules);
            resultModel = this.addMoneyPositionOffset(resultModel, lastLine.length());
            lastLine = lastLine + param + "\n";
            if(resultModel != null && !resultModel.isEmpty()) {
               MoneyConfig i;
               for(Iterator resultModelList = resultModel.iterator(); resultModelList.hasNext(); i.moneyType = "有效") {
                  i = (MoneyConfig)resultModelList.next();
               }

               allmoney.addAll(resultModel);
            }
         }

         if(allmoney != null && !allmoney.isEmpty()) {
            String var17 = "";

            MoneyConfig var20;
            for(Iterator var18 = allmoney.iterator(); var18.hasNext(); var17 = var17 + var20.sentence + "\n") {
               var20 = (MoneyConfig)var18.next();
            }

            var17 = var17.substring(0, var17.length() - 1);
            JSONObject var19 = new JSONObject();
            var19.put("casecause", casecause);
            var19.put("content", var17);
            String var21 = HttpRequest.sendPost(extractUrl, var19);
            if(var21 != null && !var21.isEmpty() && !var21.contains("W")) {
               var21 = var21.replaceAll("\\]", "").replaceAll("\\[", "").replaceAll("\'", "").replaceAll(" ", "");
               List var22 = Arrays.asList(var21.split(","));

               for(int var23 = 0; var23 < var22.size(); ++var23) {
                  if(((String)var22.get(var23)).equals("1")) {
                     ((MoneyConfig)allmoney.get(var23)).moneyType = "有效";
                  } else {
                     ((MoneyConfig)allmoney.get(var23)).moneyType = "无效";
                  }
               }
            }
         }
      }

      return allmoney;
   }

   public List extractMoneyUsedPbModel(String text, String casecause, List modelRuleList, List invalidRuleList, List secondInvalidRuleList, List convertRuleList, List moneyRules) {
      String extractUrl = (String)this.extractUrl.get(0);
      ArrayList allmoney = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));
         String lastLine = "";

         for(int content = 0; content < textList.size(); ++content) {
            String param = (String)textList.get(content);
            List resultModel = extractMoney(param, moneyRules);
            resultModel = this.addMoneyPositionOffset(resultModel, lastLine.length());
            lastLine = lastLine + param + "\n";
            if(resultModel != null && !resultModel.isEmpty()) {
               MoneyConfig i;
               for(Iterator resultModelList = resultModel.iterator(); resultModelList.hasNext(); i.para = MoneyTools.mosic(param, i, resultModel)) {
                  i = (MoneyConfig)resultModelList.next();
               }

               allmoney.addAll(resultModel);
            }
         }

         if(allmoney != null && !allmoney.isEmpty()) {
            String var17 = "";

            MoneyConfig var20;
            for(Iterator var18 = allmoney.iterator(); var18.hasNext(); var17 = var17 + var20.para + "\n") {
               var20 = (MoneyConfig)var18.next();
            }

            var17 = var17.substring(0, var17.length() - 1);
            JSONObject var19 = new JSONObject();
            var19.put("casecause", casecause);
            var19.put("content", var17);
            String var21 = HttpRequest.sendPost(extractUrl, var19);
            if(var21 != null && !var21.isEmpty() && !var21.contains("W")) {
               var21 = var21.replaceAll("\\]", "").replaceAll("\\[", "").replaceAll("\'", "").replaceAll(" ", "");
               List var22 = Arrays.asList(var21.split(","));

               for(int var23 = 0; var23 < var22.size(); ++var23) {
                  if(((String)var22.get(var23)).equals("1")) {
                     ((MoneyConfig)allmoney.get(var23)).moneyType = "有效";
                  } else {
                     ((MoneyConfig)allmoney.get(var23)).moneyType = "无效";
                  }
               }
            }
         }
      }

      return allmoney;
   }

   public List extractMoneyPureRule(String text, List PatternList) {
      ArrayList allmoney = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));
         String lastLine = "";

         for(int i = 0; i < textList.size(); ++i) {
            String line = (String)textList.get(i);
            List moneyConfigList = extractMoney(line, this.moneyRatioList);
            moneyConfigList = this.addMoneyPositionOffset(moneyConfigList, lastLine.length());
            lastLine = lastLine + line + "\n";
            if(moneyConfigList != null && moneyConfigList.size() > 0) {
               MoneyConfig moneyConfig;
               for(Iterator var9 = moneyConfigList.iterator(); var9.hasNext(); moneyConfig.endColor = moneyConfig.end) {
                  moneyConfig = (MoneyConfig)var9.next();
                  String mosicText = MatchRule.cleanMatchTextPattern(moneyConfig.sentence, this.firstInvalidPureRuleList);
                  mosicText = MatchRule.cleanMatchTextPattern(mosicText, this.secondInvalidPureRuleList);
                  if(MatchRule.IsMatch(mosicText, PatternList)) {
                     moneyConfig.moneyType = "有效";
                  } else {
                     moneyConfig.moneyType = "无效";
                     if(MatchRule.IsMatchPattern(moneyConfig.sentence, this.convertRuleList) && !MatchRule.matchPatternsBool(moneyConfig.sentence, this.secondInvalidPureRuleList)) {
                        moneyConfig.isconvert = true;
                     }
                  }

                  moneyConfig.paraindex = i;
                  moneyConfig.startColor = moneyConfig.start;
               }

               allmoney.addAll(moneyConfigList);
            }
         }
      }

      return allmoney;
   }

   public List extractMoneyPurePattern(String text, String casecause, List modelRuleList, List invalidRuleList, List secondInvalidRuleList, List convertRuleList, List moneyRules) {
      ArrayList allmoney = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));
         String lastLine = "";

         for(int i = 0; i < textList.size(); ++i) {
            String line = (String)textList.get(i);
            List moneyConfigList = extractMoney(line, this.moneyRatioList);
            moneyConfigList = this.addMoneyPositionOffset(moneyConfigList, lastLine.length());
            lastLine = lastLine + line + "\n";
            if(moneyConfigList != null && moneyConfigList.size() > 0) {
               MoneyConfig moneyConfig;
               for(Iterator var14 = moneyConfigList.iterator(); var14.hasNext(); moneyConfig.endColor = moneyConfig.end) {
                  moneyConfig = (MoneyConfig)var14.next();
                  String mosicText = MatchRule.cleanMatchTextPattern(moneyConfig.sentence, this.firstInvalidPureRuleList);
                  mosicText = MatchRule.cleanMatchTextPattern(mosicText, this.secondInvalidPureRuleList);
                  if(MatchRule.IsMatchPattern(mosicText, this.validPureRuleList)) {
                     moneyConfig.moneyType = "有效";
                  } else {
                     moneyConfig.moneyType = "无效";
                     if(MatchRule.IsMatchPattern(moneyConfig.sentence, convertRuleList) && !MatchRule.matchPatternsBool(moneyConfig.sentence, this.secondInvalidPureRuleList)) {
                        moneyConfig.isconvert = true;
                     }
                  }

                  moneyConfig.paraindex = i;
                  moneyConfig.startColor = moneyConfig.start;
               }

               allmoney.addAll(moneyConfigList);
            }
         }
      }

      return allmoney;
   }

   public List extractMoneyPureRule(String text) {
      ArrayList allmoney = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         List textList = Arrays.asList(text.split("\n"));
         String lastLine = "";

         for(int i = 0; i < textList.size(); ++i) {
            String line = (String)textList.get(i);
            List moneyConfigList = extractMoney(line, this.moneyRatioList);
            moneyConfigList = this.addMoneyPositionOffset(moneyConfigList, lastLine.length());
            lastLine = lastLine + line + "\n";
            if(moneyConfigList != null && moneyConfigList.size() > 0) {
               MoneyConfig moneyConfig;
               for(Iterator var8 = moneyConfigList.iterator(); var8.hasNext(); moneyConfig.endColor = moneyConfig.end) {
                  moneyConfig = (MoneyConfig)var8.next();
                  String mosicText = MatchRule.cleanMatchTextPattern(moneyConfig.sentence, this.firstInvalidPureRuleList);
                  mosicText = MatchRule.cleanMatchTextPattern(mosicText, this.secondInvalidPureRuleList);
                  if(MatchRule.IsMatchMoney(mosicText, this.moneyRatioList)) {
                     moneyConfig.moneyType = "有效";
                  } else {
                     moneyConfig.moneyType = "无效";
                     if(MatchRule.IsMatchPattern(moneyConfig.sentence, this.convertRuleList) && !MatchRule.matchPatternsBool(moneyConfig.sentence, this.secondInvalidPureRuleList)) {
                        moneyConfig.isconvert = true;
                     }
                  }

                  moneyConfig.paraindex = i;
                  moneyConfig.startColor = moneyConfig.start;
               }

               allmoney.addAll(moneyConfigList);
            }
         }
      }

      return allmoney;
   }

   public static MoneyConfig extractTotalMoney(List allmoney, List PatternList, List moneyRules) {
      if(allmoney != null && allmoney.size() > 0) {
         MoneyConfig maxMoneyConfig = new MoneyConfig();
         Iterator var4 = allmoney.iterator();

         while(var4.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var4.next();
            if(MatchRule.matchRulesBool(moneyConfig.sentence, PatternList) && moneyConfig.value - maxMoneyConfig.value > 1.0E-4D) {
               maxMoneyConfig = moneyConfig;
            }
         }

         if(maxMoneyConfig != null && maxMoneyConfig.value > 1.0E-4D) {
            return maxMoneyConfig;
         }
      }

      return null;
   }

   public static MoneyConfig extractTotalMoneyPattern(List allmoney, List PatternList, List moneyRules) {
      if(allmoney != null && allmoney.size() > 0) {
         MoneyConfig maxMoneyConfig = new MoneyConfig();
         Iterator var4 = allmoney.iterator();

         while(var4.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var4.next();
            if(MatchRule.matchPatternsBool(moneyConfig.sentence, PatternList) && moneyConfig.value - maxMoneyConfig.value > 1.0E-4D) {
               maxMoneyConfig = moneyConfig;
            }
         }

         if(maxMoneyConfig != null && maxMoneyConfig.value > 1.0E-4D) {
            return maxMoneyConfig;
         }
      }

      return null;
   }

   public static List extractMoney(String text, List moneyRules) {
      Object moneyConfigs = new ArrayList();
      if(StringUtils.isNotEmpty(text)) {
         moneyConfigs = MatchRule.matchMoney(text, moneyRules);
      }

      return (List)moneyConfigs;
   }

   public double sumMoney(List moneyConfigList, ColorTextConfig colorTextConfig, List effectMoney) {
      double sum = 0.0D;
      if(moneyConfigList != null && !moneyConfigList.isEmpty()) {
         Iterator var6 = moneyConfigList.iterator();

         while(var6.hasNext()) {
            MoneyConfig moneyConfig = (MoneyConfig)var6.next();
            sum += moneyConfig.value;
            colorTextConfig.effectText = colorTextConfig.effectText + moneyConfig.startColor + "," + moneyConfig.endColor + ";";
            effectMoney.add(moneyConfig);
         }
      }

      return sum;
   }

   public void packingResult(MoneyConfig moneyConfig, ColorTextConfig colorTextConfig, JSONObject result) {
      result.put("value", String.format("%.2f", new Object[]{Double.valueOf(moneyConfig.value)}));
      ColorText colorText = new ColorText();
      String text = colorText.run(colorTextConfig);
      result.put("text", text);
   }

   public void packingResult(double sum, ColorTextConfig colorTextConfig, JSONObject result) {
      ColorText colorText = new ColorText();
      String text = colorText.run(colorTextConfig);
      result.put("value", String.format("%.2f", new Object[]{Double.valueOf(sum)}));
      result.put("text", text);
   }

   public List addMoneyPositionOffset(List money, int offset) {
      if(money != null) {
         for(int i = 0; i < money.size(); ++i) {
            MoneyConfig moneyConfig = (MoneyConfig)money.get(i);
            moneyConfig.startColor += offset;
            moneyConfig.endColor += offset;
            money.set(i, moneyConfig);
         }
      }

      return money;
   }
}
