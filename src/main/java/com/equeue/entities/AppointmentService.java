package com.equeue.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "appointment_services")
public class AppointmentService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(nullable = false)
    private String serviceNameSnapshot;

    @Column(nullable = false)
    private BigDecimal servicePriceSnapshot;

    @Column(nullable = false)
    private Integer serviceDurationSnapshotMinutes;

    private Integer quantity = 1;
}

