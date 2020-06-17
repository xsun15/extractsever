package com.cjbdi.core.extractcenter.common.crimialnum;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.DefendantModel;
import com.cjbdi.core.extractcenter.utils.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.utils.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.extractcenter.utils.tracesource.MatchModel;
import com.cjbdi.core.utils.CN2Num;
import com.cjbdi.core.utils.CommonTools;
import com.cjbdi.core.utils.HttpRequest;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class CriminalNumExtractor {
    public Label doExtract(CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel) {
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
      // 判断经审理查明段是不是标准的文书格式，如果是直接数body的大小
      paraName = "justice";
      priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
      FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
      if (factTextConfig.isNormal && StringUtils.isNotEmpty(factTextConfig.body)) {
          List<String> criminals = Arrays.asList(casecauseModel.getJustice().split("\n"));
          if (criminals!=null&&criminals.size()>0) {
              label = new Label();
              MatchModel matchModel = new MatchModel();
              matchModel.setParaName(paraName);
              matchModel.setParaText(casecauseModel.getJustice());
              matchModel.setPriorityLevel(priorityLevel);
              matchModel.setStartPos(casecauseModel.getJustice().indexOf(factTextConfig.body));
              matchModel.setEndPos(matchModel.getStartPos()+factTextConfig.body.length());
              label.setValue(String.valueOf(criminals.size()));
              List<MatchModel> matchModelList = new ArrayList<>();
              matchModelList.add(matchModel);
              label.setMatchModelList(matchModelList);
          }
      }
      // 如果上述没有抽取到，则根据模型判断犯罪次数
      paraName = "justice";
      priorityLevel = BeanFactoryConfig.splitDocConfig.getPriorityLevel().getInteger(paraName);
      label = runModel(casecauseModel.getJustice(), paraName, priorityLevel, extractFeatureModel);
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

   public Label runModel(String text, String paraName, int priorityLevel, ExtractFeatureModel extractFeatureModel) {
      if (StringUtils.isEmpty(text)) {
          Label label = new Label();
          label.setValue("1");
          return label;
      } else {
          int criminalCounts = 0;
          List<String> textList = Arrays.asList(text.split("\n"));
          for (String line : textList) {
              JSONObject reqPara = new JSONObject();
              reqPara.put("fullText", line);
              String result = HttpRequest.sendPost(BeanFactoryConfig.interfaceUrl.getServiceUrl().getJSONObject("featureModel").getString("predictCrimeCountPb"), reqPara);
              if (StringUtils.isNotEmpty(result)) {
                  if (CN2Num.isNum(result)) criminalCounts += Integer.parseInt(result);
              }
          }
          Label label = new Label();
          if (criminalCounts > 1)  label.setValue(String.valueOf(criminalCounts));
          else label.setValue("1");
          return label;
      }
   }
}
