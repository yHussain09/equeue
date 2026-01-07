package com.equeue.controllers;

import com.equeue.entities.Payment;
import com.equeue.services.PaymentService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * CREATE PAYMENT FOR APPOINTMENT
     * STAFF + ADMIN
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<PaymentResponse> create(
            @RequestBody @Valid PaymentCreateRequest request,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        Payment payment =
                paymentService.createPayment(
                        user.getOrganizerId(),
                        user.getId(),
                        request
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PaymentResponse.from(payment));
    }

    /**
     * GET PAYMENT STATUS
     */
    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<PaymentStatusResponse> status(
            @PathVariable Long paymentId,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        Payment payment =
                paymentService.getById(
                        paymentId,
                        user.getOrganizerId()
                );

        return ResponseEntity.ok(
                PaymentStatusResponse.from(payment)
        );
    }

    /**
     * MARK PAYMENT AS PAID (CASH / MANUAL)
     */
    @PostMapping("/{paymentId}/mark-paid")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<PaymentStatusResponse> markPaid(
            @PathVariable Long paymentId,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        Payment payment =
                paymentService.markAsPaid(
                        paymentId,
                        user.getOrganizerId(),
                        user.getId()
                );

        return ResponseEntity.ok(
                PaymentStatusResponse.from(payment)
        );
    }
}

