package com.cjbdi.core.convertlabelcenter.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.servercenter.utils.TraceSourceModel;

import java.util.*;

public class TraceSource {
	public static JSONArray run(JSONObject caseSplit, JSONArray caseDeepPortrait) {
		if (caseSplit!=null&&caseSplit.size()>0&&caseDeepPortrait!=null&&caseDeepPortrait.size()>0) {
			for (int i=0; i<caseDeepPortrait.size(); i++) {
				JSONObject defendantDeepPortrait = caseDeepPortrait.getJSONObject(i);
				JSONArray completeFeatures = defendantDeepPortrait.getJSONArray("completeList");
				for (int j=0; j<completeFeatures.size(); j++) {
					JSONObject percaseFetaures = completeFeatures.getJSONObject(j);
					JSONArray factLists = percaseFetaures.getJSONArray("factsList");
					convert(factLists, caseSplit);
					JSONArray plotList = percaseFetaures.getJSONArray("plotList");
					convert( plotList, caseSplit);
					percaseFetaures.put("factsList", factLists);
					percaseFetaures.put("plotList", plotList);
				}
			}
		}
		return caseDeepPortrait;
	}

	public static JSONArray convert(JSONArray extractList, JSONObject caseSplit) {
		for(int ruleList = 0; ruleList < extractList.size(); ++ruleList) {
			JSONObject extractFeature = extractList.getJSONObject(ruleList);
			Map<String, Object> traceSourceMap = extractFeature.getObject("traceSourceMap", Map.class);
			List<String> matchTextList = new ArrayList<>();
			List<Integer> startPosList = new ArrayList<>();
			List<String> parasList = new ArrayList<>();
			if (traceSourceMap!=null) {
				for (String lineText : traceSourceMap.keySet()) {
					JSONObject traceSource = (JSONObject) traceSourceMap.get(lineText);
					for (String key : caseSplit.keySet()) {
						String content = caseSplit.getString(key);
						if (content.contains(lineText)) {
							if (traceSource.getObject("matchText", List.class)!=null) matchTextList.addAll(traceSource.getObject("matchText", List.class));
							List<Integer> startpos = traceSource.getObject("startpos", List.class);
							if (startpos != null && startpos.size() > 0) {
								for (int i = 0; i < startpos.size(); i++) {
									startpos.set(i, startpos.get(i) + content.indexOf(lineText));
								}
								startPosList.addAll(startpos);
							}
							parasList.add(key);
							break;
						}
					}

				}
				extractFeature.put("featureContent", matchTextList);
				extractFeature.put("locationPara", parasList);
				extractFeature.put("locationIndex", startPosList);
			}
		}
		return extractList;
	}
}
