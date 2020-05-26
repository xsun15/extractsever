package com.cjbdi.intelJudge;

import com.cjbdi.intelJudge.configure.BeanConfigCenter;
import com.cjbdi.intelJudge.model.Similarity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class IntelJudgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelJudgeApplication.class, args);
	}

	@PostConstruct
	public void init() {
		BeanConfigCenter.init();
	}

}
