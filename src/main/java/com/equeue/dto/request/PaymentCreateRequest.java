package com.equeue.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCreateRequest {

    @NotNull
    private Long appointmentId;

    @NotBlank
    private String method; // CASH, CARD, ONLINE

    private String provider; // STRIPE, JAZZCASH, EASYPAY (optional)
}

