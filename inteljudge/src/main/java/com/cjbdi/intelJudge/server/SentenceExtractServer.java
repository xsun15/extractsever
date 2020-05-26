package com.cjbdi.intelJudge.server;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.intelJudge.configure.BeanConfigCenter;
import com.cjbdi.intelJudge.model.Similarity;
import com.cjbdi.intelJudge.utils.HttpRequest;
import com.cjbdi.intelJudge.utils.Tools;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class SentenceExtractServer {

	@RequestMapping(value = {"/intelli/judge/"}, produces = {"application/json;charset=UTF-8"})
	public JSONObject extractVector(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
		JSONObject result = new JSONObject();
		if (!reqParam.containsKey("fullText") || !reqParam.containsKey("min_penalty") || !reqParam.containsKey("max_penalty")) return result;
		String content = reqParam.getString("fullText");
		if (!StringUtils.isNotEmpty(content)) return result;
		// 获取文书经审理查明段
		JSONObject reqParaCaseportray = new JSONObject();
		reqParaCaseportray.put("fullText", content);
		String caseportray = HttpRequest.sendPost(BeanConfigCenter.bagwords.getPlugService().get("caseportrait").get("docsplit").toString(), reqParaCaseportray);
		if (StringUtils.isNotEmpty(caseportray)) {
			JSONObject caseportrayJson = JSONObject.parseObject(caseportray);
			if (caseportrayJson.containsKey("justice") && StringUtils.isNotEmpty(caseportrayJson.getString("justice")))
				content = caseportrayJson.getString("justice");
		}
		content = Tools.clean(content);
		// 如果没有传案由则预测案由
		String casecause = "";
		if (!reqParam.containsKey("casecause")) {
			JSONObject reqParaModel = new JSONObject();
			reqParaModel.put("content", content);
			casecause = HttpRequest.sendPost(BeanConfigCenter.bagwords.getPlugService().get("model").get("predictcasecausermdl"), reqParaModel);
		} else {
			casecause = reqParam.getString("casecause");
		}
		if (!StringUtils.isNotEmpty(casecause)) return result;
		// 案由名称映射到内部英文
		for (String ename : BeanConfigCenter.bagwords.getCasecause().keySet()) {
			if (BeanConfigCenter.bagwords.getCasecause().get(ename).equals(casecause)) {
				casecause = ename;
			}
		}
		if (!BeanConfigCenter.bagwords.getCasecause().containsKey(casecause)) return result;
		int min_penalty = reqParam.getInteger("min_penalty");
		int max_penalty = reqParam.getInteger("max_penalty");
		int topKS = BeanConfigCenter.bagwords.getTopKCase().get(casecause);
		// 如果用户传递最相似案件个数，则接受用户设定值
		if (reqParam.containsKey("topK")) topKS = reqParam.getInteger("topK");
		List<String> similarCase = Similarity.assignTask(content, casecause, topKS);
		// 调用ES库分析裁判结果
		TransportClient client = BeanConfigCenter.esclient.getClient();
		Map<String, Integer> similarCaseResult = new HashMap<>();
		for (String filename : similarCase) {
			GetResponse response = client.prepareGet(BeanConfigCenter.bagwords.getEsindex(), BeanConfigCenter.bagwords.getEstype(), filename).get();
			if (StringUtils.isNotEmpty(response.getSourceAsString())) {
				JSONObject responseJson = JSONObject.parseObject(response.getSourceAsString());
				int oldCasePenalty = responseJson.getInteger("principalPenalty");
				if (oldCasePenalty >= min_penalty && oldCasePenalty < max_penalty) {
					similarCaseResult.put(filename, oldCasePenalty);
				}
			}
		}
		System.out.println(similarCaseResult);
		return result;
	}


}
