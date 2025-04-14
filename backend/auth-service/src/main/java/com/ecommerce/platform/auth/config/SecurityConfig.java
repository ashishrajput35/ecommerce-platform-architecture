package com.ecommerce.platform.auth.config;

import com.ecommerce.platform.auth.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Main security configuration class responsible for setting up Spring Security for the application.

 * Configuration class for Spring Security.
 * This class sets up JWT authentication, password encoding,
 * and custom user details service for securing the auth-service endpoints.
 */

@Configuration // Marks this class as a source of Spring bean definitions.
@EnableMethodSecurity // Enables method-level security (e.g., @PreAuthorize, @Secured)
@RequiredArgsConstructor // Generates a constructor with required arguments (final fields), for dependency injection.
public class SecurityConfig {

    // Injected custom JWT filter that validates JWTs on incoming requests
    private final JwtAuthenticationFilter jwtAuthFilter;

    // Custom implementation of Spring Security's UserDetailsService
    private final UserDetailsService userDetailsService;

    /**
     * Defines the security filter chain for HTTP security.
     * - Disables CSRF (since we are using stateless JWT auth)
     * - Allows public access to authentication endpoints (/api/v1/auth/**)
     * - All other endpoints require authentication
     * - Adds custom JWT filter before UsernamePasswordAuthenticationFilter
     *
     * @param http the HttpSecurity configuration object
     * @return configured SecurityFilterChain
     * @throws Exception in case of configuration errors
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Disable CSRF (Cross-Site Request Forgery) protection for stateless APIs
                .csrf(AbstractHttpConfigurer::disable)

                // Define URL access rules
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to authentication endpoints (e.g., login/register)
                        .requestMatchers("/api/v1/auth/**").permitAll() // Public endpoints
                        // All other requests require authentication
                        .anyRequest().authenticated() // Secure all other routes
                )

                // Tell Spring to not create any session (JWT is stateless) // Set session policy to stateless (since we use tokens, not sessions)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                )

                // Register the JWT filter before Spring Security’s default authentication filter
                // Register our custom JWT filter before Spring’s UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    /**
     * Bean for password encoding.
     * Uses BCrypt with strength 10 (default).
     * Strongly recommended for storing hashed passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the AuthenticationProvider to use:
     * - Custom UserDetailsService to load user from DB
     * - BCrypt password encoder for password verification
     * - // Optional: Define custom authentication provider if using custom UserDetailsService
     *
     * @return a configured DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // Create new authentication provider

        var provider = new DaoAuthenticationProvider();

        // Tell provider to use our custom user details service
        provider.setUserDetailsService(userDetailsService); // Set custom user service

        // Tell provider to use BCrypt password encoder
        provider.setPasswordEncoder(passwordEncoder()); // Set password encoder
        return provider;
    }

    /**
     * Exposes the AuthenticationManager bean.
     * Required if you want to perform manual authentication (e.g., in login endpoints).
     *  Bean to expose AuthenticationManager (used in login logic)
     *
     * @param config provided by Spring Boot
     * @return the AuthenticationManager
     * @throws Exception if unable to fetch
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Get AuthenticationManager from Spring context
        return config.getAuthenticationManager();
    }
}