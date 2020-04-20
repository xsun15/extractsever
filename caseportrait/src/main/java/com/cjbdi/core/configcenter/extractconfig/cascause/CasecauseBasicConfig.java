package com.cjbdi.core.configcenter.extractconfig.cascause;

import com.alibaba.fastjson.JSONObject;
import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class CasecauseBasicConfig {
    List<String> ename;
    List<String> name;
    List<String> rule;
    List<String> subSituationRule;

    public CasecauseBasicConfig(String featureName, String sourceName) {
        this.name = YamlPropertySourceFactoryUser.loadConfig( featureName + ".name", sourceName);
        this.ename = YamlPropertySourceFactoryUser.loadConfig(featureName + ".ename", sourceName);
        this.rule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".rule", sourceName);
        this.subSituationRule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".subSituationRule", sourceName);
    }
    public String getName() {

        return StringUtils.strip(name.toString(), "[]");
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getRule() {
        return rule;
    }

    public void setRule(List<String> rule) {
        this.rule = rule;
    }

    public String getEname() {
        return StringUtils.strip(ename.toString(), "[]");
    }

    public void setEname(List<String> ename) {
        this.ename = ename;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(StringUtils.strip(name.toString(),"[]"), rule);
        return jsonObject;
    }

    public JSONObject chiname2ename() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(StringUtils.strip(name.toString(),"[]"), StringUtils.strip(ename.toString(), "[]"));
        return jsonObject;
    }

    public List<String> getSubSituationRule() {
        return subSituationRule;
    }

    public void setSubSituationRule(List<String> subSituationRule) {
        this.subSituationRule = subSituationRule;
    }
}
