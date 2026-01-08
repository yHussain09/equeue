package com.equeue.security.jwt;

import com.equeue.security.entity.Role;
import com.equeue.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.conscrypt.ct.DigitallySigned;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.access-token-expiry}")
    private long accessTokenExpiry;

    public String generateToken(User user) {

        Date now = new Date();
        Date expiry =
                new Date(now.getTime() + accessTokenExpiry * 60 * 1000);

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles",
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .toList())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()),
                        DigitallySigned.SignatureAlgorithm.HS256)
                .compact();
    }

    public String createAccessToken(
            String username,
            Long organizerId,
            Collection<? extends GrantedAuthority> authorities) {

        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(username)
                .claim("org", organizerId)
                .claim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessTokenExpiry)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), DigitallySigned.SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

