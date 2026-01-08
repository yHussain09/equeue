package com.equeue.api.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Duration;

@Data
public class QueueCreateRequest {

    @NotNull
    private Long eventId;

    @NotBlank
    private String name;

    /*@NotNull
    private Duration averageServiceTime;*/

    private Integer averageElapsedTimeMinutes;
}

