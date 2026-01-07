package com.equeue.entities;

import com.equeue.base.BaseTenantEntity;
import com.equeue.enums.PaymentMethod;
import com.equeue.enums.PaymentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;
    private String provider;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;



    private String transactionReference;

    private LocalDateTime createdAt;
    private LocalDateTime paidAt;
}

