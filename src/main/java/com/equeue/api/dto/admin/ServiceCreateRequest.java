package com.equeue.api.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
public class ServiceCreateRequest {

    @NotBlank
    private Long eventId;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer durationMinutes;
}

