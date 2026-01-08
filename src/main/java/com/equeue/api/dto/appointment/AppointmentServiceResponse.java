package com.equeue.api.dto.appointment;

import com.equeue.appointment.entity.AppointmentService;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AppointmentServiceResponse {

    private Long serviceId;
    private String serviceName;
    private BigDecimal price;
    private Integer durationMinutes;

    public static AppointmentServiceResponse from(AppointmentService as) {
        return AppointmentServiceResponse.builder()
                .serviceId(as.getService().getId())
                .serviceName(as.getService().getName())
                .price(as.getServicePriceSnapshot())
                .durationMinutes(as.getServiceDurationSnapshotMinutes())
                .build();
    }
}

