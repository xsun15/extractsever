package com.cjbdi.core.extractcenter.common.peoplenum;

import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.DefendantModel;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.extractcenter.utils.tracesource.MatchModel;
import com.cjbdi.core.utils.CN2Num;
import com.cjbdi.core.utils.CommonTools;
import org.apache.commons.lang.StringUtils;
import java.util.*;

public class InjuryNumExtractor {
    public Label doExtract(DefendantModel defendantModel, CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel) {
      Set<String> defendants = defendantModel.getDefendantNameSet();
      // 抽取本院认为段总结性数字
      String paraName = "opinion";
      Integer priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
      Label label = runSummarize(casecauseModel.getOpinion(), paraName, priorityLevel, extractFeatureModel);
      if (label != null) {
         return label;
      }
      // 经审理查明段抽取总结性数字
      paraName = "justice";
      priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
      label = runSummarize(casecauseModel.getJustice(), paraName, priorityLevel, extractFeatureModel);
      if (label != null) {
         return label;
      }
      // 如果上述没有抽取到，则去本院认为段数人头
      paraName = "opinion";
      priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
      label = runCalculate(casecauseModel.getOpinion(), defendants,paraName, priorityLevel, extractFeatureModel);
      if (label != null) {
         return label;
      }
      // 如果上述没有抽取到，则去经审理查明段数人头
      paraName = "justice";
      priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
      label = runCalculate(casecauseModel.getJustice(), defendants, paraName, priorityLevel, extractFeatureModel);
      if (label != null) {
         return label;
      }
      return null;
   }

   public Label runSummarize(String text, String paraName, int priorityLevel, ExtractFeatureModel extractFeatureModel) {
      if (StringUtils.isNotEmpty(text)) {
         // 根据noiseWord切割文本
         List<String> textList = Arrays.asList(text.split("\n"));
         List<String> contentList = new ArrayList<>();
         for (String line : textList) {
             contentList.addAll(Arrays.asList(line.split(extractFeatureModel.getNoiseWord())));
         }
         // 得到含有keyWord文本
         List<String> targetContent = new ArrayList<>();
         for (String sentence : contentList) {
            if (CommonTools.isMatch(sentence, extractFeatureModel.getNoiseWord())) {
               targetContent.add(sentence);
            }
         }
         // 提取总结性数字
         if (targetContent != null) {
             int injuryNumber = 0;
             List<MatchModel> matchModelList = new ArrayList<>();
             for (String content : targetContent) {
                 MatchModel matchModel = CommonTools.matchModelFirstGroup(content, extractFeatureModel.getSumPositiveRule(), extractFeatureModel.getNegativePureRule());
                 if (matchModel != null) {
                     if (CN2Num.isChineseNum(matchModel.getMatchText()))
                         injuryNumber += CN2Num.cn2Num(matchModel.getMatchText());
                     else if (CN2Num.isNum(matchModel.getMatchText()))
                         injuryNumber += Integer.parseInt(matchModel.getMatchText());
                     matchModel.setParaName(paraName);
                     matchModel.setPriorityLevel(priorityLevel);
                     matchModel.setParaText(content);
                     matchModelList.add(matchModel);
                 }
             }
             if (injuryNumber > 0) {
                 Label label = new Label();
                 label.setValue(String.valueOf(injuryNumber));
                 label.setMatchModelList(matchModelList);
                 return label;
             }
         }
      }
      return null;
   }

   public Label runCalculate(String text, Set<String> defendants, String paraName, int priorityLevel, ExtractFeatureModel extractFeatureModel) {
      // 根据noiseWord切割文本
      if (StringUtils.isNotEmpty(text)) {
         List<String> textList = Arrays.asList(text.split("\n"));
         List<String> contentList = new ArrayList<>();
         for (String line : textList) {
            contentList.addAll(Arrays.asList(line.split(extractFeatureModel.getNoiseWord())));
         }
         // 得到含有keyWord文本
         List<String> targetContent = new ArrayList<>();
         for (String sentence : contentList) {
            if (CommonTools.isMatch(sentence, extractFeatureModel.getKeyWord() )) {
               targetContent.add(sentence);
            }
         }
         // 抽取人名
         if (targetContent != null) {
            List<MatchModel> matchModelList = new ArrayList<>();
            int injuryNum = 0;
            for (String content : targetContent) {
               MatchModel matchModel = CommonTools.matchModel(content, extractFeatureModel.getPositivePureRule(), extractFeatureModel.getNegativePureRule());
               if (matchModel!=null) {
                   if (StringUtils.isEmpty(matchModel.getMatchText())) continue;
                   Set<String> nameSet = CommonTools.getNameSet(matchModel.getMatchText(), defendants);
                   if (nameSet!=null&&nameSet.size()>0) injuryNum += nameSet.size();
                   else injuryNum += 1;
                   matchModel.setParaName(paraName);
                   matchModel.setPriorityLevel(priorityLevel);
                   matchModel.setParaText(content);
                   matchModelList.add(matchModel);
               }
            }
            if (injuryNum>0) {
                Label label = new Label();
                label.setMatchModelList(matchModelList);
                label.setValue(String.valueOf(injuryNum));
                return label;
            }
         }
      }
      return null;
   }
}
