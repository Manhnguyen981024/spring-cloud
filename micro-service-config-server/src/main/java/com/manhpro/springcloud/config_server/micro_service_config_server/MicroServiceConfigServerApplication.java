package com.manhpro.springcloud.config_server.micro_service_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class MicroServiceConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServiceConfigServerApplication.class, args);
	}

}
