package com.equeue.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false)
    private String personName;

    private String contactPhone;

    private LocalDate appointmentDate;
    private LocalTime slotStartTime;
    private LocalTime slotEndTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private BigDecimal totalAmount;
    private Integer totalDurationMinutes;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /* ---------- Relationships ---------- */

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentService> services = new ArrayList<>();
}

