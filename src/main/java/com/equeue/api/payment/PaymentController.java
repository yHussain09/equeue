package com.equeue.api.payment;

import com.equeue.dto.response.PaymentResponse;
import com.equeue.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('USER')")
    public PaymentResponse initiate(@RequestParam Long appointmentId) {
        return PaymentResponse.from(
                paymentService.initiate(appointmentId));
    }

    @PostMapping("/{id}/paid")
    @PreAuthorize("hasRole('ADMIN')")
    public PaymentResponse markPaid(
            @PathVariable Long id,
            @RequestParam String reference) {

        return PaymentResponse.from(
                paymentService.markPaid(id, reference));
    }
}


/*@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    *//**
     * CREATE PAYMENT FOR APPOINTMENT
     * STAFF + ADMIN
     *//*
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

    *//**
     * GET PAYMENT STATUS
     *//*
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

    *//**
     * MARK PAYMENT AS PAID (CASH / MANUAL)
     *//*
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
}*/

