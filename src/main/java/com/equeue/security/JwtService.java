package com.equeue.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;

@Service
public class JwtService {

    private final Key key = Keys.hmacShaKeyFor(
            "very-secret-key-should-be-long".getBytes()
    );

    public JwtClaims parse(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        Long organizerId = claims.get("organizer_id", Long.class);

        List<String> roles = claims.get("roles", List.class);

        List<GrantedAuthority> authorities =
                roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        return new JwtClaims(username, organizerId, authorities);
    }
}
