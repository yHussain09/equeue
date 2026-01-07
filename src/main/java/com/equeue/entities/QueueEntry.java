package com.equeue.entities;

import com.equeue.base.BaseTenantEntity;
import com.equeue.enums.QueueStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(
        name = "queue_entries",
        indexes = {
                @Index(name = "idx_queue_org_event", columnList = "organizer_id,event_id"),
                @Index(name = "idx_queue_number", columnList = "queue_number")
        }
) // Actual people waiting in the queue / live waiting
public class QueueEntry extends BaseTenantEntity {

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

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

}

