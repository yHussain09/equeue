package com.equeue.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtClaims {

    private final String username;
    private final Long organizerId;
    private final List<GrantedAuthority> authorities;
}
