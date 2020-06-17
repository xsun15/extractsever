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
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class PRExtractMoney extends BasicMoneyExtractor {
	private JSONObject caseMoney;
	private List<Pattern> negativeRule;
	private List<Pattern> positive1thRule;
	private List<Pattern> positive2thRule;
	private List<Pattern> positive3thRule;

	public PRExtractMoney (JSONObject caseMoney) {
		this.caseMoney = caseMoney;
		this.positive1thRule = new ArrayList<>();
		this.positive2thRule = new ArrayList<>();
		this.positive3thRule = new ArrayList<>();
		this.negativeRule = new ArrayList<>();
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
		if (caseMoney.containsKey("positivePureRule")) {
			if (!caseMoney.getJSONObject("positivePureRule").isEmpty()) {
				if (caseMoney.getJSONObject("positivePureRule").containsKey("3th"))
					positive3thRule.addAll(caseMoney.getJSONObject("positivePureRule").getObject("3th", ExtractMoneyModel.class).getRule());
			}
		}
		if (caseMoney.containsKey("negativePureRule")) {
			if (!caseMoney.getJSONObject("negativePureRule").isEmpty()) {
				if (caseMoney.getJSONObject("negativePureRule").containsKey("1th"))
					negativeRule.addAll(caseMoney.getJSONObject("negativePureRule").getObject("1th", ExtractMoneyModel.class).getRule());
				if (caseMoney.getJSONObject("negativePureRule").containsKey("2th"))
					negativeRule.addAll(caseMoney.getJSONObject("negativePureRule").getObject("2th", ExtractMoneyModel.class).getRule());
			}
		}
	}

	public Label doExtractNRule(CasecauseModel casecauseModel) {
		Label label = new Label();
		List<MatchModel> allmoney = null;
		// 先抽取本院认为段，如果本院认为段排除无效金额外，只剩下一个有效金额，则认为为抽取金额
		// 本处的有效金额判定只用到模型，未用正则
		if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
			allmoney = this.extractMoneyUsedNRule(casecauseModel.getOpinion(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
			if(isOneEffectMoney(allmoney, label)) {
				return label;
			}
		}
		// 抽取经审理查明段
		if(StringUtils.isNotEmpty(casecauseModel.getJustice())) {
			// 如果经审理查明段没有金额相关描述，则直接返回
			allmoney = this.extractMoneyUsedNRule(casecauseModel.getJustice(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
			if(allmoney == null || allmoney.size() == 0) {
				return null;
			}
			// 将经审理查明段分割为头段、body和尾段
			FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
			// 如果存在头段，且排除无效金额外，只剩下一个有效金额，则认为为抽取金额
			if(StringUtils.isNotBlank(factTextConfig.header) && StringUtils.isNotEmpty(factTextConfig.header)) {
				allmoney = this.extractMoneyUsedNRule(factTextConfig.header, BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				if(this.isOneEffectMoney(allmoney, label)) {
					return label;
				}
			}
			// 如果存在尾段，且排除无效金额外，只剩下一个有效金额，则认为为抽取金额
			if(StringUtils.isNotBlank(factTextConfig.tail) && StringUtils.isNotEmpty(factTextConfig.tail)) {
				allmoney = this.extractMoneyUsedNRule(factTextConfig.tail, BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				if(this.isOneEffectMoney(allmoney, label)) {
					return label;
				}
			}
			// 如果存在body,则进行详细抽取
			if(StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
				allmoney = this.extractMoneyUsedNRule(factTextConfig.body, BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				return this.extractMoneyFromFactBody(allmoney, factTextConfig.body, caseMoney);
			}
		}
		return null;
	}

	public Label doExtractNPRule(CasecauseModel casecauseModel) {
		Label label = new Label();
		List<MatchModel> allmoney = null;
		// 先抽取本院认为段，如果本院认为段排除无效金额外，只剩下一个有效金额，则认为为抽取金额
		// 本处的有效金额判定只用到模型，未用正则
		if(StringUtils.isNotEmpty(casecauseModel.getOpinion())) {
			allmoney = this.extractMoneyUsedNPRule(casecauseModel.getOpinion(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
			if(isOneEffectMoney(allmoney, label)) {
				return label;
			}
		}
		// 抽取经审理查明段
		if(StringUtils.isNotEmpty(casecauseModel.getJustice())) {
			// 如果经审理查明段没有金额相关描述，则直接返回
			allmoney = this.extractMoneyUsedNPRule(casecauseModel.getJustice(), BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
			if(allmoney == null || allmoney.size() == 0) {
				return null;
			}
			// 将经审理查明段分割为头段、body和尾段
			FactTextConfig factTextConfig = FactTextSplit.run(casecauseModel.getJustice());
			// 如果存在头段，且排除无效金额外，只剩下一个有效金额，则认为为抽取金额
			if(StringUtils.isNotBlank(factTextConfig.header) && StringUtils.isNotEmpty(factTextConfig.header)) {
				allmoney = this.extractMoneyUsedNPRule(factTextConfig.header, BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				if(this.isOneEffectMoney(allmoney, label)) {
					return label;
				}
			}
			// 如果存在尾段，且排除无效金额外，只剩下一个有效金额，则认为为抽取金额
			if(StringUtils.isNotBlank(factTextConfig.tail) && StringUtils.isNotEmpty(factTextConfig.tail)) {
				allmoney = this.extractMoneyUsedNPRule(factTextConfig.tail, BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				if(this.isOneEffectMoney(allmoney, label)) {
					return label;
				}
			}
			// 如果存在body,则进行详细抽取
			if(StringUtils.isNotBlank(factTextConfig.body) && StringUtils.isNotEmpty(factTextConfig.body)) {
				allmoney = this.extractMoneyUsedNPRule(factTextConfig.body, BeanFactoryConfig.extractFeatureConfig.getMoneyCurrency());
				return this.extractMoneyFromFactBody(allmoney, factTextConfig.body, caseMoney);
			}
		}
		return null;
	}

	public List<MatchModel> extractMoneyUsedNRule(String content, JSONObject moneyCurrency) {
		List<MatchModel> allMoney = new ArrayList();
		if (StringUtils.isNotEmpty(content)) {
			List<MatchModel> invalidMatchModelList = CommonTools.matchModelAll(content, negativeRule);
			if (!invalidMatchModelList.isEmpty()) allMoney.addAll(invalidMatchModelList);
			content = CommonTools.mosicText(content, negativeRule);
			List<MatchModel> effectMatchModelList = CommonTools.matchModelAll(content, moneyCurrency);
			if (!effectMatchModelList.isEmpty()) allMoney.addAll(effectMatchModelList);
			return allMoney;
		}
		return null;
	}

	public List<MatchModel> extractMoneyUsedNPRule(String content, JSONObject moneyCurrency) {
		List<MatchModel> allMoney = new ArrayList();
		if (StringUtils.isNotEmpty(content)) {
			List<MatchModel> invalidMatchModelList = CommonTools.matchModelAll(content, negativeRule);
			if (!invalidMatchModelList.isEmpty()) allMoney.addAll(invalidMatchModelList);
			content = CommonTools.mosicText(content, negativeRule);
			List<Pattern> ruleList = new ArrayList<>();
			if (!positive1thRule.isEmpty()) ruleList.addAll(positive1thRule);
			if (!positive2thRule.isEmpty()) ruleList.addAll(positive2thRule);
			if (!positive3thRule.isEmpty()) ruleList.addAll(positive3thRule);
			List<MatchModel> effectMatchModelList = CommonTools.matchModelAll(content, ruleList);
			if (!effectMatchModelList.isEmpty()) allMoney.addAll(effectMatchModelList);
			return allMoney;
		}
		return null;
	}
}
