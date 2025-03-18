package com.vrtkarim.usergateway.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtService {
    String key = "this is my fucking key to secure my fucking api";
    public String extractEmail(String  token ){
        return extractClaim(token, Claims::getSubject);
    }

    private <T>  T extractClaim(String token, Function<Claims, T> getSubject) {
        final Claims claims = extractAllClaims(token);
        return getSubject.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token).getBody();
    }
    private String buildToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isTokenValid(String token,UserDetails user) {
        final String username = extractEmail(token);
        return username.equals(user.getUsername());
    }

    public String generateToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails);
    }
}
