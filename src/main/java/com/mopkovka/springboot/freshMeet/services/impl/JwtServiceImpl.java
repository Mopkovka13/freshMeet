package com.mopkovka.springboot.freshMeet.services.impl;

import com.mopkovka.springboot.freshMeet.domain.UserEntity;
import com.mopkovka.springboot.freshMeet.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Primary
public class JwtServiceImpl implements JwtService {
    @Value("${JWT_KEY}")
    private String jwtSigningKey;

    /*
     * Generate token for use
     * @param userDetails - user details to be used for generating token
     * @return generated token
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof UserEntity customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("role", customUserDetails.getRole());
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 100000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    /*
     * Validate token
     * @param token - token to be validated
     * @param userDetails - user details to be used for validating token
     * @return true if token is valid, false otherwise
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUsername(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /*
     * Extract username from token
     * @param token - token to be extracted
     * @return username from token
     */
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
     * Extract claim from token
     * @param token - token to be extracted
     * @param claimsResolvers - function to extract claim from token
     * @return claim from token
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {

        final Claims claims = Jwts
                .parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolvers.apply(claims);
    }

    /*
     * Check if token is expired
     * @param token - token to be checked
     * @return true if token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);

        return expiration.before(new Date());
    }

    /*
     * Get signing key
     * @return signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
