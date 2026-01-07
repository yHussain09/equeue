package com.equeue.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentCreateRequest(
        @NotNull Long eventId,
        @NotNull Long serviceId,
        @NotNull LocalDate appointmentDate,
        @NotNull LocalTime preferredTime
        ) {}
