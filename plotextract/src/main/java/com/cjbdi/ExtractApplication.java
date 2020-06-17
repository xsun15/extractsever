package com.cjbdi;

import com.cjbdi.core.configcenter.BeanFactoryConfig;
import com.cjbdi.core.configcenter.checkfeature.CheckFeatureConfig;
import com.cjbdi.core.configcenter.configplace.ConfigPlace;
import com.cjbdi.core.configcenter.casecause.CasecauseConfig;
import com.cjbdi.core.configcenter.convertlabel.ConvertFeatureConfig;
import com.cjbdi.core.configcenter.extractfeature.ExtractFeatureConfig;
import com.cjbdi.core.configcenter.interfaceurl.InterfaceUrl;
import com.cjbdi.core.configcenter.splitdoc.SplitDocConfig;
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
		BeanFactoryConfig.splitDocConfig = new SplitDocConfig();
		BeanFactoryConfig.casecauseConfig = new CasecauseConfig();
		BeanFactoryConfig.extractFeatureConfig = new ExtractFeatureConfig();
		BeanFactoryConfig.convertFeatureConfig = new ConvertFeatureConfig();
		BeanFactoryConfig.checkFeatureConfig = new CheckFeatureConfig();
		BeanFactoryConfig.interfaceUrl = new InterfaceUrl();
	}
}