package com.manhpro.springcloud.currency_conversion.currency_conversion_service.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.manhpro.springcloud.currency_conversion.currency_conversion_service.bean.CurrencyConversion;
import com.manhpro.springcloud.currency_conversion.currency_conversion_service.proxy.CurrencyExchangeProxy;
import com.netflix.discovery.converters.Auto;

@Configuration(proxyBeanMethods = false)
class RestTemplateConfiguration {
    
    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}	

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion conversion(
				@PathVariable String from,
				@PathVariable String to,
				@PathVariable Integer quantity
			) {
		
		HashMap<String,String> UrlVariables = new HashMap<>();
		UrlVariables.put("from", from);
		UrlVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = this.restTemplate
				.getForEntity("http://currency-exchange:8000/currency-exchange/from/{from}/to/{to}"
				, CurrencyConversion.class
				, UrlVariables);
		
		var responsedEntity = responseEntity.getBody();
		
		var currencyConversion = new CurrencyConversion(
				responsedEntity.getId()
				, from
				, to
				, quantity
				, responsedEntity.getConversionMultiple()
				, responsedEntity.getConversionMultiple().multiply(BigDecimal.valueOf(quantity))
				, responsedEntity.getEnvironment());
		
		return currencyConversion;
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion conversionByUsingFeign(
				@PathVariable String from,
				@PathVariable String to,
				@PathVariable Integer quantity
			) {
	
		
		var responsedEntity = this.proxy.getExchange(from, to);
		
		var currencyConversion = new CurrencyConversion(
				responsedEntity.getId()
				, from
				, to
				, quantity
				, responsedEntity.getConversionMultiple()
				, responsedEntity.getConversionMultiple().multiply(BigDecimal.valueOf(quantity))
				, responsedEntity.getEnvironment() + " from feign");
		
		return currencyConversion;
	}
}
