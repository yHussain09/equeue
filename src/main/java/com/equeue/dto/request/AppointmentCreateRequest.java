package com.equeue.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AppointmentCreateRequest {

    @NotNull
    private Long eventId;

    @NotNull
    private Long serviceId;

    @NotNull
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime preferredTime;
}

