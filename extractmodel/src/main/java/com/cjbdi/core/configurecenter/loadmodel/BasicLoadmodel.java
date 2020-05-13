package com.cjbdi.core.configurecenter.loadmodel;

import com.cjbdi.core.configurecenter.BeanConfigCenter;
import com.cjbdi.core.utils.TfidfUtils;
import com.cjbdi.core.utils.Tools;
import org.bytedeco.opencv.presets.opencv_core;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer.Builder;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class BasicLoadmodel {
	private List<MultiLayerNetwork> model = new ArrayList<>();
	private LinkedHashMap<String, Double> idf= new LinkedHashMap<>();
	private List<String> bagwords = new ArrayList<>();

	public BasicLoadmodel(String path, int max_words) {
		URL url = this.getClass().getClassLoader().getResource("application.properties");
		List<String> list = Arrays.asList(url.getPath().split("classes"));
		String absolutePath = list.get(0) + "classes" + path;
		File file = new File(absolutePath);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isFile() && tempList[i].getName().contains("model")) {
				try {
					model.add(KerasModelImport.importKerasSequentialModelAndWeights(tempList[i].getAbsolutePath(), false));
				}catch (Exception e) {
					e.printStackTrace();
				}
			} else if (tempList[i].getName().contains("x_train")) {
				try {
					List<String> trainData = Tools.getFileContext(tempList[i].getAbsolutePath());
					for (String line : trainData) {
						String [] arrays = line.split("\t");
						idf.put(arrays[0].trim(), Double.parseDouble(arrays[1].replaceAll("\n", "").trim()));
						bagwords.add(arrays[0].trim());
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<MultiLayerNetwork> getModel() {
		return model;
	}

	public void setModel(List<MultiLayerNetwork> model) {
		this.model = model;
	}

	public LinkedHashMap<String, Double> getIdf() {
		return idf;
	}

	public void setIdf(LinkedHashMap<String, Double> idf) {
		this.idf = idf;
	}

	public List<String> getBagwords() {
		return bagwords;
	}

	public void setBagwords(List<String> bagwords) {
		this.bagwords = bagwords;
	}
}
