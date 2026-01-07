package com.equeue.repositories;

import com.equeue.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByTokenAndRevokedFalse(String token);

    @Modifying
    @Query("""
        update RefreshToken rt
           set rt.revoked = true
         where rt.userId = :userId
    """)
    void revokeAllByUser(Long userId);
}
