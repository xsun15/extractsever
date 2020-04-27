package com.cjbdi.core.servercenter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

public class Test {
	@RequestMapping(value = {"/hello"}, produces = {"application/json;charset=UTF-8"})
	public String test(@RequestBody JSONObject reqParam, @Context HttpServletRequest request) {
		return "你好";
	}
}
