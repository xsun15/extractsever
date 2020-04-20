package com.cjbdi.core.extractcenter.model;

import java.util.Map;
import java.util.Set;

public class DefendantModel {
    private String name;
    private Set<String> casecauseSet;
    private Set<String> nameSet;
    private Map<String, CasecauseModel> casecauseModelMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getCasecauseSet() {
        return casecauseSet;
    }

    public void setCasecauseSet(Set<String> casecauseSet) {
        this.casecauseSet = casecauseSet;
    }

    public Map<String, CasecauseModel> getCasecauseModelMap() {
        return casecauseModelMap;
    }

    public void setCasecauseModelMap(Map<String, CasecauseModel> casecauseModelMap) {
        this.casecauseModelMap = casecauseModelMap;
    }

    public Set<String> getNameSet() {
        return nameSet;
    }

    public void setNameSet(Set<String> nameSet) {
        this.nameSet = nameSet;
    }
}
