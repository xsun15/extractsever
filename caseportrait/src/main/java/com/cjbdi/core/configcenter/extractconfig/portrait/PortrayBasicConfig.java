package com.cjbdi.core.configcenter.extractconfig.portrait;

import com.cjbdi.core.configcenter.utils.YamlPropertySourceFactoryUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class PortrayBasicConfig {
    private List<String> name;
    private List<String> header;
    private List<String> casecauserule;

    public PortrayBasicConfig(String featureName, String sourceName) {
        this.name = YamlPropertySourceFactoryUser.loadConfig(featureName + ".name", sourceName);
        this.header = YamlPropertySourceFactoryUser.loadConfig(featureName + ".header", sourceName);
        this.casecauserule = YamlPropertySourceFactoryUser.loadConfig(featureName + ".casecauserule", sourceName);
    }

    public String getName() {
        return StringUtils.strip(this.name.toString(), "[]");
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }

    public List<String> getCasecauserule() {
        return casecauserule;
    }

    public void setCasecauserule(List<String> casecauserule) {
        this.casecauserule = casecauserule;
    }
}
