package com.equeue.queue.entity;

import com.equeue.common.entity.BaseEntity;
import com.equeue.event.entity.Event;
import com.equeue.organizer.entity.Organizer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "queues")
@Getter
@Setter
public class Queue extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private String name;

    /**
     * Average service time in minutes
     * (fallback if service duration is missing)
     */
    private Integer averageElapsedTimeMinutes;
}