package com.cjbdi.core.configurecenter.loadmodel;

import com.cjbdi.core.configurecenter.BeanConfigCenter;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.util.*;

public class CasecauseLoadModel {
	private Map<String, List<MultiLayerNetwork>> boolModelMap = new HashMap<>();
	private Map<String, LinkedHashMap<String, Double>> boolIDFMap = new HashMap<>();
	private Map<String, List<String>> boolBagwordsMap = new HashMap<>();
	private List<MultiLayerNetwork> moneyModelSimpleList;
	private LinkedHashMap<String, Double> moneyIDF = new LinkedHashMap<>();
	private List<String> moneyBagwords = new ArrayList<>();

	public CasecauseLoadModel(String casecause) {
		Map<String, HashMap<String, Object>> features = BeanConfigCenter.configPlace.getFeatures().get(casecause);
		if (features!=null) {
			for (String feature : features.keySet()) {
				if (!feature.equals("type")) {
					String path = BeanConfigCenter.configPlace.getFeatures().get(casecause).get(feature).get("path").toString();
					String code = BeanConfigCenter.configPlace.getFeatures().get(casecause).get(feature).get("code").toString();
					int max_words = Integer.parseInt(BeanConfigCenter.configPlace.getFeatures().get(casecause).get(feature).get("max_words").toString());
					if (!feature.equals("moneysimple")) {
						BasicLoadmodel basicLoadmodel = new BasicLoadmodel(path, max_words);
						boolModelMap.put(code, basicLoadmodel.getModel());
						boolIDFMap.put(code, basicLoadmodel.getIdf());
						boolBagwordsMap.put(code, basicLoadmodel.getBagwords());
					} else if (feature.equals("moneysimple")) {
						BasicLoadmodel basicLoadmodel = new BasicLoadmodel(path, max_words);
						moneyModelSimpleList = basicLoadmodel.getModel();
						moneyIDF = basicLoadmodel.getIdf();
						moneyBagwords = basicLoadmodel.getBagwords();
					}
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

	public List<MultiLayerNetwork> getMoneyModelSimpleList() {
		return moneyModelSimpleList;
	}

	public void setMoneyModelSimpleList(List<MultiLayerNetwork> moneyModelSimpleList) {
		this.moneyModelSimpleList = moneyModelSimpleList;
	}

	public Map<String, LinkedHashMap<String, Double>> getBoolIDFMap() {
		return boolIDFMap;
	}

	public void setBoolIDFMap(Map<String, LinkedHashMap<String, Double>> boolIDFMap) {
		this.boolIDFMap = boolIDFMap;
	}

	public LinkedHashMap<String, Double> getMoneyIDF() {
		return moneyIDF;
	}

	public void setMoneyIDF(LinkedHashMap<String, Double> moneyIDF) {
		this.moneyIDF = moneyIDF;
	}

	public Map<String, List<String>> getBoolBagwordsMap() {
		return boolBagwordsMap;
	}

	public void setBoolBagwordsMap(Map<String, List<String>> boolBagwordsMap) {
		this.boolBagwordsMap = boolBagwordsMap;
	}

	public List<String> getMoneyBagwords() {
		return moneyBagwords;
	}

	public void setMoneyBagwords(List<String> moneyBagwords) {
		this.moneyBagwords = moneyBagwords;
	}
}
