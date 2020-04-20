package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;

public class IndicitOpinionConfig {
    private IndicitOpinionBasicConfig indicitOpinion;

    public IndicitOpinionConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getStructurationConfigPlace().getIndicitopinion());
        for (String feature : featureNameList) {
            IndicitOpinionBasicConfig indicitOpinionBasicConfig = new IndicitOpinionBasicConfig(feature, BeanConfigCenter.configPlace.getStructurationConfigPlace().getIndicitopinion());
            indicitOpinion=indicitOpinionBasicConfig;
        }
    }

    public IndicitOpinionBasicConfig getIndicitOpinion() {
        return indicitOpinion;
    }

    public void setIndicitOpinion(IndicitOpinionBasicConfig indicitOpinion) {
        this.indicitOpinion = indicitOpinion;
    }
}
