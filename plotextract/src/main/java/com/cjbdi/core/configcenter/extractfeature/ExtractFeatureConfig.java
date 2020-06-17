package com.cjbdi.core.configcenter.extractfeature;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.extractfeature.money.CurrencyModel;
import com.cjbdi.core.configcenter.extractfeature.money.ExtractCurrencyModel;
import com.cjbdi.core.configcenter.extractfeature.money.ExtractMoneyModel;
import com.cjbdi.core.utils.CN2Num;
import com.cjbdi.core.utils.CommonTools;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ExtractFeatureConfig {
	private JSONObject feature;
	// 各案由金额抽取规则
	private JSONObject caseMoney;
	// 汇率转化
	private JSONObject moneyCurrency;
	// 各案由金额抽取策略
	private JSONObject moneyControl;
	// 克数抽取规则
	private JSONObject gram;
	// 转换为克数比率
	private JSONObject gramRatio;


	public ExtractFeatureConfig() {
		feature = loadFeature(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getFeature());
		// 加载金额相关配置
		caseMoney = loadMoney(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getMoney());
		moneyCurrency = loadCurrency(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getMoney());
		moneyControl = loadMoneyControl(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getMoney());
		// 加载克数相关配置
		gram = loadMoney(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getGram());
		gramRatio = loadCurrency(BeanFactoryConfig.configPlace.getLxFeatureConfigPlace().getGram());
	}

	public JSONObject loadMoneyControl(JSONObject configPlaces) {
		Yaml yaml = new Yaml();
		JSONObject config = new JSONObject();
		for (String fileName : configPlaces.keySet()) {
			try {
				if (fileName.equalsIgnoreCase("control")) {
					String place = configPlaces.getString(fileName);
					HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
					for (String caseId : hashMap.keySet()) {
						JSONObject oneCaseId = new JSONObject();
						if (hashMap.get(caseId).containsKey("method") && hashMap.get(caseId).get("method")!=null)
							oneCaseId.put("method", hashMap.get(caseId).get("method"));
						if (hashMap.get(caseId).containsKey("modelUrl") && hashMap.get(caseId).get("modelUrl")!=null)
							oneCaseId.put("modelUrl", hashMap.get(caseId).get("modelUrl"));
						config.put(caseId, oneCaseId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return config;
	}

	public JSONObject loadCurrency(JSONObject configPlaces) {
		Yaml yaml = new Yaml();
		JSONObject config = new JSONObject();
		for (String caseId : configPlaces.keySet()) {
			try {
				if (caseId.equalsIgnoreCase("currency")) {
					String place = configPlaces.getString(caseId);
					HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
					ExtractCurrencyModel extractCurrencyModel = new ExtractCurrencyModel();
					boolean enDecrypt = BeanFactoryConfig.configPlace.getEnDecryptConfigPlace().getIsEncrypt();
					if (hashMap.get("placeHolder") !=null) {
						extractCurrencyModel.setPlaceHolderNum(hashMap.get("placeHolder").get("placeHolderNum").toString());
						extractCurrencyModel.setPlaceHolderUnit(hashMap.get("placeHolder").get("placeHolderUnit").toString());
						List<String> ruleList = CommonTools.object2LS(hashMap.get("placeHolder").get("rule"), enDecrypt);
						List<String> ruleListNew = new ArrayList<>();
						if (ruleList!=null) {
							for (String rule : ruleList) {
								rule = rule.replaceAll("placeHolderNum", extractCurrencyModel.getPlaceHolderNum());
								rule = rule.replaceAll("placeHolderUnit", extractCurrencyModel.getPlaceHolderUnit());
								ruleListNew.add(rule);
							}
						}
						extractCurrencyModel.setRule(ruleListNew);
					}
					if (hashMap.get("currency") !=null) {
						for (String currency : hashMap.get("currency").keySet()) {
							CurrencyModel currencyModel = new CurrencyModel();
							currencyModel.setName(((Map)hashMap.get("currency").get(currency)).get("name").toString());
							currencyModel.setRatio(Double.parseDouble(((Map)hashMap.get("currency").get(currency)).get("ratio").toString()));
							List<String> ruleList = extractCurrencyModel.getRule();
							List<Pattern> ruleListNew = new ArrayList<>();
							if (ruleList!=null) {
								for (String rule : ruleList) {
									rule = rule.replaceAll("name", currencyModel.getName());
									ruleListNew.add(Pattern.compile(rule));
								}
							}
							currencyModel.setRule(ruleListNew);
							config.put(currency, currencyModel);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return config;
	}

	public JSONObject loadMoney(JSONObject configPlaces) {
		Yaml yaml = new Yaml();
		JSONObject config = new JSONObject();
		for (String caseId : configPlaces.keySet()) {
			try {
				if (CN2Num.isNum(caseId) || caseId.equalsIgnoreCase("gram")) {
					String place = configPlaces.getString(caseId);
					HashMap<String, HashMap<String, ExtractMoneyModel>> hashMap = yaml.load(new FileInputStream(place));
					JSONObject oneCaseId = new JSONObject();
					for (String level : hashMap.keySet()) {
						if (hashMap.get(level)==null) continue;
						JSONObject oneLevel = new JSONObject();
						if (hashMap.get(level).containsKey("1th") && hashMap.get(level).get("1th")!=null)
							oneLevel.put("1th", hashMap.get(level).get("1th"));
						if (hashMap.get(level).containsKey("2th") && hashMap.get(level).get("2th")!=null)
							oneLevel.put("2th", hashMap.get(level).get("2th"));
						if (hashMap.get(level).containsKey("3th") && hashMap.get(level).get("3th")!=null)
							oneLevel.put("3th", hashMap.get(level).get("3th"));
						if (hashMap.get(level).containsKey("11th") && hashMap.get(level).get("11th")!=null)
							oneLevel.put("11th", hashMap.get(level).get("11th"));
						oneCaseId.put(level, oneLevel);
					}
					config.put(caseId, oneCaseId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return config;
	}

	public JSONObject loadFeature(JSONObject configPlaces) {
		Yaml yaml = new Yaml();
		JSONObject config = new JSONObject();
		for (String caseId : configPlaces.keySet()) {
			if (caseId.equalsIgnoreCase("currency")) continue;
			try {
				String place = configPlaces.getString(caseId);
				HashMap<String, HashMap<String, Object>> hashMap = yaml.load(new FileInputStream(place));
				JSONObject oneCaseId = new JSONObject();
				for (String id : hashMap.keySet()) {
					ExtractFeatureModel extractFeatureModel = new ExtractFeatureModel();
					if (hashMap.get(id).containsKey("name") && hashMap.get(id).get("name")!=null )
						extractFeatureModel.setName(hashMap.get(id).get("name").toString());
					if (hashMap.get(id).containsKey("type") && hashMap.get(id).get("type")!=null )
						extractFeatureModel.setType(hashMap.get(id).get("type").toString());
					if (hashMap.get(id).containsKey("ptype") && hashMap.get(id).get("ptype")!=null )
						extractFeatureModel.setPtype(hashMap.get(id).get("ptype").toString());
					if (hashMap.get(id).containsKey("commonCode") && hashMap.get(id).get("commonCode")!=null )
						extractFeatureModel.setPtype(hashMap.get(id).get("commonCode").toString());
					if (hashMap.get(id).containsKey("keyWord") && hashMap.get(id).get("keyWord")!=null)
						extractFeatureModel.setKeyWord(hashMap.get(id).get("keyWord").toString());
					if (hashMap.get(id).containsKey("noiseWord") && hashMap.get(id).get("noiseWord")!=null )
						extractFeatureModel.setNoiseWord(hashMap.get(id).get("noiseWord").toString());
					if (hashMap.get(id).containsKey("usedClass") && hashMap.get(id).get("usedClass")!=null )
						extractFeatureModel.setUsedClass(hashMap.get(id).get("usedClass").toString());
					if (hashMap.get(id).containsKey("modelUrl") && hashMap.get(id).get("modelUrl")!=null )
						extractFeatureModel.setModelUrl(hashMap.get(id).get("modelUrl").toString());
					boolean enDecrypt = BeanFactoryConfig.configPlace.getEnDecryptConfigPlace().getIsEncrypt();
					if (hashMap.get(id).containsKey("sumPositivePureRule") && hashMap.get(id).get("sumPositivePureRule")!=null )
						extractFeatureModel.setSumPositiveRule(CommonTools.object2LP(hashMap.get(id).get("sumPositivePureRule"), enDecrypt));
					if (hashMap.get(id).containsKey("positivePureRule") && hashMap.get(id).get("positivePureRule")!=null )
						extractFeatureModel.setPositivePureRule(CommonTools.object2LP(hashMap.get(id).get("positivePureRule"), enDecrypt));
					if (hashMap.get(id).containsKey("positiveModelRule") && hashMap.get(id).get("positiveModelRule")!=null )
						extractFeatureModel.setPositiveModelRule(CommonTools.object2LP(hashMap.get(id).get("positiveModelRule"), enDecrypt));
					if (hashMap.get(id).containsKey("negativePureRule") && hashMap.get(id).get("negativePureRule")!=null )
						extractFeatureModel.setNegativePureRule(CommonTools.object2LP(hashMap.get(id).get("negativePureRule"), enDecrypt));
					if (hashMap.get(id).containsKey("negativeModelRule") && hashMap.get(id).get("negativeModelRule")!=null )
						extractFeatureModel.setNegativeModelRule(CommonTools.object2LP(hashMap.get(id).get("negativeModelRule"), enDecrypt));
					if (hashMap.get(id).containsKey("rule") && hashMap.get(id).get("rule")!=null )
						extractFeatureModel.setRule(CommonTools.object2LP(hashMap.get(id).get("rule"), enDecrypt));
					if (hashMap.get(id).containsKey("ratio") && hashMap.get(id).get("ratio")!=null )
						extractFeatureModel.setRatio(CommonTools.object2MP(hashMap.get(id).get("ratio"), enDecrypt));
					oneCaseId.put(id, extractFeatureModel);
				}
				config.put(caseId, oneCaseId);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return config;
	}

	public JSONObject getFeature() {
		return feature;
	}

	public void setFeature(JSONObject feature) {
		this.feature = feature;
	}

	public JSONObject getCaseMoney() {
		return caseMoney;
	}

	public void setCaseMoney(JSONObject caseMoney) {
		this.caseMoney = caseMoney;
	}

	public JSONObject getMoneyCurrency() {
		return moneyCurrency;
	}

	public void setMoneyCurrency(JSONObject moneyCurrency) {
		this.moneyCurrency = moneyCurrency;
	}

	public JSONObject getMoneyControl() {
		return moneyControl;
	}

	public void setMoneyControl(JSONObject moneyControl) {
		this.moneyControl = moneyControl;
	}

	public JSONObject getGram() {
		return gram;
	}

	public void setGram(JSONObject gram) {
		this.gram = gram;
	}

	public JSONObject getGramRatio() {
		return gramRatio;
	}

	public void setGramRatio(JSONObject gramRatio) {
		this.gramRatio = gramRatio;
	}
}
