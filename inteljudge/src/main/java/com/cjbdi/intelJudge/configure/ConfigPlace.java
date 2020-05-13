package com.cjbdi.intelJudge.configure;

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;

public class ConfigPlace {
	private HashMap<String, HashMap<String, HashMap<String, Object>>> features;

	public ConfigPlace() {
		Yaml yaml = new Yaml();
		features = yaml.load(ConfigPlace.class.getResourceAsStream("/configure.yml"));
	}

	public HashMap<String, HashMap<String, HashMap<String, Object>>> getFeatures() {
		return features;
	}

	public void setFeatures(HashMap<String, HashMap<String, HashMap<String, Object>>> features) {
		this.features = features;
	}

}
