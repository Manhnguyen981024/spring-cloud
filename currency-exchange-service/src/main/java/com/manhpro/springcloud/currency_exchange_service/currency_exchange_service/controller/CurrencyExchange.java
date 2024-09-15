package com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.bean.CurrencyExchangeBean;

@RestController
public class CurrencyExchange {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchangeBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		return new CurrencyExchangeBean(10000L, "USD", "VND", BigDecimal.valueOf(50));
	}
}
