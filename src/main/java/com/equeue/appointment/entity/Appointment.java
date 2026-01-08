package com.equeue.appointment.entity;

import com.equeue.appointment.enums.AppointmentStatus;
import com.equeue.common.entity.BaseEntity;
import com.equeue.event.entity.Event;
import com.equeue.organizer.entity.Organizer;
import com.equeue.queue.entity.QueueEntry;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;*/

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;*/

    @Column(nullable = false)
    private String personName;

    private String contactPhone;

    private LocalDate appointmentDate;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_entry_id")
    private QueueEntry queueEntry;

    /* ---------- Relationships ---------- */
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<com.equeue.appointment.entity.AppointmentService> services = new ArrayList<>();
}

