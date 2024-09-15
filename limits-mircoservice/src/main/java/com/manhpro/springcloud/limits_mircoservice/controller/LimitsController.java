package com.manhpro.springcloud.limits_mircoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manhpro.springcloud.limits_mircoservice.bean.Limits;
import com.manhpro.springcloud.limits_mircoservice.configuration.Configuration;

@RestController
public class LimitsController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public Limits getLimits() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}
}
