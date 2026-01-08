package com.equeue.security.service;

import com.equeue.api.dto.auth.LoginRequest;
import com.equeue.security.entity.RefreshToken;
import com.equeue.security.entity.User;
import com.equeue.api.dto.auth.AuthResponse;
import com.equeue.security.jwt.JwtTokenProvider;
import com.equeue.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    public AuthResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        User user =
                userRepository.findByUsername(request.getUsername())
                        .orElseThrow(() ->
                                new UsernameNotFoundException("User not found"));

        String accessToken =
                jwtTokenProvider.generateToken(user);

        RefreshToken refreshToken =
                refreshTokenService.create(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    public AuthResponse issueNewAccessToken(RefreshToken refreshToken) {

        User user = refreshToken.getUser();

        String newAccessToken =
                jwtTokenProvider.generateToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }
}

