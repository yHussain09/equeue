package com.equeue.security.service;

import com.equeue.security.entity.RefreshToken;
import com.equeue.security.entity.User;
import com.equeue.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${security.refresh-token.expiry-days:7}")
    private long expiryDays;

    public RefreshToken create(Long userId, Long organizerId) {

        RefreshToken token = new RefreshToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUserId(userId);
        token.setOrganizerId(organizerId);
        token.setExpiryDate(
                Instant.now().plus(30, ChronoUnit.DAYS));

        return this.refreshTokenRepository.save(token);
    }

    public RefreshToken create(User user) {

        RefreshToken token = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(
                        Instant.now().plus(expiryDays, ChronoUnit.DAYS))
                .build();

        return this.refreshTokenRepository.save(token);
    }


    public RefreshToken validate(String token) {

        RefreshToken refreshToken =
                this.refreshTokenRepository.findByTokenAndRevokedFalse(token)
                        .orElseThrow(() ->
                                new IllegalStateException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            this.refreshTokenRepository.delete(refreshToken);
            throw new IllegalStateException("Refresh token expired");
        }

        return refreshToken;
    }

    public void revokeAll(Long userId) {
        this.refreshTokenRepository.deleteByUserId(userId);
    }
}


/*@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshToken create(Long userId, Long organizerId) {

        RefreshToken token = new RefreshToken();
        token.setUserId(userId);
        token.setOrganizerId(organizerId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS));

        return repository.save(token);
    }

    public RefreshToken validate(String token) {

        RefreshToken refreshToken = repository
                .findByTokenAndRevokedFalse(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    public void revokeAll(Long userId) {
        repository.revokeAllByUser(userId);
    }
}*/
