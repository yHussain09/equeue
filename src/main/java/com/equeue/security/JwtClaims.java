package com.equeue.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtClaims {

    private final String username;
    private final Long organizerId;
    private final List<GrantedAuthority> authorities;

    public JwtClaims(String username, Long organizerId, List<GrantedAuthority> authorities) {
        this.username = username;
        this.organizerId = organizerId;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
