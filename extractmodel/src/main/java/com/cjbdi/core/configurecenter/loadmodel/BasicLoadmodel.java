package com.cjbdi.core.configurecenter.loadmodel;

import com.cjbdi.core.configurecenter.BeanConfigCenter;
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

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicLoadmodel {
	private List<MultiLayerNetwork> model = new ArrayList<>();
	private  TfidfVectorizer vectorizer;

	public BasicLoadmodel(String path) {
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
					TokenizerFactory t = new DefaultTokenizerFactory();
					t.setTokenPreProcessor(new CommonPreprocessor());
					String filePath = tempList[i].getAbsolutePath();
					SentenceIterator iter = new BasicLineIterator(filePath);
					 vectorizer= new Builder()
							.setIterator(iter)
							.setTokenizerFactory(t)
							.build();
					vectorizer.fit();
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

	public TfidfVectorizer getVectorizer() {
		return vectorizer;
	}

	public void setVectorizer(TfidfVectorizer vectorizer) {
		this.vectorizer = vectorizer;
	}
}
