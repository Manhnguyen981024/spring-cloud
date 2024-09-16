package com.manhpro.springcloud.currency_conversion.currency_conversion_service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.manhpro.springcloud.currency_conversion.currency_conversion_service.bean.CurrencyConversion;

@FeignClient(name = "currency-exchange-service")
public interface CurrencyExchangeProxy {
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion getExchange(@PathVariable String from, @PathVariable String to);
}
