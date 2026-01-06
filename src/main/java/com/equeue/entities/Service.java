package com.equeue.entities;

import com.equeue.base.BaseTenantEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
public class Service extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @Column(nullable = false)
    private String name;

    private String description;

    private BigDecimal price;
    private Integer durationMinutes;

    private Boolean isActive = true;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
