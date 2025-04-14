package com.ecommerce.platform.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ecommerce.platform.auth")
public class EcommerceAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAuthServiceApplication.class, args);
	}

}
