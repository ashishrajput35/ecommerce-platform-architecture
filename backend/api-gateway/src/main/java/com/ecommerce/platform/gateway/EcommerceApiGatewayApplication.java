package com.ecommerce.platform.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main Class for API Gateway
 * @EnableDiscoveryClient - To Enable Eureka Client Registration
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EcommerceApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiGatewayApplication.class, args);
	}

}
