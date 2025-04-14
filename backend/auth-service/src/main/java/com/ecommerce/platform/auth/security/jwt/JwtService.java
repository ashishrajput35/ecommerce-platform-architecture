package com.ecommerce.platform.auth.security.jwt;

import io.jsonwebtoken.Claims;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {

    String generateToken(String username, Map<String,Object> extraClaims);

    String extractUsername(String token);

    boolean isTokenValid(String token, String username);

//    <T> T extractClaim(String token, Function<Claims, T> claimsResolver); // <-- Add this!

    // Add these so AuthService can use them
    Long extractIssuedAtMillis(String token);
    Long extractExpirationMillis(String token);



}
