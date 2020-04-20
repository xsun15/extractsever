package com.cjbdi.core.configcenter.extractconfig.cascause;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CasecauseConfig {
    LinkedHashMap<String, CasecauseBasicConfig> casecause = new LinkedHashMap<>();

    public CasecauseConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getExtractionConfigPlace().getCasecause());
        for (String feature : featureNameList) {
            CasecauseBasicConfig casecauseBasicConfig = new CasecauseBasicConfig(feature, BeanConfigCenter.configPlace.getExtractionConfigPlace().getCasecause());
            casecause.put(feature, casecauseBasicConfig);
        }
    }

    public LinkedHashMap<String, CasecauseBasicConfig> getCasecause() {
        return casecause;
    }

    public Map<String, String> getCasecauseName() {
        Map<String, String> map = new HashMap<>();
        for (String feature : casecause.keySet()) {
            CasecauseBasicConfig casecauseBasicConfig = casecause.get(feature);
            map.put(casecauseBasicConfig.getName(), casecauseBasicConfig.getEname());
        }
        return map;
    }
}
