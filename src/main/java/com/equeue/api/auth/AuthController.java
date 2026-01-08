package com.equeue.api.auth;

import com.equeue.api.dto.auth.LoginRequest;
import com.equeue.security.entity.RefreshToken;
import com.equeue.api.dto.auth.AuthResponse;
import com.equeue.security.service.AuthService;
import com.equeue.security.service.RefreshTokenService;
import com.google.api.client.auth.oauth2.RefreshTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {

        RefreshToken token =
                refreshTokenService.validate(request.getRefreshToken());

        return authService.issueNewAccessToken(token);
    }
}



/*@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    *//**
     * LOGIN
     *//*
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

    *//**
     * REFRESH TOKEN
     *//*
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

    *//**
     * LOGOUT (revoke all refresh tokens)
     *//*
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
}*/
