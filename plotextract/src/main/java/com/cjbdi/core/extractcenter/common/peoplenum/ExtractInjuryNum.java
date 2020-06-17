package com.cjbdi.core.extractcenter.common.peoplenum;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.DefendantModel;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.utils.CN2Num;
import com.cjbdi.core.utils.CommonTools;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ExtractInjuryNum {
	public List<Label> doExtract(DefendantModel defendantModel, CasecauseModel casecauseModel) {
		InjuryNumExtractor injuryNumExtractor = new InjuryNumExtractor();
		String caseId = CommonTools.getCaseId(casecauseModel.getCasecause());
		if (StringUtils.isEmpty(caseId)) return null;
		// 获取案由和共有情节树
		JSONObject caseCauseFeatureList = BeanFactoryConfig.extractFeatureConfig.getFeature().getJSONObject(caseId);
		JSONObject commonFeatureList = BeanFactoryConfig.extractFeatureConfig.getFeature().getJSONObject("common");
		List<Label> labelList = new ArrayList<>();
		for (String featureId : caseCauseFeatureList.keySet()) {
			ExtractFeatureModel extractFeatureModel = caseCauseFeatureList.getObject(featureId, ExtractFeatureModel.class).getExtractFeatureModelNew();
			Label label = extractInjuryNum(defendantModel, casecauseModel, extractFeatureModel, commonFeatureList, injuryNumExtractor, featureId);
			if (label!=null) labelList.add(label);
			label = extractDisableNum(defendantModel, casecauseModel, extractFeatureModel, commonFeatureList, injuryNumExtractor, featureId);
			if (label!=null) labelList.add(label);
		}
		return labelList;
	}

	public Label extractInjuryNum(DefendantModel defendantModel, CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel, JSONObject commonFeatureList,
								  InjuryNumExtractor injuryNumExtractor, String featureId) {
		if (extractFeatureModel.getType().equals("私有人数")) {
			for (String commonId : commonFeatureList.keySet()) {
				if (commonId.equals(extractFeatureModel.getCommonCode())) {
					ExtractFeatureModel extractFeatureModelCommon = commonFeatureList.getObject(commonId, ExtractFeatureModel.class).getExtractFeatureModelNew();
					Label label = injuryNumExtractor.doExtract(defendantModel, casecauseModel, extractFeatureModelCommon);
					if (label!=null) {
						label.setCode(featureId);
						label.setCaseCause(casecauseModel.getCasecause());
						label.setChiName(extractFeatureModel.getName());
						return label;
					}
					break;
				}
			}
		}
		return null;
	}

	public Label extractDisableNum(DefendantModel defendantModel, CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel, JSONObject commonFeatureList,
								   InjuryNumExtractor injuryNumExtractor, String featureId) {
		if (extractFeatureModel.getType().equals("私有伤残人数")) {
			for (String commonId : commonFeatureList.keySet()) {
				if (commonId.equals(extractFeatureModel.getCommonCode())) {
					ExtractFeatureModel extractFeatureModelCommon = commonFeatureList.getObject(commonId, ExtractFeatureModel.class).getExtractFeatureModelNew();
					normalizeExtractFeatureModel(extractFeatureModelCommon);
					Label label = injuryNumExtractor.doExtract(defendantModel, casecauseModel, extractFeatureModelCommon);
					if (label != null) {
						label.setCode(featureId);
						label.setCaseCause(casecauseModel.getCasecause());
						label.setChiName(extractFeatureModel.getName());
						return label;
					}
					break;
				}
			}
		}
		return null;
	}

	public void normalizeExtractFeatureModel(ExtractFeatureModel extractFeatureModel) {
		String rule = "([一二三四五六七八九十两])级伤残人数";
		String level = CommonTools.matchTextFirstGroup(extractFeatureModel.getName(), rule);
		int disableLevel = 0;
		if (StringUtils.isNotEmpty(level) && CN2Num.isChineseNum(level)) disableLevel = CN2Num.cn2Num(level);
		if (disableLevel >0) {
			// 替换keyWord中的占位符
			extractFeatureModel.setKeyWord(extractFeatureModel.getKeyWord().replaceAll("占位", level + disableLevel));
			// 替换noiseWord中占位符
			String levels = "0123456789一二三四五六七八九十两零";
			levels = CommonTools.mosicText(levels, "[" + level + disableLevel + "]");
			extractFeatureModel.setNoiseWord(extractFeatureModel.getNoiseWord().replaceAll("占位", levels));
			// 替换提取规则中的占位符
			extractFeatureModel.setSumPositiveRule(CommonTools.replacePlaceHolder(extractFeatureModel.getSumPositiveRule(), "占位", level + disableLevel));
			extractFeatureModel.setPositivePureRule(CommonTools.replacePlaceHolder(extractFeatureModel.getPositivePureRule(), "占位", level + disableLevel));
		}
	}
}
