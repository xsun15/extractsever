package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;

import java.util.List;

public class FactSplitBasicConfig {
	private List<String> header;
	private List<String> tail;
	private List<String> cansize;

	public FactSplitBasicConfig(String featureName, String sourceName) {
		this.header = YamlPropertySourceFactoryUser.loadConfig( featureName + ".header", sourceName);
		this.tail = YamlPropertySourceFactoryUser.loadConfig(featureName + ".tail", sourceName);
		this.cansize = YamlPropertySourceFactoryUser.loadConfig(featureName + ".cansize", sourceName);
	}

	public List<String> getHeader() {
		return header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<String> getTail() {
		return tail;
	}

	public void setTail(List<String> tail) {
		this.tail = tail;
	}

	public List<String> getCansize() {
		return cansize;
	}

	public void setCansize(List<String> cansize) {
		this.cansize = cansize;
	}

}
