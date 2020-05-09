package com.cjbdi.core.configurecenter.loadmodel;

import com.cjbdi.core.configurecenter.BeanConfigCenter;
import org.apache.commons.lang.StringUtils;
import org.bytedeco.javacv.FrameFilter;
import org.bytedeco.opencv.presets.opencv_core;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VectorMain {
	public static void main(String [] args) {
		String casecause = "盗窃罪";
		String feature = "1001";
		BeanConfigCenter.init();
		TfidfVectorizer vectorizer = BeanConfigCenter.casecauseModel.getModelMap().get(casecause).getBoolVectorizerMap().get(feature);
		List<String> content = getFileContext("/home/xrsun/extractsever/extractmodel/src/main/resources/model/steal/withweapon/x_train.txt");
		if (content!=null&&!content.isEmpty()) {
			int size = content.size();
			OutputStream outputStream = new OutputStream() {
				@Override
				public void write(int b) throws IOException {

				}
			};

			for (int i=0; i<size; i++) {
				INDArray indArray = vectorizer.transform(content.get(i));
				try {
					Nd4j.writeTxt(indArray, "test.txt", ",");
					String arrayContent = getFile("test.txt");
					System.out.println(i);
					saveAsFileWriter("result.txt", arrayContent);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public static List<String> getFileContext(String path) {
		List<String> list = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				if (str.trim().length() > 0) {
					list.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static String getFile(String path) {
		String text = "";
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String str = "";
			while ((str = bufferedReader.readLine()) != null) {
				if (str.trim().length() > 0) {
					str = str.replaceAll(" ", "");
					text += str ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		text += "\n";
		return text;
	}

	private static void saveAsFileWriter(String savepath, String content) {
		FileWriter fwriter = null;
		try {
			// true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
			fwriter = new FileWriter(savepath, true);
			fwriter.write(content);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				fwriter.flush();
				fwriter.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
