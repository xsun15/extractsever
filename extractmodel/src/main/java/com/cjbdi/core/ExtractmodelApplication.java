package com.cjbdi.core;

import com.cjbdi.core.configurecenter.BeanConfigCenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ExtractmodelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtractmodelApplication.class, args);
	}

	@PostConstruct
	public void init() {
		BeanConfigCenter.init();
	}
}
