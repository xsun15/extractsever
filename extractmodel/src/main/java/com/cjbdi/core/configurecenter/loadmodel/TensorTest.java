package com.cjbdi.core.configurecenter.loadmodel;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class TensorTest {


	public static void main(String[] args) {

		int[][] ret = new int[1][600];
		for (int i=0;i< 600;i++){
			ret[0][i] = 0;
		}
        //long out = predict(ret,loadModel("交通肇事罪_无驾驶资格.pb"));
//        System.out.println(out);

	}

	public static int[][]  pading(String doc,Map<String, Integer> wordToid){



		int[][] ret = new int[1][600];
		for (int i=0;i< 600;i++){
			ret[0][i] = 0;
		}
		for (int i=600-doc.length(), j=0; i< 600 && i >= 0; i++,j++){

			try{
				ret[0][i] = wordToid.get((doc.substring(j,j+1)));

				if (ret[0][i] >= 1412) {
					ret[0][i] = 0;
				}


			}catch (Exception e){
				ret[0][i] = wordToid.get("<PAD>");
			}

		}
		return ret;

	}

	public static Map<String, Integer> readVocab() {

		Map<String, Integer> wordToid  = new HashMap<>();
		String path = TensorTest.class.getClassLoader().getResource("dictionary.json").getPath();
		String path2 = null;
		try{
			path2 = URLDecoder.decode(path ,"utf-8");
		}catch (Exception e){
			e.printStackTrace();
		}

		try{
			String input = FileUtils.readFileToString(new File(path2),"utf-8");
			JSONObject jsonObject = JSONObject.parseObject(input);
			for (Object key : jsonObject.keySet()){
				wordToid.put(key.toString(),(int)jsonObject.get(key));

			}

		}catch (Exception e){
			e.printStackTrace();
		}

		return wordToid;
	}
	public static long predict(int[][] input, Session session){

		long[] res = new long[1];
		try {
//            Graph graph = new Graph();
//            graph.importGraphDef(modelBytes);
//            Session session = new Session(graph);
			Tensor out = session.runner().feed("input_x", Tensor.create(input)).feed("keep_prob",Tensor.create(1.0f)).fetch("score/out_put").run().get(0);
			out.copyTo(res);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res[0];

	}
	public static byte[] loadModel(String modelName){
		byte[] graphBytes = null;
		try {
			String path = TensorTest.class.getClassLoader().getResource(modelName).getPath();
			String pathUtf8 = null;
			try{
				pathUtf8 = URLDecoder.decode(path,"utf-8");

			}catch (Exception e){
				e.printStackTrace();
			}

			graphBytes = IOUtils.toByteArray(new FileInputStream(pathUtf8));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return graphBytes;

	}

}
