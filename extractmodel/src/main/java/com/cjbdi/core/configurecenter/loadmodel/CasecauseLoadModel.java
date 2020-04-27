package com.cjbdi.core.configurecenter.loadmodel;

import com.cjbdi.core.configurecenter.BeanConfigCenter;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CasecauseLoadModel {
	private Map<String, List<MultiLayerNetwork>> boolModelMap = new HashMap<>();
	private Map<String, TfidfVectorizer> boolVectorizerMap = new HashMap<>();
	private List<MultiLayerNetwork> moneyModelSimpleList;
	private TfidfVectorizer moneyTfidfVectorizer;

	public CasecauseLoadModel(String casecause) {
		Map<String, HashMap<String, Object>> features = BeanConfigCenter.configPlace.getFeatures().get(casecause);
		if (features!=null) {
			for (String feature : features.keySet()) {
				if (!feature.equals("type") && !feature.equals("moneysimple")) {
					String path = BeanConfigCenter.configPlace.getFeatures().get(casecause).get(feature).get("path").toString();
					String code = BeanConfigCenter.configPlace.getFeatures().get(casecause).get(feature).get("code").toString();
					BasicLoadmodel basicLoadmodel = new BasicLoadmodel(path);
					boolModelMap.put(code, basicLoadmodel.getModel());
					boolVectorizerMap.put(code, basicLoadmodel.getVectorizer());
				} else if (feature.equals("moneysimple")) {
					String path = BeanConfigCenter.configPlace.getFeatures().get(casecause).get(feature).get("path").toString();
					BasicLoadmodel basicLoadmodel = new BasicLoadmodel(path);
					moneyModelSimpleList = basicLoadmodel.getModel();
					moneyTfidfVectorizer = basicLoadmodel.getVectorizer();
				}
			}
		}
	}

	public Map<String, List<MultiLayerNetwork>> getBoolModelMap() {
		return boolModelMap;
	}

	public void setBoolModelMap(Map<String, List<MultiLayerNetwork>> boolModelMap) {
		this.boolModelMap = boolModelMap;
	}

	public Map<String, TfidfVectorizer> getBoolVectorizerMap() {
		return boolVectorizerMap;
	}

	public void setBoolVectorizerMap(Map<String, TfidfVectorizer> boolVectorizerMap) {
		this.boolVectorizerMap = boolVectorizerMap;
	}

	public List<MultiLayerNetwork> getMoneyModelSimpleList() {
		return moneyModelSimpleList;
	}

	public void setMoneyModelSimpleList(List<MultiLayerNetwork> moneyModelSimpleList) {
		this.moneyModelSimpleList = moneyModelSimpleList;
	}

	public TfidfVectorizer getMoneyTfidfVectorizer() {
		return moneyTfidfVectorizer;
	}

	public void setMoneyTfidfVectorizer(TfidfVectorizer moneyTfidfVectorizer) {
		this.moneyTfidfVectorizer = moneyTfidfVectorizer;
	}
}
