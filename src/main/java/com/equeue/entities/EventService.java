package com.equeue.entities;

import com.equeue.base.BaseTenantEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "event_services",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "service_id"})
)
public class EventService extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    private BigDecimal priceOverride;
    private Integer durationOverrideMinutes;

    private Boolean isActive = true;
}

