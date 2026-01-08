package com.equeue.security.entity;

import com.equeue.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
public class RefreshToken extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long organizerId;

    @Column(nullable = false)
    private Instant expiryDate;

    private boolean revoked = false;
}

