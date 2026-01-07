package com.equeue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@AllArgsConstructor
public class ServiceResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Duration averageDuration;

    public static ServiceResponse from(ServiceEntity s) {
        return new ServiceResponse(
                s.getId(),
                s.getName(),
                s.getPrice(),
                s.getAverageDuration()
        );
    }
}

