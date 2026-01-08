package com.equeue.queue.entity;

import com.equeue.appointment.entity.Appointment;
import com.equeue.common.entity.BaseEntity;
import com.equeue.enums.QueueStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "queue_entries",
        indexes = {
                @Index(name = "idx_queue_active", columnList = "queue_id,status")
        })
@Getter
@Setter
public class QueueEntry extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id", nullable = false)
    private Queue queue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private Integer queueNumber;

    @Enumerated(EnumType.STRING)
    private QueueStatus status;

    /**
     * NULL → waiting
     * NOT NULL → being served / served
     */
    private Instant startedAt;

    private Instant completedAt;
}

