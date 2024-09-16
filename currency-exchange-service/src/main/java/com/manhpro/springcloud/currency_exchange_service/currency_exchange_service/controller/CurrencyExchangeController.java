package com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.bean.CurrencyExchangeBean;
import com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchangeBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		var port = environment.getProperty("local.server.port");
		var currencyExchange = this.currencyExchangeRepository.findByFromAndTo(from, to);
		
		if (currencyExchange == null) 
			throw new RuntimeException(
					"Unable to find currency exchange from " + from + " to " + to);
		
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
	}
}
