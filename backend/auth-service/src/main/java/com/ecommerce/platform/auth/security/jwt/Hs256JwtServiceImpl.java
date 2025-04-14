package com.ecommerce.platform.auth.security.jwt;

import io.jsonwebtoken.Claims; // Core JJWT interfaces and classes
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // Utility to create signing keys
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


/**
 * Implementation of JwtService using HS256 symmetric signing algorithm.
 * Services responsible for handling JWT operations such as token generation,
 * extraction, and validation.
 * Best practice: Keep JWT logic separated from controller/service layers for SRP (single Responsibility principle).
 */
@Service("hs256JwtServiceImpl")
@Primary
@ConditionalOnProperty(name = "jwt.algorithm", havingValue = "HS256", matchIfMissing = true)
public final class Hs256JwtServiceImpl implements JwtService { // Marked final to prevent inheritance & promote immutability

    // Secret key for signing the JWT(injected from application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // Token validity period in milliseconds(e.g 86400000 = 1 day)
    @Value("${jwt.expiration}")
    private long jwtExpiration; // in milliseconds

    @PostConstruct
    public void init() {
        System.out.println("Hs256JwtServiceImpl initialized!");
    }

    /**
     * Returns the HMAC SHA-256, key should be at least 256 bits(32+ chars).
     * Best Practices: For HS256, key should be at least 256 bits(32+ chars).
     */
    private Key getSigningKey(){

        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    /**
     * Generate a JWT token for the given username and custom claims
     * @param username the username (subject) of the token
     * @param extraClaims Additional claims like roles, userId, etc.
     * @return A signed JWT token string
     */
    @Override
    public String generateToken(String username, Map<String,Object> extraClaims){

        return Jwts.builder()
                .setClaims(extraClaims) //custom claims
                .setSubject(username) // subject(usually the username or userId)
                .setIssuedAt(new Date()) //Issued time
                .setExpiration(new Date(System.currentTimeMillis()+jwtExpiration)) // Expiry
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // sign with secret key and algo
                .compact();// compact to String

    }


    /**
     * Extracts the username (subject) from a JWT token
     *
     * @param token The JWT token
     * @return The username embedded in the token
     */
    @Override
    public String extractUsername(String token){

        return extractClaim(token,Claims::getSubject);

    }

    /**
     * Generic method to extract any claim using a resolver function.
     *
     * @param token The JWT token
     * @param claimsResolver A function to extract a claim from Claims object
     * @param <T>
     * @return The claim value
     */

    private  <T> T extractClaim(String token, Function<Claims,T> claimsResolver){

        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Set signing key for validation
                .build()
                .parseClaimsJws(token) // Validates and parses the token
                .getBody(); // get payload (Claims)
        return claimsResolver.apply(claims);
    }

    /**
     * Used to track when token was issued
     * @param token
     * @return Time when the token was created (in ms since epoch)
     */
    @Override
    public Long extractIssuedAtMillis(String token) {
        return extractClaim(token, claims -> claims.getIssuedAt().getTime());
    }

    /**
     * Used to show remaining time or auto-logout
     * @param token
     * @return Time when the token will expire
     */
    @Override
    public Long extractExpirationMillis(String token) {
        return extractClaim(token, claims -> claims.getExpiration().getTime());
    }

    /**
     * Validates token: checks if username matches and token is not expired.
     *
     * @param token the JWT token
     * @param username The username to compare with token subject
     * @return true if valid, false otherwise
     */
    @Override
    public boolean isTokenValid(String token, String username){

        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }


    /**
     * checks whether the token is expired.
     *
     * @param token The JWT token
     * @return true if expired, false otherwise
     */
    private boolean isTokenExpired(String token){

        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
