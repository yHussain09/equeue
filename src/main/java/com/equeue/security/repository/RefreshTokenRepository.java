package com.equeue.security.repository;

import com.equeue.security.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {


    @Modifying
    @Query("""
        update RefreshToken rt
           set rt.revoked = true
         where rt.userId = :userId
    """)
    void revokeAllByUser(Long userId);

    Optional<RefreshToken> findByTokenAndRevokedFalse(String token);

    void deleteByUserId(Long userId);
}
