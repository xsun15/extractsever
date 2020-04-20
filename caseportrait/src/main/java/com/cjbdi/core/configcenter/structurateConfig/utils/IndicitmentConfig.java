package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;

public class IndicitmentConfig {
    private IndicitmentBasicConfig indicitment;

    public IndicitmentConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getStructurationConfigPlace().getIndictment());
        for (String feature : featureNameList) {
            IndicitmentBasicConfig indicitmentBasicConfig = new IndicitmentBasicConfig(feature, BeanConfigCenter.configPlace.getStructurationConfigPlace().getIndictment());
            indicitment=indicitmentBasicConfig;
        }
    }

    public IndicitmentBasicConfig getIndicitment() {
        return indicitment;
    }

    public void setIndicitment(IndicitmentBasicConfig indicitment) {
        this.indicitment = indicitment;
    }
}
