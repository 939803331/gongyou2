package com.ph.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public String hello() {
		System.out.println("nihao");
		return "hello gateway";
	}

	@GetMapping("par")
	public String params(String s) {
		return s + " aslnzoivnsdf";
	}
}
