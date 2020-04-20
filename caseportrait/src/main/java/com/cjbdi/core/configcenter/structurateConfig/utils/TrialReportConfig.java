package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;

public class TrialReportConfig {
    private TrialReportBasicConfig trialReport;

    public TrialReportConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getStructurationConfigPlace().getTrialreport());
        for (String feature : featureNameList) {
            TrialReportBasicConfig trialReportBasicConfig = new TrialReportBasicConfig(feature, BeanConfigCenter.configPlace.getStructurationConfigPlace().getTrialreport());
            trialReport=trialReportBasicConfig;
        }
    }

    public TrialReportBasicConfig getTrialReport() {
        return trialReport;
    }

    public void setTrialReport(TrialReportBasicConfig trialReport) {
        this.trialReport = trialReport;
    }
}
