package com.cjbdi.core.extractcenter.common.money;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureModel;
import com.cjbdi.core.configcenter.extractfeature.money.ExtractMoneyModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.DefendantModel;
import com.cjbdi.core.extractcenter.utils.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.utils.splitdocument.FactTextSplit;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.extractcenter.utils.tracesource.MatchModel;
import com.cjbdi.core.utils.CommonTools;
import com.cjbdi.core.utils.HttpRequest;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class MLExtractMoney extends BasicMoneyExtractor {
	private String modelUrl;
	private JSONObject caseMoney;
	private List<Pattern> negativeRule;
	private List<Pattern> positive1thRule;
	private List<Pattern> positive2thRule;
	private List<Pattern> positive3thRule;
	private List<Pattern> convertRule;
	private List<Pattern> mustRule;

	public void MLExtractMoney (String modelUrl, JSONObject caseMoney) {
		this.modelUrl = modelUrl;
		this.caseMoney = caseMoney;
		this.positive1thRule = new ArrayList<>();
		this.positive2thRule = new ArrayList<>();
		this.positive3thRule = new ArrayList<>();
		this.negativeRule = new ArrayList<>();
		this.convertRule = new ArrayList<>();
		this.mustRule = new ArrayList<>();
		if (caseMoney.containsKey("positivePureRule")) {
			if (!caseMoney.getJSONObject("positivePureRule").isEmpty()) {
				if (caseMoney.getJSONObject("positivePureRule").containsKey("1th"))
					positive1thRule.addAll(caseMoney.getJSONObject("positivePureRule").getObject("1th", ExtractMoneyModel.class).getRule());
			}
		}
		if (caseMoney.containsKey("positivePureRule")) {
			if (!caseMoney.getJSONObject("positivePureRule").isEmpty()) {
				if (caseMoney.getJSONObject("positivePureRule").containsKey("2th"))
					positive2thRule.addAll(caseMoney.getJSONObject("positivePureRule").getObject("2th", ExtractMoneyModel.class).getRule());
			}
		}
		if (caseMoney.containsKey("positiveModelRule")) {
			if (!caseMoney.getJSONObject("positiveModelRule").isEmpty()) {
				if (caseMoney.getJSONObject("positiveModelRule").containsKey("3th"))
					positive3thRule.addAll(caseMoney.getJSONObject("positiveModelRule").getObject("3th", ExtractMoneyModel.class).getRule());
			}
		} else if (caseMoney.containsKey("positivePureRule")) {
			if (!caseMoney.getJSONObject("positivePureRule").isEmpty()) {
				if (caseMoney.getJSONObject("positivePureRule").containsKey("3th"))
					positive3thRule.addAll(caseMoney.getJSONObject("positivePureRule").getObject("3th", ExtractMoneyModel.class).getRule());
			}
		}
		if (caseMoney.containsKey("negativeModelRule")) {
			if (!caseMoney.getJSONObject("negativeModelRule").isEmpty()) {
				if (caseMoney.getJSONObject("negativeModelRule").containsKey("1th"))
					negativeRule.addAll(caseMoney.getJSONObject("negativeModelRule").getObject("1th", ExtractMoneyModel.class).getRule());
				if (caseMoney.getJSONObject("negativeModelRule").containsKey("2th"))
					negativeRule.addAll(caseMoney.getJSONObject("negativeModelRule").getObject("2th", ExtractMoneyModel.class).getRule());
			}
		}
		if (caseMoney.containsKey("convertRule")) {
			if (!caseMoney.getJSONObject("convertRule").isEmpty()) {
				if (caseMoney.getJSONObject("convertRule").containsKey("1th"))
					convertRule.addAll(caseMoney.getJSONObject("convertRule").getObject("1th", ExtractMoneyModel.class).getRule());
			}
		}
		if (caseMoney.containsKey("mustRule")) {
			if (!caseMoney.getJSONObject("mustRule").isEmpty()) {
				if (caseMoney.getJSONObject("mustRule").containsKey("1th"))
					mustRule.addAll(caseMoney.getJSONObject("mustRule").getObject("1th", ExtractMoneyModel.class).getRule());
			}
		}
	}

	public Label doExtract(DefendantModel defendantModel, CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel) {
		Label label = new Label();
		List<MatchModel> allmoney = null;
		// 先抽取本院认为段，如果本院认为段排除无效金额外，只剩下一个有效金额，则认为为抽取金额
		// 本处的有效金额判定只用到模型，未用正则
		if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
			allmoney = this.extractMoneyUsedModel(casecauseModel.getOpinion(), casecauseModel.getCasecause(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
			if(isOneEffectMoney(allmoney, label)) {
				return label;
			}
		}
		// 抽取经审理查明段
		if(StringUtils.isNotEmpty(casecauseModel.getJustice())) {
			// 如果经审理查明段没有金额相关描述，则直接返回
			allmoney = this.extractMoneyUsedModel(casecauseModel.getJustice(), casecauseModel.getCasecause(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
			if(allmoney == null || allmoney.size() == 0) {
				return null;
			}
			// 将经审理查明段分割为头段、body和尾段
			FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
			// 如果存在头段，且排除无效金额外，只剩下一个有效金额，则认为为抽取金额
			if(StringUtils.isNotBlank(factTextConfig.header) && StringUtils.isNotEmpty(factTextConfig.header)) {
				allmoney = this.extractMoneyUsedModel(factTextConfig.header, casecauseModel.getCasecause(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				if(this.isOneEffectMoney(allmoney, label)) {
					return label;
				}
			}
			// 如果存在尾段，且排除无效金额外，只剩下一个有效金额，则认为为抽取金额
			if(StringUtils.isNotBlank(factTextConfig.tail) && StringUtils.isNotEmpty(factTextConfig.tail)) {
				allmoney = this.extractMoneyUsedModel(factTextConfig.tail, casecauseModel.getCasecause(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				if(this.isOneEffectMoney(allmoney, label)) {
					return label;
				}
			}
			// 如果存在body,则进行详细抽取
			if(StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
				allmoney = this.extractMoneyUsedModel(factTextConfig.body, casecauseModel.getCasecause(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				return this.extractMoneyFromFactBody(allmoney, factTextConfig.body, caseMoney);
			}
		}
		return null;
	}

	public List<MatchModel> extractMoneyUsedModel(String content, String caseCause, JSONObject moneyCurrency) {
		List<MatchModel> allMoney = new ArrayList();
		if(StringUtils.isNotEmpty(content)) {
			List<String> contentList = Arrays.asList(content.split("\n"));
			String lastLine = "";
			for(String line : contentList) {
				List<MatchModel> matchModelList = extractMoney(line, moneyCurrency);
				lastLine = lastLine + line + "\n";
				if(matchModelList != null) {
					// 提取溯源
					matchModelList = addPosOffset(matchModelList, lastLine.length());
					allMoney.addAll(matchModelList);
				}
			}
			if(allMoney!=null) {
				String containMoneyShortSentence = "";
				for (MatchModel matchModel : allMoney) {
					containMoneyShortSentence += matchModel.getMatchShortSentence() + "\n";
				}
				JSONObject reqPara = new JSONObject();
				reqPara.put("caseCause", caseCause);
				reqPara.put("content", containMoneyShortSentence);
				String result = HttpRequest.sendPost(modelUrl, reqPara);
				if(StringUtils.isNotEmpty(result)) {
					List<String> resultList= CommonTools.string2List(result);
					if (resultList!=null) {
						for (int i = 0; i < resultList.size(); ++i) {
							if (resultList.get(i).equalsIgnoreCase("1")) {
								allMoney.get(i).setType("有效");
								if (CommonTools.isMatchPattern(allMoney.get(i).getMatchText(), negativeRule)) {
									allMoney.get(i).setType("无效");
									if (CommonTools.isMatchPattern(allMoney.get(i).getMatchText(), convertRule))
										allMoney.get(i).setIsConvert(true);
									else allMoney.get(i).setIsConvert(false);
								}
							} else {
								allMoney.get(i).setType("无效");
								if (CommonTools.isMatchPattern(allMoney.get(i).getMatchText(), mustRule)) {
									allMoney.get(i).setType("有效");
								} else {
									if (CommonTools.isMatchPattern(allMoney.get(i).getMatchText(), convertRule))
										allMoney.get(i).setIsConvert(true);
									else allMoney.get(i).setIsConvert(false);
								}
							}
						}
					}
				}
			}
		}
		return allMoney;
	}
}
