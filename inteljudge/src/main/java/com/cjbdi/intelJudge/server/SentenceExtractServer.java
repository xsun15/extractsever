package com.cjbdi.intelJudge.server;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@RestController
public class SentenceExtractServer {

	@RequestMapping(value = {"/model/extract/vector"}, produces = {"application/json;charset=UTF-8"})
	public String extractVector(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
		return "";
	}


}
