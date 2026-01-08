package com.equeue.entities;

import com.equeue.common.entity.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter @Setter
public class UserDevice extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(length = 255)
    private String fcmToken;

    private Instant lastUpdated;
}
