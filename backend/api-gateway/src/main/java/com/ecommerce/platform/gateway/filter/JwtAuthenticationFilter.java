package com.ecommerce.platform.gateway.filter;

import com.ecommerce.platform.gateway.security.JwtServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * Filter to Check JWT Token in Request Header
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtServiceUtil jwtServiceUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getPath().value();

        System.out.println("path - "+path);

        // Step 0: Allow public endpoints without token check
        if (path.startsWith("/api/v1/auth") || path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        System.out.println("path ---1 ");

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // If No Token Present

        System.out.println("authHeader --- "+authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("authHeader inside null--- "+authHeader);

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Step 2: Extract token
        String token = authHeader.substring(7); // Remove "Bearer "

        System.out.println("token---"+token);

        // In Real World → Validate this Token
        // Step 3: Validate token
        if (!jwtServiceUtil.isTokenValid(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            System.out.println("Unauthrized - ");
            return exchange.getResponse().setComplete();
        }
        System.out.println("authrized - ");

        // Step 4 (Optional): Extract username (or role)
        String username = jwtServiceUtil.extractUsername(token);
        System.out.println("username - "+username);

        // Create authentication object
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, null, Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );

        // Set authentication in the Reactive Security Context
        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));


    }
}
