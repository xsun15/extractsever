package com.cjbdi.core.configcenter.extractconfig.personparty;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;

import java.util.List;

public class PersonPartyBasicConfig {
	private List<String> defendant;

	public PersonPartyBasicConfig(String featureName, String sourceName) {
		this.defendant = YamlPropertySourceFactoryUser.loadConfig( featureName + ".defendant", sourceName);
	}

	public List<String> getDefendant() {
		return defendant;
	}

	public void setDefendant(List<String> defendant) {
		this.defendant = defendant;
	}
}
