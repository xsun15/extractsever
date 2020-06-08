package com.cjbdi.core.configurecentent.callinterface;

import com.cjbdi.core.configurecentent.utils.YamlPropertySourceFactoryUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class InterfaceCivil {
	private List<String> ip;
	private List<String> portray;
	private List<String> split;

	public InterfaceCivil(String featureName, String sourceName) {
		this.ip = YamlPropertySourceFactoryUser.loadConfig(featureName + ".ip", sourceName);
		this.portray = YamlPropertySourceFactoryUser.loadConfig(featureName + ".portray", sourceName);
		this.split = YamlPropertySourceFactoryUser.loadConfig(featureName + ".split", sourceName);
	}

	public String getIp() {
		return StringUtils.strip(this.ip.toString(), "[]");
	}

	public void setIp(List ip) {
		this.ip = ip;
	}

	public String getPortray() {
		return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.portray.toString(), "[]");
	}

	public String getSplit() {
		return StringUtils.strip(this.ip.toString(), "[]") + StringUtils.strip(this.split.toString(), "[]");
	}
}
