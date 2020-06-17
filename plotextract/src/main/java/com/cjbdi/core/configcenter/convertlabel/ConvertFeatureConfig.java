package com.cjbdi.core.configcenter.convertlabel;

import com.cjbdi.core.configcenter.BeanFactoryConfig;

public class ConvertFeatureConfig {
    public PurposeFeatureConfig zhengAn;
    public PurposeFeatureConfig selfSentence;
    public PurposeFeatureConfig leiAn;

    public ConvertFeatureConfig() {
        this.zhengAn = new PurposeFeatureConfig(BeanFactoryConfig.configPlace.getSelfSentenceConfigPlace().getCasecauseFeature());
        this.selfSentence = new PurposeFeatureConfig(BeanFactoryConfig.configPlace.getSelfSentenceConfigPlace().getCasecauseFeature());
        this.leiAn = new PurposeFeatureConfig(BeanFactoryConfig.configPlace.getLeianConfigPlace().getCaseCauseFeature());
    }
}
