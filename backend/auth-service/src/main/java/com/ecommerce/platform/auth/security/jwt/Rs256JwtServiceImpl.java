package com.ecommerce.platform.auth.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


/**
 * Implementation of JwtService using RS256 asymmetric singing algorithm
 */

@Component
//@ConditionalOnProperty(name ="jwt.algorithm", havingValue = "RS256")
public class Rs256JwtServiceImpl{
//        implements JwtService{

//    @Value("${jwt.privateKeyPath}")
//    private String privateKeyPath;
//
//    @Value("${jwt.publicKeyPath}")
//    private String publicKeyPath;
//
//    @Value("${jwt.expiration}")
//    private long jwtExpiration;
//
//    /**
//     * Loads the private key from a file and converts it to PrivateKey object.
//     */
//    private PrivateKey getPrivateKey() throws Exception {
//        byte[] keyBytes = Files.readAllBytes(Paths.get(privateKeyPath));
//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
//        return KeyFactory.getInstance("RSA").generatePrivate(spec);
//    }
//
//    /**
//     * Loads the public key from a file and converts it to PublicKey object.
//     */
//    private PublicKey getPublicKey() throws Exception {
//        byte[] keyBytes = Files.readAllBytes(Paths.get(publicKeyPath));
//        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
//        return KeyFactory.getInstance("RSA").generatePublic(spec);
//    }
//
//    /**
//     * Generates a JWT token signed using RS256 (asymmetric with private key).
//     */
//    @Override
//    public String generateToken(String username, Map<String, Object> extraClaims) {
//        try {
//            return Jwts.builder()
//                    .setClaims(extraClaims)
//                    .setSubject(username)
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
//                    .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
//                    .compact();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate RS256 token", e);
//        }    }
//
//    /**
//     * Extracts the username (subject) from the JWT token.
//     */
//    @Override
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    /**
//     * Parses the token using the public key and extracts a claim.
//     */
//    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
//        try {
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(getPublicKey())
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            return resolver.apply(claims);
//        } catch (Exception e) {
//            throw new RuntimeException("Invalid RS256 token", e);
//        }
//    }
//
//
//    /**
//     * Validates the token by checking username match and expiration status.
//     */
//    @Override
//    public boolean isTokenValid(String token, String username) {
//        return extractUsername(token).equals(username)
//                && !extractClaim(token, Claims::getExpiration).before(new Date());
//    }
//
//    @Override
//    public Long extractIssuedAtMillis(String token) {
//        return extractClaim(token, claims -> claims.getIssuedAt().getTime());    }
//
//    @Override
//    public Long extractExpirationMillis(String token) {
//        return extractClaim(token, claims -> claims.getExpiration().getTime());
//    }

}
