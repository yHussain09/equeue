package com.equeue.api.dto.payment;

import com.equeue.payment.entity.Payment;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponse {

    private Long id;
    private Long appointmentId;
    private BigDecimal amount;
    private String status;
    private String reference;

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .appointmentId(payment.getAppointment().getId())
                .amount(payment.getAmount())
                .status(payment.getStatus().name())
                .reference(payment.getTransactionReference())
                .build();
    }
}
