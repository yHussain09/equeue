package com.equeue.dto.response;

import com.equeue.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class PaymentStatusResponse {

    private Long paymentId;
    private String status;
    private Instant paidAt;

    public static PaymentStatusResponse from(Payment p) {
        return new PaymentStatusResponse(
                p.getId(),
                p.getStatus().name(),
                p.getPaidAt()
        );
    }
}

