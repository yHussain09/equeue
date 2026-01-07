package com.equeue.dto.request;

import com.equeue.entities.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class AppointmentResponse {

    private Long id;
    private Long eventId;
    private Long serviceId;
    private Integer queueNumber;
    private String status;
    private Duration estimatedWaitTime;

    public static AppointmentResponse from(Appointment a) {
        return new AppointmentResponse(
                a.getId(),
                a.getEventId(),
                a.getServiceId(),
                a.getQueueNumber(),
                a.getStatus().name(),
                a.getEstimatedWaitTime()
        );
    }
}

