package com.equeue.api.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventCreateRequest {

    private Long organizerId;

    @NotBlank
    private String name;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private String type; // ?????
    private String recurrenceType; // MTW, REPEATED, PERIODICAL
}

