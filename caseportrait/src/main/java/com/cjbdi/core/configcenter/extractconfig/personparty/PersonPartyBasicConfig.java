package com.cjbdi.core.configcenter.extractconfig.personparty;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;
import org.w3c.dom.ls.LSException;

import java.util.List;

public class PersonPartyBasicConfig {
	private List<String> defendant;
	private List<String> indictee;
	private List<String> victim;
	private List<String> witness;

	public PersonPartyBasicConfig(String featureName, String sourceName) {
		this.defendant = YamlPropertySourceFactoryUser.loadConfig( featureName + ".defendant", sourceName);
		this.indictee = YamlPropertySourceFactoryUser.loadConfig( featureName + ".indictee", sourceName);
		this.victim = YamlPropertySourceFactoryUser.loadConfig( featureName + ".victim", sourceName);
		this.witness = YamlPropertySourceFactoryUser.loadConfig( featureName + ".witness", sourceName);
	}

	public List<String> getDefendant() {
		return defendant;
	}

	public void setDefendant(List<String> defendant) {
		this.defendant = defendant;
	}

	public List<String> getIndictee() {
		return indictee;
	}

	public void setIndictee(List<String> indictee) {
		this.indictee = indictee;
	}

	public List<String> getVictim() {
		return victim;
	}

	public void setVictim(List<String> victim) {
		this.victim = victim;
	}

	public List<String> getWitness() {
		return witness;
	}

	public void setWitness(List<String> witness) {
		this.witness = witness;
	}
}
