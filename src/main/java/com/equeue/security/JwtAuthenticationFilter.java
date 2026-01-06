package com.equeue.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {

                String token = authHeader.substring(7);

                JwtClaims claims = jwtService.parse(token);

                AuthenticatedUser user =
                        new AuthenticatedUser(
                                claims.getUsername(),
                                claims.getOrganizerId(),
                                claims.getAuthorities()
                        );

                JwtAuthenticationToken authentication =
                        new JwtAuthenticationToken(user);

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

                // 🔐 Set tenant
                TenantContext.setOrganizerId(claims.getOrganizerId());
            }

            filterChain.doFilter(request, response);

        } finally {
            TenantContext.clear();
        }
    }
}
