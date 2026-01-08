package com.equeue.payment.entity;

import com.equeue.appointment.entity.Appointment;
import com.equeue.common.entity.BaseEntity;
import com.equeue.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @Column(nullable = false)
    private BigDecimal amount;
    private String currency;

    private String method;
    private String provider;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionReference;

    private LocalDateTime paidAt;
}
