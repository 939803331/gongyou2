package com.ph.provider.service.impl;

import com.ph.provider.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
	@Override
	public String hello() {
		return "feign miss you two!!";
	}
}
