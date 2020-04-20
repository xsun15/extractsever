package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;

public class FactSplitConfig {

    private FactSplitBasicConfig factSplit;

    public FactSplitConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getStructurationConfigPlace().getFactsplit());
        for (String feature : featureNameList) {
            FactSplitBasicConfig factSplitBasicConfig = new FactSplitBasicConfig(feature, BeanConfigCenter.configPlace.getStructurationConfigPlace().getFactsplit());
            factSplit=factSplitBasicConfig;
        }
    }

    public FactSplitBasicConfig getFactSplit() {
        return factSplit;
    }

    public void setFactSplit(FactSplitBasicConfig factSplit) {
        this.factSplit = factSplit;
    }
}
