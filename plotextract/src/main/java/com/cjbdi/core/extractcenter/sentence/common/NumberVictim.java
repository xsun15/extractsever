package com.cjbdi.core.extractcenter.sentence.common;

import com.cjbdi.core.convertlabelcenter.utils.TraceSource;
import com.cjbdi.core.extractcenter.sentence.common.utils.NumberConfig;
import com.cjbdi.core.extractcenter.utils.*;
import com.cjbdi.core.servercenter.utils.TraceSourceModel;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

import static com.cjbdi.core.extractcenter.utils.MatchRule.*;

public class NumberVictim {
   private List<Pattern> positivePurePattern;
   private List<Pattern> negativePurePattern;
   private List<String> retainSyntax;

   public NumberVictim(List<Pattern> positivePureRule, List<Pattern> negativePureRule) {
      this.positivePurePattern = positivePureRule;
      this.negativePurePattern = negativePureRule;
      this.retainSyntax = new ArrayList<>();
      this.retainSyntax.add("主谓关系");
      this.retainSyntax.add("核心关系");
      this.retainSyntax.add("动宾关系");
      this.retainSyntax.add("并列关系");
   }

   public NumberConfig doextract(DefendantModel defendantModel, CasecauseModel casecauseModel, String keyword, String noiseword) {
      Set<String> defendants = defendantModel.getDefendantNameSet();
      // 本院认为段抽取总结性数字
      String conclusion = casecauseModel.getOpinion();
      NumberConfig numberConfig = runSummarize(conclusion, keyword, noiseword);
      if (numberConfig != null) {
         numberConfig.paraName = "本院认为";
         return numberConfig;
      }
      // 经审理查明段抽取总结性数字
      String factText = casecauseModel.getJustice();
      numberConfig = runSummarize(factText, keyword, noiseword);
      if (numberConfig != null) {
         numberConfig.paraName="经审理查明";
         return numberConfig;
      }
      // 如果上述没有抽取到，则去本院认为段数人头
      numberConfig = runCalculate(conclusion, keyword, noiseword, defendants);
      if (numberConfig != null) {
         numberConfig.paraName = "本院认为";
         return numberConfig;
      }
      // 如果上述没有抽取到，则去经审理查明段数人头
      numberConfig = runCalculate(factText, keyword, noiseword, defendants);
      if (numberConfig != null) {
         numberConfig.paraName="经审理查明";
         return numberConfig;
      }
      return null;
   }

   public NumberConfig runSummarize(String text, String keyword, String noiseword) {
      if (StringUtils.isNotEmpty(text)) {
         List<String> textList = Arrays.asList(text.split("\n"));
         List<String> contentList = new ArrayList<>();
         for (String line : textList) {
             contentList.addAll(Arrays.asList(line.split(noiseword)));
         }
         List<String> targetContent = new ArrayList<>();
         for (String sentence : contentList) {
            if (matchRule(sentence, keyword)) {
               targetContent.add(sentence);
            }
         }
         if (targetContent != null && targetContent.size() > 0) {
            int injuryNumber = 0;
            List<NumberConfig> numberConfigList = new ArrayList<>();
            Map<String, TraceSourceModel> traceSourceModelMap = new HashMap<>();
            for (String content : targetContent) {
               NumberConfig numberConfig = matchPatternNumberConfig(content, positivePurePattern, negativePurePattern);
               if (numberConfig != null) {
                  String number = ExtractNumber.run(WordfigureToNumber.run(numberConfig.target), "^[0-9]+");
                  if (StringUtils.isNotEmpty(number) && IsDigit.isInteger(number)) {
                     injuryNumber = injuryNumber + Integer.parseInt(number);
                  }
                  numberConfig.startcolor = numberConfig.startcolor + text.indexOf(content);
                  numberConfig.endcolor = numberConfig.endcolor + text.indexOf(content);
                  numberConfig.colorTarget = numberConfig.startcolor + "," + numberConfig.endcolor + ";";
                  // 提取溯源
                  for (String line : textList) {
                     if (line.contains(content)) {
                        TraceSourceModel traceSourceModel = new TraceSourceModel();
                        traceSourceModel.startpos.add(line.indexOf(content) + content.indexOf(numberConfig.target));
                        traceSourceModel.matchText.add(numberConfig.target);
                        traceSourceModelMap.put(line, traceSourceModel);
                        break;
                     }
                  }
                  numberConfigList.add(numberConfig);
               }
            }
            if (injuryNumber > 0) {
               NumberConfig result = new NumberConfig();
               ColorTextConfig colorTextConfig = new ColorTextConfig();
               colorTextConfig.text=text;
               for (NumberConfig numberConfig : numberConfigList) {
                  colorTextConfig.effectText += numberConfig.colorTarget;
                  result.rule += numberConfig.rule;
                  result.target = numberConfig.target;
                  result.startcolor = numberConfig.startcolor;
                  result.endcolor = numberConfig.endcolor;
               }
               ColorText colorText = new ColorText();
               result.value= injuryNumber;
               result.colorTarget = colorText.run(colorTextConfig);
               result.traceSourceMap = traceSourceModelMap;
               return result;
            }
         }
      }
      return null;
   }

