package com.ecommerce.platform.gateway.filter;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Routes Configuration
 * Maps Path → Microservice
 */
//@Configuration
public class GatewayRoutesConfig {

//    @Bean
//    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("order-service", r -> r.path("/order/**")
//                        .uri("lb://order-service"))
//
//                .route("product-service", r -> r.path("/product/**")
//                        .uri("lb://product-service"))
//
//                .build();
//    }
}
