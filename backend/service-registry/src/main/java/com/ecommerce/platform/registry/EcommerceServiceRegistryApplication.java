package com.ecommerce.platform.registry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*
 * Main class to bootstrap Spring Boot Eureka Server.
 * @EnableEurekaServer annotation enables this application
 * to act as a Service Registry.
 */
@SpringBootApplication // Marks this class as Spring Boot main configuration class
@EnableEurekaServer // Enable Eureka Server to register microservices
public class EcommerceServiceRegistryApplication {

	public static void main(String[] args) {

		SpringApplication.run(EcommerceServiceRegistryApplication.class, args);

	}

}
