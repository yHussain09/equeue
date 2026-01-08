package com.equeue.event.entity;

import com.equeue.common.entity.BaseEntity;
import com.equeue.event.enums.EventType;
import com.equeue.organizer.entity.Organizer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    private Organizer organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_event_id")
    private Event parentEvent;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type;

    private Timestamp registrationStart;
    private Timestamp registrationEnd;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private LocalTime endTime;
}