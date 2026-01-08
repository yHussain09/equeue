package com.equeue.api.dto.appointment;

import com.equeue.appointment.entity.Appointment;
import com.equeue.appointment.entity.AppointmentService;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class AppointmentResponse {

    private Long id;
    private Long eventId;
    private String status;
    private LocalDateTime appointmentTime;

    /** NEW */
    private List<AppointmentServiceResponse> services;

    private BigDecimal totalAmount;
    private Integer totalDurationMinutes;

    public static AppointmentResponse from(Appointment appointment) {

        List<AppointmentServiceResponse> services =
                appointment.getServices()
                        .stream()
                        .map(AppointmentServiceResponse::from)
                        .toList();

        BigDecimal totalAmount =
                appointment.getServices()
                        .stream()
                        .map(AppointmentService::getServicePriceSnapshot)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalDuration =
                appointment.getServices()
                        .stream()
                        .map(AppointmentService::getServiceDurationSnapshotMinutes)
                        .reduce(0, Integer::sum);

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .eventId(appointment.getEvent().getId())
                .status(appointment.getStatus().name())
                .appointmentTime(appointment.getAppointmentTime())
                .services(services)
                .totalAmount(totalAmount)
                .totalDurationMinutes(totalDuration)
                .build();
    }
}



