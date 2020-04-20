package com.cjbdi.core.configcenter.extractconfig;

import com.cjbdi.core.configcenter.extractconfig.cascause.CasecauseConfig;
import com.cjbdi.core.configcenter.extractconfig.personparty.PersonPartyConfig;
import com.cjbdi.core.configcenter.extractconfig.portrait.PortrayConfig;
import com.cjbdi.core.configcenter.extractconfig.province.ProvinceConfig;

public class ExtractConfig {
	private CasecauseConfig casecauseConfig = new CasecauseConfig();
	private PersonPartyConfig personPartyConfig = new PersonPartyConfig();
	private PortrayConfig portrayConfig = new PortrayConfig();
	private ProvinceConfig provinceConfig = new ProvinceConfig();

	public CasecauseConfig getCasecauseConfig() {
		return casecauseConfig;
	}

	public void setCasecauseConfig(CasecauseConfig casecauseConfig) {
		this.casecauseConfig = casecauseConfig;
	}

	public PersonPartyConfig getPersonPartyConfig() {
		return personPartyConfig;
	}

	public void setPersonPartyConfig(PersonPartyConfig personPartyConfig) {
		this.personPartyConfig = personPartyConfig;
	}

	public PortrayConfig getPortrayConfig() {
		return portrayConfig;
	}

	public void setPortrayConfig(PortrayConfig portrayConfig) {
		this.portrayConfig = portrayConfig;
	}

	public ProvinceConfig getProvinceConfig() {
		return provinceConfig;
	}

	public void setProvinceConfig(ProvinceConfig provinceConfig) {
		this.provinceConfig = provinceConfig;
	}
}
