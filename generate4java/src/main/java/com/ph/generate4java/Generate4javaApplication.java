package com.ph.generate4java;

import com.ph.generate4java.generator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Generate4javaApplication implements ApplicationRunner {

	@Autowired
	private GenerateEntity generateEntity;

	@Autowired
	private GenerateVo generateVo;

	@Autowired
	private GenerateMapper generateMapper;

	@Autowired
	private GenerateService generateService;

	@Autowired
	private GenerateServiceImpl generateServiceImpl;

	@Autowired
	private GenerateController generateController;

	@Autowired
	private GenerateJsonSchema generateJsonSchema;

	public static void main(String[] args) {
		SpringApplication.run(Generate4javaApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		generateEntity.generator();
		//generateVo.generator();
		//generateMapper.generator();
		//generateService.generator();
		//generateServiceImpl.generator();
		//generateController.generator();
		//generateJsonSchema.generator();
	}
}
