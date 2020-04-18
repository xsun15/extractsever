package com.cjbdi;

import com.cjbdi.core.configurecentent.BeanFactoryConfig;
import com.cjbdi.core.configurecentent.callinterface.InterfaceConfig;
import com.cjbdi.core.configurecentent.casecause.PredCasecauseConfig;
import com.cjbdi.core.configurecentent.configplace.ConfigPlace;
import com.cjbdi.core.configurecentent.converlabel.ConvertLabelConfig;
import com.cjbdi.core.configurecentent.extractfeature.ExtractFeatureConfig;
import com.cjbdi.core.configurecentent.extractfeature.checklabel.CheckLabelConfig;
import com.cjbdi.core.configurecentent.splitdocument.FactTextConfig;
import com.cjbdi.core.extractcenter.BeanFactoryExtract;
import com.cjbdi.core.extractcenter.sentence.SentenceExtractor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ExtractApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtractApplication.class, args);
	}

	@PostConstruct
	public void init() {
		BeanFactoryConfig.configPlace = new ConfigPlace();
		BeanFactoryConfig.predCasecauseConfig = new PredCasecauseConfig();
		BeanFactoryConfig.factTextConfig = new FactTextConfig();
		BeanFactoryConfig.interfaceConfig = new InterfaceConfig();
		BeanFactoryConfig.extractFeatureConfig = new ExtractFeatureConfig();
		BeanFactoryConfig.convertLabelConfig = new ConvertLabelConfig();
		BeanFactoryConfig.checkLabelConfig = new CheckLabelConfig();
		BeanFactoryExtract.init();
	}
}