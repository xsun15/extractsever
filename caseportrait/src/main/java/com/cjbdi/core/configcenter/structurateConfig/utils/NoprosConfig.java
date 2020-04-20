package com.cjbdi.core.configcenter.structurateConfig.utils;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;

public class NoprosConfig {
    private NoprosBasicConfig nopros;

    public NoprosConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getStructurationConfigPlace().getNopros());
        for (String feature : featureNameList) {
            NoprosBasicConfig noprosBasicConfig = new NoprosBasicConfig(feature, BeanConfigCenter.configPlace.getStructurationConfigPlace().getNopros());
            nopros=noprosBasicConfig;
        }
    }

    public NoprosBasicConfig getNopros() {
        return nopros;
    }

    public void setNopros(NoprosBasicConfig nopros) {
        this.nopros = nopros;
    }
}
