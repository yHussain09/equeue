package com.equeue.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticatedUser {

    private final String username;
    private final Long organizerId;
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthenticatedUser(String username, Long organizerId, Collection<? extends GrantedAuthority> authorities) {
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
