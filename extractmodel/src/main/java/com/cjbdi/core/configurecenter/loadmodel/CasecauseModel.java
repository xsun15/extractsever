package com.cjbdi.core.configurecenter.loadmodel;

import java.util.HashMap;
import java.util.Map;

public class CasecauseModel {
	private Map<String, CasecauseLoadModel> modelMap = new HashMap<>();

	public CasecauseModel() {
		modelMap.put("盗窃罪", new CasecauseLoadModel("steal"));
		modelMap.put("抢劫罪", new CasecauseLoadModel("robbery"));
		modelMap.put("抢夺罪", new CasecauseLoadModel("seizing"));
	}
}
