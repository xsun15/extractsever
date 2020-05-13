///*
//package com.cjbdi.core.servercenter;
//
//import com.alibaba.fastjson.JSONObject;
//import com.cjbdi.core.configurecenter.BeanConfigCenter;
//import org.apache.commons.lang.StringUtils;
//import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
//import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
//import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
//import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
//import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
//import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.factory.Nd4j;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.core.Context;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//@RestController
//public class SentenceExtractServerBak {
//
//	@RequestMapping(value = {"/model/extract/vector"}, produces = {"application/json;charset=UTF-8"})
//	public String extractVector(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
//		if(reqParam.containsKey("content") && reqParam.containsKey("casecause")) {
//			JSONObject result = new JSONObject();
//			String content = reqParam.getString("content");
//			String casecause = reqParam.getString("casecause");
//			String feature = reqParam.getString("feature");
//			if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(casecause) && StringUtils.isNotEmpty(feature)) {
//				if (BeanConfigCenter.casecauseModel.getModelMap().containsKey(casecause) &&
//						BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolVectorizerMap().containsKey(feature)) {
//					 TfidfVectorizer vectorizer = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolVectorizerMap().get(feature);
//					List<MultiLayerNetwork> modelList = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolModelMap().get(feature);
//					try {
//						TokenizerFactory t = new DefaultTokenizerFactory();
//						t.setTokenPreProcessor(new CommonPreprocessor());
//						String filePath = "/home/xrsun/extractsever/extractmodel/src/main/resources/model/steal/inhome/x_train.txt";
//						SentenceIterator iter = new BasicLineIterator(filePath);
//						vectorizer = new TfidfVectorizer.Builder()
//								.setIterator(iter)
//								.setTokenizerFactory(t)
//								.build();
//						vectorizer.fit();
//						System.out.println(content	);
//
//						sklearn.feature_extraction.text.TfidfVectorizer tfidfVectorizer = new sklearn.feature_extraction.text.TfidfVectorizer("cdcd", "cd");
//						tfidfVectorizer.getPyModule();
//						INDArray indArray = vectorizer.transform(content);
//						indArray = Nd4j.readNumpy("test.txt", ",");
//						int num_neg = 0;
//						int num_pos = 0;
//						for (MultiLayerNetwork multiLayerNetwork : modelList) {
//							INDArray indArray1 = multiLayerNetwork.output(indArray);
//							double ratio = multiLayerNetwork.output(indArray).getDouble(0);
//							if (ratio > 0.5) num_neg++;
//							else num_pos++;
//						}
//						if (num_neg >= num_pos) result.put(feature, "0");
//						else result.put(feature, "1");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					return result.toJSONString();
//				}
//			}
//		}
//		return "";
//	}
//
//	@RequestMapping(value = {"/model/extract/boolfeature"}, produces = {"application/json;charset=UTF-8"})
//	public String extractBoolFeature(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
//		if(reqParam.containsKey("content") && reqParam.containsKey("casecause")) {
//			String content = reqParam.getString("content");
//			String casecause = reqParam.getString("casecause");
//			if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(casecause)) {
//				if (BeanConfigCenter.casecauseModel.getModelMap().containsKey(casecause)) {
//					JSONObject result = new JSONObject();
//					Map<String, TfidfVectorizer> vectorizerMap = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolVectorizerMap();
//					Map<String, List<MultiLayerNetwork>> multiLayerNetworkMap = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolModelMap();
//					Set<String> featureSet = vectorizerMap.keySet();
//					for (String feature : featureSet) {
//						TfidfVectorizer tfidfVectorizer = vectorizerMap.get(feature);
//						INDArray indArray = tfidfVectorizer.transform(content);
//						int num_neg = 0;
//						int num_pos = 0;
//						for (MultiLayerNetwork multiLayerNetwork : multiLayerNetworkMap.get(feature)) {
//							double ratio = multiLayerNetwork.output(indArray).getDouble(0);
//							if (ratio > 0.5) num_neg++;
//							else num_pos++;
//						}
//						if (num_neg >= num_pos) result.put(feature, "0");
//						else result.put(feature, "1");
//					}
//					return result.toString();
//				}
//			}
//		}
//		return "";
//	}
//
//	@RequestMapping(value = {"/model/extract/money/simple"}, produces = {"application/json;charset=UTF-8"})
//	public String extractMoneySimple(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
//		if(reqParam.containsKey("content") && reqParam.containsKey("casecause")) {
//			String content = reqParam.getString("content");
//			String casecause = reqParam.getString("casecause");
//			if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(casecause)) {
//				if (BeanConfigCenter.casecauseModel.getModelMap().containsKey(casecause)) {
//					JSONObject result = new JSONObject();
//					List<String> contentList = Arrays.asList(content.split("\n"));
//					TfidfVectorizer vectorizer = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getMoneyTfidfVectorizer();
//					List<MultiLayerNetwork> multiLayerNetworkList = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getMoneyModelSimpleList();
//					int count = 1;
//					for (String line : contentList) {
//						INDArray indArray = vectorizer.transform(line);
//						int num_neg = 0;
//						int num_pos = 0;
//						for (MultiLayerNetwork multiLayerNetwork : multiLayerNetworkList) {
//							double ratio = multiLayerNetwork.output(indArray).getDouble(0);
//							if (ratio > 0.5) num_neg++;
//							else num_pos++;
//						}
//						if (num_neg >= num_pos) result.put(String.valueOf(count), "0");
//						else result.put(String.valueOf(count), "1");
//						count++;
//					}
//					return result.toString();
//				}
//			}
//		}
//		return "";
//	}
//}
//*/
