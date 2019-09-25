package com.ph.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@Value("${message}")
	private String message;

	@GetMapping("/msg")
	public String getMsg() {
		return message;
	}
}
