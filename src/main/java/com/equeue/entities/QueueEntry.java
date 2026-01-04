package com.equeue.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "queue_entries",
        indexes = @Index(name = "idx_queue_active", columnList = "queue_id,status"))
public class QueueEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id")
    private Queue queue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private Integer queueNumber;

    @Enumerated(EnumType.STRING)
    private QueueStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}

