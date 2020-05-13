package com.cjbdi.core.servercenter;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configurecenter.BeanConfigCenter;
import com.cjbdi.core.utils.TfidfUtils;
import com.cjbdi.core.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sklearn.feature_extraction.text.CountVectorizer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Context;
import java.nio.channels.SelectionKey;
import java.util.*;

@RestController
public class SentenceExtractServer {

	@RequestMapping(value = {"/model/extract/bool/feature"}, produces = {"application/json;charset=UTF-8"})
	public String extractVector(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
		if(reqParam.containsKey("content") && reqParam.containsKey("casecause")) {
			JSONObject result = new JSONObject();
			String content = reqParam.getString("content");
			String casecause = reqParam.getString("casecause");
			String feature = reqParam.getString("feature");
			if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(casecause) && StringUtils.isNotEmpty(feature)) {
				if (BeanConfigCenter.casecauseModel.getModelMap().containsKey(casecause) &&
						BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolIDFMap().containsKey(feature)) {
					List<MultiLayerNetwork> modelList = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolModelMap().get(feature);
					LinkedHashMap<String, Double> idf = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolIDFMap().get(feature);
					List<String> bagwords = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolBagwordsMap().get(feature);
					List<Double> tfidf = TfidfUtils.run(content, bagwords, idf);
					InputStream inputStrem = new ByteArrayInputStream(Tools.list2str(tfidf).getBytes());
					try {
						INDArray indArray = Nd4j.readNumpy(inputStrem, ",");
						int num_neg = 0;
						int num_pos = 0;
						for (MultiLayerNetwork multiLayerNetwork : modelList) {
							double ratio = multiLayerNetwork.output(indArray).getDouble(0);
							if (ratio > 0.5) num_neg++;
							else num_pos++;
						}
						if (num_neg >= num_pos) result.put(feature, "0");
						else result.put(feature, "1");
					} catch (Exception e) {
						e.printStackTrace();
					}
					return result.toJSONString();
				}
			}
		}
		return "";
	}

	/*
	@RequestMapping(value = {"/model/extract/money/simple"}, produces = {"application/json;charset=UTF-8"})
	public String extractMoneySimple(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
		if(reqParam.containsKey("content") && reqParam.containsKey("casecause")) {
			String content = reqParam.getString("content");
			String casecause = reqParam.getString("casecause");
			if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(casecause)) {
				if (BeanConfigCenter.casecauseModel.getModelMap().containsKey(casecause)) {
					JSONObject result = new JSONObject();
					List<String> contentList = Arrays.asList(content.split("\n"));
					TfidfVectorizer vectorizer = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getMoneyTfidfVectorizer();
					List<MultiLayerNetwork> multiLayerNetworkList = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getMoneyModelSimpleList();
					int count = 1;
					for (String line : contentList) {
						INDArray indArray = vectorizer.transform(line);
						int num_neg = 0;
						int num_pos = 0;
						for (MultiLayerNetwork multiLayerNetwork : multiLayerNetworkList) {
							double ratio = multiLayerNetwork.output(indArray).getDouble(0);
							if (ratio > 0.5) num_neg++;
							else num_pos++;
						}
						if (num_neg >= num_pos) result.put(String.valueOf(count), "0");
						else result.put(String.valueOf(count), "1");
						count++;
					}
					return result.toString();
				}
			}
		}
		return "";
	}
	*/
}
