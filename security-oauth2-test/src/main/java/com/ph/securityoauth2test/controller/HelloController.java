package com.ph.securityoauth2test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @PACKAGE_NAME: com.ph.securityoauth2test.controller
 * @NAME: HelloController
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 10:03
 **/
@RestController
public class HelloController {

	@RequestMapping("/api/hello")
	public String hello() {
		System.out.println("hello spring security oauth2");
		return "hello spring security oauth2";
	}
}
