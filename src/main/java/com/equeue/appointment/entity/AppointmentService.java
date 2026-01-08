package com.equeue.appointment.entity;

import com.equeue.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "appointment_services")
@Getter
@Setter
public class AppointmentService extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private com.equeue.service.entity.Service service;

    @Column(nullable = false)
    private String serviceNameSnapshot;

    @Column(nullable = false)
    private BigDecimal servicePriceSnapshot;

    @Column(nullable = false)
    private Integer serviceDurationSnapshotMinutes;

    private Integer quantity = 1;
}

