package com.equeue.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Setter
public class QueueCreateRequest {

    @NotNull
    private Long eventId;

    @NotBlank
    private String name;

    @NotNull
    private Duration averageServiceTime;
}

