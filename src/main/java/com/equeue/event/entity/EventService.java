package com.equeue.event.entity;

import com.equeue.common.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "event_services",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "service_id"})
)
public class EventService extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private com.equeue.service.entity.Service service;

    private BigDecimal priceOverride;
    private Integer durationMinutesOverride;

    private Boolean isActive = true;
}

