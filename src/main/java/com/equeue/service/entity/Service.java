package com.equeue.service.entity;

import com.equeue.common.entity.BaseEntity;
import com.equeue.event.entity.Event;
import com.equeue.organizer.entity.Organizer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "services")
@Getter
@Setter
public class Service extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;*/

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Expected time for service (minutes)
     */
    @Column(nullable = false)
    private Integer durationMinutes;

    private Boolean isActive;
}

