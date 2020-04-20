package com.cjbdi.core.configcenter.extractconfig.portrait;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;

public class PortrayConfig {
	private PortrayBasicConfig portrayBasicConfig;

	public PortrayConfig() {
		ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getExtractionConfigPlace().getPortray());
		for (String feature : featureNameList) {
			PortrayBasicConfig personPartyBasicConfig = new PortrayBasicConfig(feature, BeanConfigCenter.configPlace.getExtractionConfigPlace().getPortray());
			portrayBasicConfig = personPartyBasicConfig;
		}
	}

	public PortrayBasicConfig getPortrayBasicConfig() {
		return portrayBasicConfig;
	}

	public void setPortrayBasicConfig(PortrayBasicConfig portrayBasicConfig) {
		this.portrayBasicConfig = portrayBasicConfig;
	}
}
