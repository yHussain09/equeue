package com.equeue.api.dto.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class AppointmentCreateRequest {

    @NotNull
    private Long eventId;

    /** Multiple services selected by user */
    private List<Long> serviceIds;

    /*@NotNull
    private LocalDate appointmentDate;

    @NotNull
    private LocalTime preferredTime;*/

    @NotNull
    private LocalDateTime appointmentDateTime;
}

