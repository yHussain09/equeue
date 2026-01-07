package com.equeue.controllers;

import com.equeue.dto.request.LoginRequest;
import com.equeue.dto.request.RefreshRequest;
import com.equeue.dto.response.TokenResponse;
import com.equeue.entities.RefreshTokenEntity;
import com.equeue.security.JwtTokenProvider;
import com.equeue.services.RefreshTokenService;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    @PostMapping("/auth/login")
    public TokenResponse login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserPrincipal user = (UserPrincipal) auth.getPrincipal();

        String accessToken = jwtTokenProvider.createAccessToken(
                user.getUsername(),
                user.getOrganizerId(),
                user.getAuthorities()
        );

        RefreshTokenEntity refreshToken =
                refreshTokenService.create(
                        user.getId(),
                        user.getOrganizerId()
                );

        return new TokenResponse(accessToken, refreshToken.getToken(), );
    }

    @PostMapping("/auth/refresh")
    public TokenResponse refresh(@RequestBody RefreshRequest request) {

        RefreshTokenEntity refreshToken =
                refreshTokenService.validate(request.refreshToken());

        UserPrincipal user =
                userService.loadById(refreshToken.getUserId());

        String newAccessToken =
                jwtTokenProvider.createAccessToken(
                        user.getUsername(),
                        refreshToken.getOrganizerId(),
                        user.getAuthorities()
                );

        return new TokenResponse(newAccessToken, refreshToken.getToken());
    }

    @PostMapping("/auth/logout")
    public void logout(Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        refreshTokenService.revokeAll(user.getId());
    }

}
