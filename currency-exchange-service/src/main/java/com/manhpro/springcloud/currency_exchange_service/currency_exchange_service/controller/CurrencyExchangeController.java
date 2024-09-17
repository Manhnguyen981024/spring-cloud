package com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.bean.CurrencyExchangeBean;
import com.manhpro.springcloud.currency_exchange_service.currency_exchange_service.repository.CurrencyExchangeRepository;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CurrencyExchangeController {
	private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);

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
	
	@GetMapping("/circuit-breaker")
	/* @Retry(name = "circuit-breaker", fallbackMethod = "callback") */
//	@CircuitBreaker(name = "default", fallbackMethod = "callback")
//	@RateLimiter(name = "same-api")
	@Bulkhead(name = "same-api", fallbackMethod = "callback")
	public String applyCircuitBreaker() {
		logger.info("receive request -> ");
		String response = new RestTemplate().getForEntity("http://localhost:8080/test", String.class).getBody();
		return response;
	}
	
	public String callback(Exception e) {
		return "apply circuit-breaker";
	}
}
