package com.cjbdi.core.configcenter.extractconfig.province;

import com.cjbdi.core.configcenter.structurateConfig.utils.ReviewReportConfig;
import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.List;

public class ProvinceConfig {
    private HashMap<String, HashMap<String, Object>> features = new HashMap<>();
    private HashMap<String, List<String>> province =  new HashMap<>();
    private HashMap<String, List<String>> city = new HashMap<>();
    private HashMap<String, List<String>> county = new HashMap<>();

    public ProvinceConfig() {
        Yaml yaml = new Yaml();
        HashMap<String, HashMap<String, Object>>  hashMap = yaml.load(ReviewReportConfig.class.getResourceAsStream("/extract/province/province.yml"));
        if (hashMap!=null) {
            for (String feature : hashMap.keySet()) {
                features.put(feature, hashMap.get(feature));
                province.put(feature, (List<String>) hashMap.get(feature).get("province"));
                city.put(feature, (List<String>) hashMap.get(feature).get("city"));
                county.put(feature, (List<String>) hashMap.get(feature).get("county"));
            }
        }
    }

    public HashMap<String, HashMap<String, Object>> getFeatures() {
        return features;
    }

    public void setFeatures(HashMap<String, HashMap<String, Object>> features) {
        this.features = features;
    }

    public HashMap<String, List<String>> getProvince() {
        return province;
    }

    public void setProvince(HashMap<String, List<String>> province) {
        this.province = province;
    }

    public HashMap<String, List<String>> getCity() {
        return city;
    }

    public void setCity(HashMap<String, List<String>> city) {
        this.city = city;
    }

    public HashMap<String, List<String>> getCounty() {
        return county;
    }

    public void setCounty(HashMap<String, List<String>> county) {
        this.county = county;
    }
}
