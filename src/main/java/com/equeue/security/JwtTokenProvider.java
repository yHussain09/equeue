package com.equeue.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secret = "very-secret-key";
    private final long accessTokenMs = 15 * 60 * 1000; // 15 min

    public String createAccessToken(
            String username,
            Long organizerId,
            Collection<? extends GrantedAuthority> roles) {

        return Jwts.builder()
                .setSubject(username)
                .claim("organizer_id", organizerId)
                .claim("roles", roles.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + accessTokenMs)
                )
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
