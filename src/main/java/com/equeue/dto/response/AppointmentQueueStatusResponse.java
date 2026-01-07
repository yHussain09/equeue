package com.equeue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class AppointmentQueueStatusResponse {

    private Integer currentlyServing;
    private Integer yourQueueNumber;
    private Duration estimatedWaitTime;

    public static AppointmentQueueStatusResponse from(QueueStatus s) {
        return new AppointmentQueueStatusResponse(
                s.getCurrentlyServing(),
                s.getYourQueueNumber(),
                s.getEstimatedWaitTime()
        );
    }
}

