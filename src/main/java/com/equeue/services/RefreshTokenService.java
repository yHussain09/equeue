package com.equeue.services;

import com.equeue.entities.RefreshTokenEntity;
import com.equeue.repositories.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenEntity create(Long userId, Long organizerId) {

        RefreshTokenEntity token = new RefreshTokenEntity();
        token.setUserId(userId);
        token.setOrganizerId(organizerId);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS));

        return repository.save(token);
    }

    public RefreshTokenEntity validate(String token) {

        RefreshTokenEntity refreshToken = repository
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
