package com.cjbdi.intelJudge.configure;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.intelJudge.model.Similarity;
import com.cjbdi.intelJudge.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class Bagwords {
	private Map<String, List<String>> bagwordCase = new HashMap<>();
	private Map<String, LinkedHashMap<String, Double>> idfCase = new HashMap<>();
	private Map<String, List<SampleModel>> keywordCase = new HashMap<>();
	private Map<String, HashMap<String, Object>> detailConfigCase= new HashMap<>();
	private Map<String, Map<String, Integer>> retainWordCase = new HashMap<>();
	private List<String> stopwords = null;
	private Map<String, Integer> topKCase = new HashMap<>();
	private Map<String, Integer> topKSCase = new HashMap<>();
	private Map<String, String> casecause = new HashMap<>();
	private Integer thread = 1;
	private String eshost = null;
	private String esport = null;
	private String escluster = null;
	private String esindex = null;
	private String estype = null;
	private HashMap<String, HashMap<String, String>> plugService = new HashMap<>();
	private HashMap<String, HashMap<Integer, FDXModel>> fdx1Case = new HashMap<>();
	private HashMap<String, HashMap<Integer, FDXModel>> fdx2Case = new HashMap<>();

	public Bagwords() {
		Yaml yaml = new Yaml();
		// 获取es相关配置
		thread = Integer.parseInt(((Object)BeanConfigCenter.configPlace.getFeatures().get("storeEs").get("thread")).toString());
		eshost = ((Object)BeanConfigCenter.configPlace.getFeatures().get("storeEs").get("ip")).toString();
		esport = ((Object)BeanConfigCenter.configPlace.getFeatures().get("storeEs").get("port")).toString();
		escluster = ((Object)BeanConfigCenter.configPlace.getFeatures().get("storeEs").get("cluster")).toString();
		esindex = ((Object)BeanConfigCenter.configPlace.getFeatures().get("storeEs").get("esindex")).toString();
		estype = ((Object)BeanConfigCenter.configPlace.getFeatures().get("storeEs").get("estype")).toString();
		// 外挂服务配置
		for (String type : BeanConfigCenter.configPlace.getFeatures().get("plugservice").keySet()) {
			HashMap<String, String> jiekoumap = new HashMap<>();
			for (String str : BeanConfigCenter.configPlace.getFeatures().get("plugservice").get(type).keySet()) {
				if (StringUtils.isNotEmpty(str) && !str.equals("ip")) {
					String jiekou = BeanConfigCenter.configPlace.getFeatures().get("plugservice").get(type).get("ip").toString() +
							BeanConfigCenter.configPlace.getFeatures().get("plugservice").get(type).get(str).toString();
					jiekoumap.put(str, jiekou);
				}
			}
			plugService.put(type, jiekoumap);
		}
		HashMap<String, HashMap<String, Object>>  hashMap = BeanConfigCenter.configPlace.getFeatures().get("data");
		for (String key : hashMap.keySet()) {
			if (!key.equals("stopword")) {
				JSONObject caseConfig = new JSONObject(hashMap.get(key));
				// 读取bagword
				String relativepath = caseConfig.getString("bagword").replaceFirst("/", "");
				String rootpath = ClassLoader.getSystemResource(relativepath).getPath().split("target")[0] + "src/main/resources/";
				List<String> bagwords = Tools.getFileContextList(rootpath+relativepath);
				// 读取idf
				relativepath = caseConfig.getString("idf").replaceFirst("/", "");
				LinkedHashMap<String, Double> idf = Tools.getFileContextMap(rootpath+relativepath);
				// 读取keyword
				relativepath = caseConfig.getString("keyword").replaceFirst("/", "");
				List<SampleModel> keyword = Tools.getFileContextSampleModel(rootpath+relativepath);
				// 读取各案由自定义配置的关键词及权重等配置
				relativepath = caseConfig.getString("config");
				HashMap<String, HashMap<String, Object>> detail = yaml.load(Bagwords.class.getResourceAsStream(relativepath));
				// 保存保留词的权重，方便后面调取使用
				Map<String, Integer> retainWord = new HashMap<>();
				String tmp = StringUtils.replace(detail.get(key).get("retainWord").toString(), "[", "").replace("]", "");
				List<String> tmpList = Arrays.asList(tmp.split(","));
				List<String> retainList = new ArrayList<>();
				for (String tmp1 : tmpList) {
					retainList.addAll(Arrays.asList(tmp1.split("\\|")));
				}
				for (String wordTerm : retainList) {
					List<String> tmp1 = Arrays.asList(wordTerm.split("@"));
					System.out.println(wordTerm);
					retainWord.put(tmp1.get(0), Integer.parseInt(tmp1.get(1)));
				}
				// 保存量刑区间
				HashMap<Integer, FDXModel> map = new HashMap<>();
				for (int i=1; i<=((Map)caseConfig.get("fdx1")).size(); i++) {
					String range = ((Map)caseConfig.get("fdx1")).get(i).toString();
					FDXModel fdxModel = new FDXModel();
					fdxModel.minval = Integer.parseInt(range.split("-")[0]);
					fdxModel.maxval = Integer.parseInt(range.split("-")[1]);
					map.put(i, fdxModel);
				}
				fdx1Case.put(key, map);
				map = new HashMap<>();
				for (int i=1; i<=((Map)caseConfig.get("fdx2")).size(); i++) {
					String range = ((Map)caseConfig.get("fdx2")).get(i).toString();
					FDXModel fdxModel = new FDXModel();
					fdxModel.minval = Integer.parseInt(range.split("-")[0]);
					fdxModel.maxval = Integer.parseInt(range.split("-")[1]);
					map.put(i, fdxModel);
				}

				fdx2Case.put(key, map);
				bagwordCase.put(key, bagwords);
				idfCase.put(key, idf);
				keywordCase.put(key, keyword);
				detailConfigCase.put(key, detail.get(key));
				topKCase.put(key, caseConfig.getInteger("topk"));
				topKSCase.put(key, caseConfig.getInteger("topks"));
				casecause.put(key, caseConfig.getString("name"));
				retainWordCase.put(key, retainWord);
			} else {
				String relativepath = ((Object) hashMap.get("stopword")).toString().replaceFirst("/", "");
				String rootpath = ClassLoader.getSystemResource(relativepath).getPath().split("target")[0] + "src/main/resources/";
				stopwords= Tools.getFileContextList(rootpath+relativepath);
			}
		}
	}

	public Map<String, List<String>> getBagwordCase() {
		return bagwordCase;
	}

	public void setBagwordCase(Map<String, List<String>> bagwordCase) {
		this.bagwordCase = bagwordCase;
	}

	public Map<String, LinkedHashMap<String, Double>> getIdfCase() {
		return idfCase;
	}

	public void setIdfCase(Map<String, LinkedHashMap<String, Double>> idfCase) {
		this.idfCase = idfCase;
	}

	public List<String> getStopwords() {
		return stopwords;
	}

	public void setStopwords(List<String> stopwords) {
		this.stopwords = stopwords;
	}

	public Map<String, List<SampleModel>> getKeywordCase() {
		return keywordCase;
	}

	public void setKeywordCase(Map<String, List<SampleModel>> keywordCase) {
		this.keywordCase = keywordCase;
	}

	public Map<String, HashMap<String, Object>> getDetailConfigCase() {
		return detailConfigCase;
	}

	public void setDetailConfigCase(Map<String, HashMap<String, Object>> detailConfigCase) {
		this.detailConfigCase = detailConfigCase;
	}

	public Map<String, Integer> getTopKCase() {
		return topKCase;
	}

	public void setTopKCase(Map<String, Integer> topKCase) {
		this.topKCase = topKCase;
	}

	public Map<String, Map<String, Integer>> getRetainWordCase() {
		return retainWordCase;
	}

	public void setRetainWordCase(Map<String, Map<String, Integer>> retainWordCase) {
		this.retainWordCase = retainWordCase;
	}

	public Integer getThread() {
		return thread;
	}

	public void setThread(Integer thread) {
		this.thread = thread;
	}

	public String getEshost() {
		return eshost;
	}

	public void setEshost(String eshost) {
		this.eshost = eshost;
	}

	public String getEsport() {
		return esport;
	}

	public void setEsport(String esport) {
		this.esport = esport;
	}

	public String getEscluster() {
		return escluster;
	}

	public void setEscluster(String escluster) {
		this.escluster = escluster;
	}

	public String getEsindex() {
		return esindex;
	}

	public void setEsindex(String esindex) {
		this.esindex = esindex;
	}

	public String getEstype() {
		return estype;
	}

	public void setEstype(String estype) {
		this.estype = estype;
	}

	public Map<String, Integer> getTopKSCase() {
		return topKSCase;
	}

	public void setTopKSCase(Map<String, Integer> topKSCase) {
		this.topKSCase = topKSCase;
	}

	public Map<String, String> getCasecause() {
		return casecause;
	}

	public void setCasecause(Map<String, String> casecause) {
		this.casecause = casecause;
	}

	public HashMap<String, HashMap<String, String>> getPlugService() {
		return plugService;
	}

	public void setPlugService(HashMap<String, HashMap<String, String>> plugService) {
		this.plugService = plugService;
	}

	public HashMap<String, HashMap<Integer, FDXModel>> getFdx1Case() {
		return fdx1Case;
	}

	public void setFdx1Case(HashMap<String, HashMap<Integer, FDXModel>> fdx1Case) {
		this.fdx1Case = fdx1Case;
	}

	public HashMap<String, HashMap<Integer, FDXModel>> getFdx2Case() {
		return fdx2Case;
	}

	public void setFdx2Case(HashMap<String, HashMap<Integer, FDXModel>> fdx2Case) {
		this.fdx2Case = fdx2Case;
	}
}
