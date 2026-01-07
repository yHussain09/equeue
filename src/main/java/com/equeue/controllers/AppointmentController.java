package com.equeue.controllers;


import com.equeue.dto.request.AppointmentCreateRequest;
import com.equeue.dto.response.AppointmentResponse;
import com.equeue.entities.Appointment;
import com.equeue.enums.QueueStatus;
import com.equeue.services.AppointmentService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    /**
     * BOOK APPOINTMENT
     * STAFF + ADMIN
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @RequestBody @Valid AppointmentCreateRequest request,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        Appointment appointment =
                appointmentService.bookAppointment(
                        user.getOrganizerId(),
                        user.getId(),
                        request
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AppointmentResponse.from(appointment));
    }

    /**
     * GET APPOINTMENT BY ID
     * STAFF + ADMIN
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<AppointmentResponse> getById(
            @PathVariable Long id,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        Appointment appointment =
                appointmentService.getById(
                        id,
                        user.getOrganizerId()
                );

        return ResponseEntity.ok(
                AppointmentResponse.from(appointment)
        );
    }

    /**
     * MY APPOINTMENTS (END USER / STAFF)
     */
    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<List<AppointmentResponse>> myAppointments(
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(
                appointmentService
                        .getAppointmentsForUser(
                                user.getId(),
                                user.getOrganizerId()
                        )
                        .stream()
                        .map(AppointmentResponse::from)
                        .toList()
        );
    }

    /**
     * CURRENT QUEUE STATUS (PUBLIC-FACING)
     */
    @GetMapping("/{id}/queue-status")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<AppointmentQueueStatusResponse> queueStatus(
            @PathVariable Long id,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        QueueStatus status =
                appointmentService.getQueueStatus(
                        id,
                        user.getOrganizerId()
                );

        return ResponseEntity.ok(
                AppointmentQueueStatusResponse.from(status)
        );
    }

    /**
     * CANCEL APPOINTMENT
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<Void> cancel(
            @PathVariable Long id,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        appointmentService.cancelAppointment(
                id,
                user.getOrganizerId(),
                user.getId()
        );

        return ResponseEntity.noContent().build();
    }
}
