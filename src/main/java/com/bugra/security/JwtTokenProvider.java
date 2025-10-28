package com.bugra.security;

import com.bugra.dto.UserResponse;
import com.bugra.exceptions.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateAccessToken(UserResponse payload) {

        Map<String,Object> claims = new HashMap<>();
        claims.put("id",payload.id());
        claims.put("username",payload.username());
        claims.put("email",payload.email());

        int expiration = 100 * 30;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(payload.id())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(UserResponse payload) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",payload.id());

        int expiration = 1000 * 60 * 60 * 24 * 7;
        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setSubject(payload.id())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(getSecretKey())
                .compact();
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractMail(String token){
        return extractClaim(token,claims -> claims.get("email").toString());
    }

    public String extractId(String token){
        return extractClaim(token, claims -> claims.get("id").toString());
    }

    public<T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractJti(String token){
        return extractClaim(token,Claims::getId);
    }

    public boolean isTokenExpired(String token){
        Date expiration = extractClaim(token,Claims::getExpiration);
        return expiration.before(new Date());
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build().parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        boolean isExpired = isTokenExpired(token);
        if(!extractMail(token).equals(userDetails.getUsername())) throw new UsernameNotFoundException("Invalid username or password");
        if(isExpired) throw new JwtException("jwt expired", 404);
        return true;
    }
}
