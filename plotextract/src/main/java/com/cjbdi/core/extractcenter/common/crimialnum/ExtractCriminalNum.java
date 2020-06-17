package com.cjbdi.core.extractcenter.common.crimialnum;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureModel;
import com.cjbdi.core.extractcenter.common.peoplenum.InjuryNumExtractor;
import com.cjbdi.core.extractcenter.utils.caseportrait.CasecauseModel;
import com.cjbdi.core.extractcenter.utils.caseportrait.DefendantModel;
import com.cjbdi.core.extractcenter.utils.tracesource.Label;
import com.cjbdi.core.utils.CommonTools;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ExtractCriminalNum {
	public List<Label> doExtract(CasecauseModel casecauseModel) {
		CriminalNumExtractor criminalNumExtractor = new CriminalNumExtractor();
		String caseId = CommonTools.getCaseId(casecauseModel.getCasecause());
		if (StringUtils.isEmpty(caseId)) return null;
		// 获取案由和共有情节树
		JSONObject caseCauseFeatureList = BeanFactoryConfig.extractFeatureConfig.getFeature().getJSONObject(caseId);
		JSONObject commonFeatureList = BeanFactoryConfig.extractFeatureConfig.getFeature().getJSONObject("common");
		List<Label> labelList = new ArrayList<>();
		for (String featureId : caseCauseFeatureList.keySet()) {
			ExtractFeatureModel extractFeatureModel = caseCauseFeatureList.getObject(featureId, ExtractFeatureModel.class).getExtractFeatureModelNew();
			Label label = extractCriminalNum(casecauseModel, extractFeatureModel, commonFeatureList, criminalNumExtractor, featureId);
			if (label!=null) labelList.add(label);
		}
		return labelList;
	}

	public Label extractCriminalNum(CasecauseModel casecauseModel, ExtractFeatureModel extractFeatureModel, JSONObject commonFeatureList,
								  CriminalNumExtractor criminalNumExtractor, String featureId) {
		if (extractFeatureModel.getType().equals("私有犯罪次数")) {
			for (String commonId : commonFeatureList.keySet()) {
				if (commonId.equals(extractFeatureModel.getCommonCode())) {
					ExtractFeatureModel extractFeatureModelCommon = commonFeatureList.getObject(commonId, ExtractFeatureModel.class).getExtractFeatureModelNew();
					Label label = criminalNumExtractor.doExtract(casecauseModel, extractFeatureModelCommon);
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
}
