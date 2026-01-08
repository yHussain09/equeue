package com.equeue.api.appointment;


import com.equeue.api.dto.appointment.AppointmentCreateRequest;
import com.equeue.appointment.enums.AppointmentStatus;
import com.equeue.dto.response.AppointmentResponse;
import com.equeue.appointment.entity.Appointment;
import com.equeue.appointment.service.AppointmentService;
import com.equeue.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

   /* public Appointment createAppointment(AppointmentCreateRequest request) {

        Appointment appointment = appointmentRepository.save(
                Appointment.builder()
                        .event(event)
                        .user(currentUser)
                        .status(AppointmentStatus.CREATED)
                        .appointmentTime(request.getAppointmentTime())
                        .build()
        );

        for (Long serviceId : request.getServiceIds()) {
            Service service = serviceRepository.findById(serviceId).orElseThrow();

            appointmentServiceRepository.save(
                    AppointmentService.builder()
                            .appointment(appointment)
                            .service(service)
                            .priceAtBooking(service.getPrice())
                            .durationMinutes(service.getDurationMinutes())
                            .build()
            );
        }

        return appointment;
    }*/


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public AppointmentResponse book(
            @RequestBody AppointmentCreateRequest request,
            @AuthenticationPrincipal User user) {

        Appointment appointment =
                appointmentService.book(
                        request.getEventId(),
                        request.getServiceIds(),
                        user,
                        request.getAppointmentDateTime());

        return AppointmentResponse.from(appointment);
    }

    @PostMapping("/{appointmentId}/check-in")
    @PreAuthorize("hasRole('USER')")
    public AppointmentResponse checkIn(
            @PathVariable Long appointmentId,
            @RequestParam Long queueId) {

        Appointment appointment = appointmentService.checkIn(appointmentId, queueId);

        return AppointmentResponse.from(appointment);
    }
}




/*@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    *//**
     * BOOK APPOINTMENT
     * STAFF + ADMIN
     *//*
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

    *//**
     * GET APPOINTMENT BY ID
     * STAFF + ADMIN
     *//*
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

    *//**
     * MY APPOINTMENTS (END USER / STAFF)
     *//*
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

    *//**
     * CURRENT QUEUE STATUS (PUBLIC-FACING)
     *//*
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

    *//**
     * CANCEL APPOINTMENT
     *//*
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
}*/
