package com.equeue.services;

import com.equeue.entities.RefreshToken;
import com.equeue.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
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
}
