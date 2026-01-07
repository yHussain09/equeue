package com.equeue.controllers;

import com.equeue.dto.request.LoginRequest;
import com.equeue.dto.request.RefreshRequest;
import com.equeue.dto.response.LogoutResponse;
import com.equeue.dto.response.TokenResponse;
import com.equeue.entities.RefreshToken;
import com.equeue.security.JwtTokenProvider;
import com.equeue.services.RefreshTokenService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    /**
     * LOGIN
     */
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @RequestBody @Valid LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        String accessToken =
                jwtTokenProvider.createAccessToken(
                        user.getUsername(),
                        user.getOrganizerId(),
                        user.getAuthorities()
                );

        RefreshToken refreshToken =
                refreshTokenService.create(
                        user.getId(),
                        user.getOrganizerId()
                );

        return ResponseEntity.ok(
                new TokenResponse(
                        accessToken,
                        refreshToken.getToken(),
                        "Bearer"
                )
        );
    }

    /**
     * REFRESH TOKEN
     */
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(
            @RequestBody @Valid RefreshRequest request) {

        RefreshToken refreshToken =
                refreshTokenService.validate(request.getRefreshToken());

        UserPrincipal user =
                userService.loadById(refreshToken.getUserId());

        String newAccessToken =
                jwtTokenProvider.createAccessToken(
                        user.getUsername(),
                        refreshToken.getOrganizerId(),
                        user.getAuthorities()
                );

        return ResponseEntity.ok(
                new TokenResponse(
                        newAccessToken,
                        refreshToken.getToken(),
                        "Bearer"
                )
        );
    }

    /**
     * LOGOUT (revoke all refresh tokens)
     */
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        refreshTokenService.revokeAll(user.getId());

        return ResponseEntity.ok(
                new LogoutResponse("Logged out successfully")
        );
    }
}
