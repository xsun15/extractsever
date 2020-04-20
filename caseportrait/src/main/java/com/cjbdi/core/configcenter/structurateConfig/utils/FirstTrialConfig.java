package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;

public class FirstTrialConfig {
    private FirstTrialBasicConfig firstTrial;

    public FirstTrialConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getStructurationConfigPlace().getFirsttrial());
        for (String feature : featureNameList) {
            FirstTrialBasicConfig firstTrialBasicConfig = new FirstTrialBasicConfig(feature, BeanConfigCenter.configPlace.getStructurationConfigPlace().getFirsttrial());
            firstTrial = firstTrialBasicConfig;
        }
    }

    public FirstTrialBasicConfig getFirstTrial() {
        return firstTrial;
    }

    public void setFirstTrial(FirstTrialBasicConfig firstTrial) {
        this.firstTrial = firstTrial;
    }
}
