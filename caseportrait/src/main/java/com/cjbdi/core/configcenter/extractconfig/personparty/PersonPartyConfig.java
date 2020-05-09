package com.cjbdi.core.configcenter.extractconfig.personparty;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.utils.GetFeatureName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PersonPartyConfig {
    private PersonPartyBasicConfig personParty;
    private Map<String, List<String>> characterParty = new HashMap<>();

    public PersonPartyConfig() {
        ArrayList<String> featureNameList = GetFeatureName.run(BeanConfigCenter.configPlace.getExtractionConfigPlace().getPersonparty());
        for (String feature : featureNameList) {
            PersonPartyBasicConfig personPartyBasicConfig = new PersonPartyBasicConfig(feature, BeanConfigCenter.configPlace.getExtractionConfigPlace().getPersonparty());
            personParty=personPartyBasicConfig;
        }
        characterParty.put("被告人", personParty.getIndictee());
        characterParty.put("被害人", personParty.getVictim());
        characterParty.put("证人", personParty.getWitness());
    }

    public PersonPartyBasicConfig getPersonParty() {
        return personParty;
    }

    public void setPersonParty(PersonPartyBasicConfig personParty) {
        this.personParty = personParty;
    }

    public Map<String, List<String>> getCharacterParty() {
        return characterParty;
    }

    public void setCharacterParty(Map<String, List<String>> characterParty) {
        this.characterParty = characterParty;
    }
}
