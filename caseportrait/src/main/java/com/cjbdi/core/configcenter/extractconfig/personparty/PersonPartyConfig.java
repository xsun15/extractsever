package com.cjbdi.core.configcenter.extractconfig.personparty;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;


public class PersonPartyConfig {
    private PersonPartyBasicConfig personParty;

    public PersonPartyConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getExtractionConfigPlace().getPersonparty());
        for (String feature : featureNameList) {
            PersonPartyBasicConfig personPartyBasicConfig = new PersonPartyBasicConfig(feature, BeanConfigCenter.configPlace.getExtractionConfigPlace().getPersonparty());
            personParty=personPartyBasicConfig;
        }
    }

    public PersonPartyBasicConfig getPersonParty() {
        return personParty;
    }

    public void setPersonParty(PersonPartyBasicConfig personParty) {
        this.personParty = personParty;
    }
}
