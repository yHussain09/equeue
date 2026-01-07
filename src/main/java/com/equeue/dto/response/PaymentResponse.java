package com.equeue.dto.response;

import com.equeue.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PaymentResponse {

    private Long paymentId;
    private Long appointmentId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String method;

    public static PaymentResponse from(Payment p) {
        return new PaymentResponse(
                p.getId(),
                p.getAppointmentId(),
                p.getAmount(),
                p.getCurrency(),
                p.getStatus().name(),
                p.getMethod().name()
        );
    }
}

