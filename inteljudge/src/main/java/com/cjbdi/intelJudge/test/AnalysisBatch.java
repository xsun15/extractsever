package com.cjbdi.intelJudge.test;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.intelJudge.configure.BeanConfigCenter;
import com.cjbdi.intelJudge.configure.SampleModel;
import com.cjbdi.intelJudge.model.Similarity;
import com.cjbdi.intelJudge.utils.HttpRequest;
import com.cjbdi.intelJudge.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisBatch {
	public static void main(String [] args) {
		BeanConfigCenter.init();
		List<SampleModel> sampleModelList = BeanConfigCenter.bagwords.getKeywordCase().get("robbery");
		// 调用ES库分析裁判结果
		String casecause = "robbery";
		int topK = 100;
		int errorNum = 20;
		double [] errorArray = new double[errorNum];
		String result = "";
		DecimalFormat df = new DecimalFormat(".00");
		TransportClient client = BeanConfigCenter.esclient.getClient();
		int totalCase = 0;
		for (SampleModel sampleModel : sampleModelList) {
			// 获取当前案件刑期
			String filename = sampleModel.getFilename();
			String fullText = "";
			GetResponse response = client.prepareGet(BeanConfigCenter.bagwords.getEsindex(), BeanConfigCenter.bagwords.getEstype(), filename).get();
			if (StringUtils.isNotEmpty(response.getSourceAsString())) {
				JSONObject responseJson = JSONObject.parseObject(response.getSourceAsString());
				int penalty = responseJson.getInteger("principalPenalty");
				fullText = responseJson.getString("text");
				sampleModel.setPenalty(penalty);
			}
			// 获取当前案件的法定刑
			String coreContent = fullText;
			JSONObject reqPara = new JSONObject();
			reqPara.put("fullText", fullText);
			String caseportray = HttpRequest.sendPost(BeanConfigCenter.bagwords.getPlugService().get("caseportrait").get("docsplit").toString(), reqPara);
			if (StringUtils.isNotEmpty(caseportray)) {
				JSONObject caseportrayJson = JSONObject.parseObject(caseportray);
				if (caseportrayJson.containsKey("justice") && StringUtils.isNotEmpty(caseportrayJson.getString("justice")) &&
						caseportrayJson.containsKey("opinion") && StringUtils.isNotEmpty(caseportrayJson.getString("opinion")))
					coreContent = (caseportrayJson.getString("justice") + caseportrayJson.getString("opinion")).replaceAll("\n", "");
			}
			reqPara.clear();
			reqPara.put("fullText", coreContent);
			String fdxIndex = HttpRequest.sendPost(BeanConfigCenter.bagwords.getPlugService().get("fdx").get("fdx2"), reqPara);
			int min_fdxIndex = Integer.parseInt(fdxIndex) - 2;
			int max_fdxIndex = Integer.parseInt(fdxIndex) + 2;
			if (min_fdxIndex < 1) min_fdxIndex = 1;
			if (max_fdxIndex > 11) max_fdxIndex = 11;
			int min_penalty = BeanConfigCenter.bagwords.getFdx2Case().get(casecause).get(min_fdxIndex).minval;
			int max_penalty = BeanConfigCenter.bagwords.getFdx2Case().get(casecause).get(max_fdxIndex).maxval;
			if (fullText.isEmpty()) continue;
			// 分析案件库中每个案件的相似案件刑期
			Map<String, Double> similarCase = Similarity.assignTask(sampleModel.getKeyword(), casecause, topK, min_penalty, max_penalty, client);
			if (similarCase==null||similarCase.size()==0) continue;
			double sum = 0.0;
			double totalSimilarity = 0.0;
			String ajbs = "";
			for ( String id : similarCase.keySet()) {
				response = client.prepareGet(BeanConfigCenter.bagwords.getEsindex(), BeanConfigCenter.bagwords.getEstype(), id).get();
				double similarity = similarCase.get(id);
				if (StringUtils.isNotEmpty(response.getSourceAsString())) {
					JSONObject responseJson = JSONObject.parseObject(response.getSourceAsString());
					int oldCasePenalty = responseJson.getInteger("principalPenalty");
					if (oldCasePenalty >= min_penalty && oldCasePenalty <= max_penalty) {
						sum += oldCasePenalty * similarity;
						totalSimilarity += similarity;
						ajbs += id + " ";
					}
				}
			}
			double predPenalty = sum / totalSimilarity;
			fdxIndex = HttpRequest.sendPost(BeanConfigCenter.bagwords.getPlugService().get("fdx").get("fdx1"), reqPara);
			int min_fdx = BeanConfigCenter.bagwords.getFdx1Case().get(casecause).get(Integer.parseInt(fdxIndex)).minval;
			int max_fdx = BeanConfigCenter.bagwords.getFdx1Case().get(casecause).get(Integer.parseInt(fdxIndex)).maxval;
			double error = 1.0*Math.abs(predPenalty- sampleModel.getPenalty())/(max_fdx-min_fdx)*100;
			for (int i=0; i<errorNum; i++) {
				if (error>=i*100.0/errorNum && error<=(i+1)*100.0/errorNum) {
					errorArray[i] += 1;
					break;
				}
			}
			result += filename + "\t" + sampleModel.getPenalty() + "\t" + df.format(predPenalty) + "\t" + df.format(error) + "%\t" + ajbs + "\n";
			totalCase++;
			if (totalCase%1==0) {
				System.out.println("-----------------漂亮分割线--------------------------");
				Tools.saveAsFileWriter("/app/develop/extractsever/inteljudge/src/main/resources/result/robbery/robbery_detail", result);
				result = "";
			}
			if (totalCase>=2000) break;
		}
		result = "";
		for (int i=0; i<errorNum; i++) {
			result += df.format((i+0.5)*100.0/errorNum) + "\t" + df.format(errorArray[i]/totalCase*100.0) + "%\n";
		}
		Tools.saveAsFileWriter("/app/develop/extractsever/inteljudge/src/main/resources/result/robbery/robbery_sum", result);
	}
}
