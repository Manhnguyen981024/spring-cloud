package com.manhpro.springcloud.api_gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatwayConfiguration {

	@Bean 
	public RouteLocator locator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p.path("/get")
							.filters(f -> 
									f.addRequestHeader("MyHeader", "MyUri")
									 .addRequestParameter("MYparam", "MYvalue"))
							.uri("http://httpbin.org:80"))
				.route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange-service"))
				.route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)"
								, "/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				
				.build();
	}
}

