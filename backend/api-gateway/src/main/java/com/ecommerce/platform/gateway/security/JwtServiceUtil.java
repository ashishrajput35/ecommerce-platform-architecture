package com.ecommerce.platform.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtServiceUtil {

//    private static final Logger logger = LoggerFactory.getLogger(JwtServiceUtil.class);

    // Secret key for signing the JWT (injected from application.properties)
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Token validity period in milliseconds (e.g., 86400000 = 1 day)
    @Value("${jwt.expiration}")
    private long jwtExpiration; // in milliseconds

    // Method to get the signing key using the JWT secret
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Validate token and check expiration
    public boolean isTokenValid(String token) {
        try {
            // Step 1: Extract Claims
            Claims claims = extractAllClaims(token);

            // Step 2: Validate Expiration
            if (claims.getExpiration().before(new Date())) {
                return false;  // Token has expired
            }

            // Token is valid if no exceptions were thrown and expiration is valid
            return true;
        } catch (SignatureException e) {
            // Handle invalid signature
            System.out.println("Invalid signature - " + e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            // Handle expired token
            System.out.println("Token expired - " + e.getMessage());
            return false;
        } catch (Exception e) {
            // Handle any other exceptions (malformed token, etc.)
            System.out.println("Invalid token - " + e.getMessage());
            return false;
        }
    }

    // Extract username (subject) from the token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use the signing key from getSigningKey()
                .build()
                .parseClaimsJws(token) // This will throw an exception if the signature is invalid
                .getBody();
    }
}
