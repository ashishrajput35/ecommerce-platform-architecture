package com.ecommerce.platform.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class EcommerceConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceConfigServerApplication.class, args);
	}

}