   public NumberConfig runCalculate(String text, String keyword, String noiseword, Set<String> defendants) {
      Segment segment = HanLP.newSegment().enableNameRecognize(true);
      if (StringUtils.isNotEmpty(text)) {
         List<String> textList = Arrays.asList(text.split("\n"));
         List<String> contentList = new ArrayList<>();
         for (String line : textList) {
            contentList.addAll(Arrays.asList(line.split(noiseword)));
         }
         List<String> targetContent = new ArrayList<>();
         for (String sentence : contentList) {
            if (matchRule(sentence, keyword)) {
               targetContent.add(sentence);
            }
         }
         Set<String> nameSet = new HashSet<>();
         int min = 0;
         if (targetContent != null && targetContent.size() > 0) {
            // 利用hanlp的词法分析提取人名
            List<NumberConfig> numberConfigList = new ArrayList<>();
            Map<String, TraceSourceModel> traceSourceModelMap = new HashMap<>();
            for (String content : targetContent) {
               List<NumberConfig> numberConfigs= matchTextListPattern(content, positivePurePattern, negativePurePattern);
               if (numberConfigs!=null) {
                  for (NumberConfig numberConfig : numberConfigs) {
                     String target = "";
                     if (target==null||target.isEmpty()) target = numberConfig.target;
                     List<Term> termList = segment.seg(target);
                     boolean flag = false;
                     for (Term term : termList) {
                        if (term.nature.toString().equals("nr")) {
                           if (!defendants.contains(term.word) && !nameSet.contains(term.word)) {
                              flag = true;
                              nameSet.add(term.word);
                              numberConfigList.add(numberConfig);
                           }
                        }
                     }
                     if (!flag){
                        min += 1; //如果如人头的结果为空，则至少要有一个人
                     }

                     // 提取溯源
                     for (String line : textList) {
                        if (line.contains(content)) {
                           TraceSourceModel traceSourceModel = new TraceSourceModel();
                           traceSourceModel.startpos.add(line.indexOf(content) + numberConfig.startcolor);
                           traceSourceModel.matchText.add(numberConfig.target);
                           traceSourceModelMap.put(line, traceSourceModel);
                           break;
                        }
                     }
                  }
               }
            }
            if ((nameSet != null && nameSet.size() > 0) || min > 0) {
               NumberConfig result = new NumberConfig();
               ColorTextConfig colorTextConfig = new ColorTextConfig();
               colorTextConfig.text=text;
               for (NumberConfig numberConfig : numberConfigList) {
                  colorTextConfig.effectText += numberConfig.colorTarget;
                  result.rule += numberConfig.rule;
                  result.target = numberConfig.target;
                  result.startcolor = numberConfig.startcolor;
                  result.endcolor = numberConfig.endcolor;
               }
               ColorText colorText = new ColorText();
               result.value= nameSet.size()>1?nameSet.size():1;
               result.colorTarget = colorText.run(colorTextConfig);
               result.traceSourceMap = traceSourceModelMap;
               return result;
            }
         }
      }
      return null;
   }

}
