package com.ph.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "provider")
public interface HelloFeign {

	@GetMapping("/hello")
	String hello();
}
