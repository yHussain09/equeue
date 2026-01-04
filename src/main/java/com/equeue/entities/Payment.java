package com.equeue.entities;

import com.equeue.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private BigDecimal amount;
    private String currency;

    private String method;
    private String provider;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionReference;

    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
}

