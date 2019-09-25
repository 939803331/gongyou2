package com.ph.generate4java.generator;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.ph.generate4java.generator
 * @NAME: GeneratorConfigurer
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 14:40
 **/
@Getter
@Setter
@Component
@ConfigurationProperties(value = "generator")
public class GeneratorProperties {
	private String schema;
	private String table;
	private String path;
	private String packagesEntity;
	private String packagesVo;
	private String packagesMapper;
	private String packagesService;
	private String packagesServiceImpl;
	private String packagesController;
}