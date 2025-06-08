package com.ecommerce.platform.gateway.config;

import com.ecommerce.platform.gateway.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


/**
 * Security Configuration for API Gateway
 * It defines which endpoints are secured/open
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtAuthenticationFilter jwtFilter){
        return http
                .csrf(csrf -> csrf.disable())  // Lambda Style Disable CSRF
                .authorizeExchange(auth -> auth

                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Allow CORS preflight
                        //  Public endpoints (no authentication needed)
                        .pathMatchers("/api/v1/auth/**", "/actuator/**").permitAll()

                        // Everything else requires authentication
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
