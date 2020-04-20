package com.cjbdi.core.configcenter.structurateConfig;

import com.cjbdi.core.configcenter.structurateConfig.utils.*;

public class StructurateConfig {
	private FactSplitConfig factSplitConfig = new FactSplitConfig();
	private FirstTrialConfig firstTrialConfig = new FirstTrialConfig();
	private IndicitOpinionConfig indicitOpinionConfig = new IndicitOpinionConfig();
	private NoprosConfig noprosConfig = new NoprosConfig();
	private TrialReportConfig trialReportConfig = new TrialReportConfig();
	private IndicitmentConfig indicitmentConfig = new IndicitmentConfig();

	public FactSplitConfig getFactSplitConfig() {
		return factSplitConfig;
	}

	public void setFactSplitConfig(FactSplitConfig factSplitConfig) {
		this.factSplitConfig = factSplitConfig;
	}

	public FirstTrialConfig getFirstTrialConfig() {
		return firstTrialConfig;
	}

	public void setFirstTrialConfig(FirstTrialConfig firstTrialConfig) {
		this.firstTrialConfig = firstTrialConfig;
	}

	public IndicitOpinionConfig getIndicitOpinionConfig() {
		return indicitOpinionConfig;
	}

	public void setIndicitOpinionConfig(IndicitOpinionConfig indicitOpinionConfig) {
		this.indicitOpinionConfig = indicitOpinionConfig;
	}

	public NoprosConfig getNoprosConfig() {
		return noprosConfig;
	}

	public void setNoprosConfig(NoprosConfig noprosConfig) {
		this.noprosConfig = noprosConfig;
	}

	public TrialReportConfig getTrialReportConfig() {
		return trialReportConfig;
	}

	public void setTrialReportConfig(TrialReportConfig trialReportConfig) {
		this.trialReportConfig = trialReportConfig;
	}

	public IndicitmentConfig getIndicitmentConfig() {
		return indicitmentConfig;
	}

	public void setIndicitmentConfig(IndicitmentConfig indicitmentConfig) {
		this.indicitmentConfig = indicitmentConfig;
	}
}
