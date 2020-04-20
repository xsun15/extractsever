package com.cjbdi;

import com.cjbdi.core.configcenter.BeanConfigCenter;
import com.cjbdi.core.configcenter.configplace.ConfigPlace;
import com.cjbdi.core.configcenter.extractconfig.ExtractConfig;
import com.cjbdi.core.configcenter.interfaceconfig.InterfaceConfig;
import com.cjbdi.core.configcenter.structurateConfig.StructurateConfig;
import com.cjbdi.core.extractcenter.BeanExtractCenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CaseportraitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseportraitApplication.class, args);
	}

	@PostConstruct
	public void init() {
		BeanConfigCenter.configPlace = new ConfigPlace();
		BeanConfigCenter.interfaceConfig=new InterfaceConfig();
		BeanConfigCenter.extractConfig=new ExtractConfig();
		BeanConfigCenter.structurateConfig = new StructurateConfig();
		BeanExtractCenter.init();
	}
}
