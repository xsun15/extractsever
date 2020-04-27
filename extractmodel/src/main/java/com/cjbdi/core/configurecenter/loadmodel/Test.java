/*
package com.cjbdi.core.configurecenter.loadmodel;


import com.alibaba.fastjson.JSONObject;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer.Builder;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main (String [] args) {
		try {
			String simpleMlp = new ClassPathResource("/model/traffic/withweapon/DNN_0.hdf5").getFile().getPath();

			// Keras Sequential models correspond to DL4J MultiLayerNetworks. We enforce loading the training configuration
			// of the model as well. If you're only interested in inference, you can safely set this to 'false'.
			MultiLayerNetwork model = KerasModelImport.importKerasSequentialModelAndWeights(simpleMlp, false);
			// Test basic inference on the model.
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("fullText", "江苏省   昆山市             凌晨   被告人   李   金荣   携带   折叠刀     铁棍   钻窗   进入   昆山市   中华   园   XX   超市     被害人   陈某     黄金首饰   柜台     窃得   项链   若干条       被害人   潘某     手机   柜台     窃得   人民币   元及   各类   手机   部   价值   人民币   余元     被告人   李   金荣   归案   后   如实   供述     犯罪事实     公诉机关认为   被告人   李   金荣   以非法占有为目的   携带   凶器   盗窃     人财物   价值   人民币   余元   数额巨大   应当     盗窃罪   追究其刑事责任     被告人   李   金荣     有期徒刑   执行   完毕后     五年     再犯   应当判处有期徒刑   以上   刑罚   之罪   系累犯   应当从重处罚     被告人   李   金荣   如实   供述   自己的   罪行       处罚     提请   本院   依法   判处                   凌晨   被告人   李   金荣   携带   折叠刀     铁棍   钻窗   进入   昆山市   中华   园   XX   超市     被害人   陈某     黄金首饰   柜台     窃得   项链   若干条       被害人   潘某     手机   柜台     窃得   人民币   五百元     各类   手机   部     部   价值   人民币   余元     被告人   李   金荣   归案   后   如实   供述     犯罪事实                 被告人   李   金荣   处   扣押     上述   被盗   手机   一部   已被   使用   现已   发还   被害人   潘某       扣押     人民币   元     CK   牌   手表   一块     折叠刀   一把   现   暂扣     昆山市   公安局  ");
			//	String result = HttpRequest.sendPost("http://127.0.0.1:8000/extract/tfidfvect/test", jsonObject);
			INDArray indArray = Nd4j.zeros(1, 52951);
			Double tmp = model.output(indArray).getDouble(1);
			System.out.println(tmp);
			Builder builder = new Builder();
			TokenizerFactory t = new DefaultTokenizerFactory();
			t.setTokenPreProcessor(new CommonPreprocessor());
			String filePath = new ClassPathResource("model/traffic/withweapon/x_train.txt").getFile().getAbsolutePath();

			SentenceIterator iter = new BasicLineIterator(filePath);
			TfidfVectorizer vec = new Builder()
					.setIterator(iter)
					.setTokenizerFactory(t)
					.build();
			vec.fit();
			//INDArray indArray = vec.transform("cdcd");
			//Double tmp = model.output(indArray).getDouble(1);
			System.out.println(indArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> getFileContext(String path) throws Exception {
		FileReader fileReader =new FileReader(path);
		BufferedReader bufferedReader =new BufferedReader(fileReader);
		List<String> list =new ArrayList<String>();
		String str=null;
		while((str=bufferedReader.readLine())!=null) {
			if(str.trim().length()>2) {
				list.add(str);
			}
		}
		return list;
	}
}
*/
