package com.cjbdi.intelJudge;

import com.cjbdi.intelJudge.configure.BeanConfigCenter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class IntelJudgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelJudgeApplication.class, args);
	}



}
