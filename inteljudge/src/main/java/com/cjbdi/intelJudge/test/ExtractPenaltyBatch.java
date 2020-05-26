package com.cjbdi.intelJudge.test;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.intelJudge.configure.BeanConfigCenter;
import com.cjbdi.intelJudge.configure.SampleModel;
import com.cjbdi.intelJudge.model.Similarity;
import com.cjbdi.intelJudge.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractPenaltyBatch {
	public static void main(String [] args) {
		BeanConfigCenter.init();
		TransportClient client = BeanConfigCenter.esclient.getClient();
		Map<String, String> data = Tools.getFileContext("/app/develop/extractsever/inteljudge/src/main/resources/rawdata/robbery_justice");
		int count = 0;
		String result = "";
		int errorNum = 30;
		int gap = 6;
		double [] errorArray = new double[errorNum];
		DecimalFormat df = new DecimalFormat(".00");
		for (String filename : data.keySet()) {
			// 获取当前案件刑期
			GetResponse response = client.prepareGet(BeanConfigCenter.bagwords.getEsindex(), BeanConfigCenter.bagwords.getEstype(), filename).get();
			if (StringUtils.isNotEmpty(response.getSourceAsString())) {
				JSONObject responseJson = JSONObject.parseObject(response.getSourceAsString());
				int penalty = responseJson.getInteger("principalPenalty");
				for (int i=0; i<errorNum; i++) {
					if(penalty >= i * gap && penalty <= (i + 1) * gap) {
						errorArray[i] += 1;
						break;
					}
				}
				String content = data.get(filename);
				content = content.replaceAll("\\[", "").replaceAll("\\}", "").split("《中华人民共和国")[0];
				result += filename + "@@" + content + "@@" + penalty + "\n";
			}
			count++;
			if (count%1000==0) {
				System.out.println(count);
				Tools.saveAsFileWriter("/app/tmpdata/robbery_123", result);
				result = "";
			}
		}
		result = "";
		for (int i=0; i<errorNum; i++) {
			result += df.format((i+0.5)*gap) + "\t" + df.format(errorArray[i]/count*100) + "%\n";
		}
		Tools.saveAsFileWriter("/app/tmpdata/robbery_sum", result);
	}
}
