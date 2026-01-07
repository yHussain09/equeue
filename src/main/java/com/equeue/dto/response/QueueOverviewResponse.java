package com.equeue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class QueueOverviewResponse {

    private Integer currentlyServing;
    private Integer nextNumber;
    private Integer waitingCount;
    private Duration averageServiceTime;

    public static QueueOverviewResponse from(QueueOverview o) {
        return new QueueOverviewResponse(
                o.getCurrentlyServing(),
                o.getNextNumber(),
                o.getWaitingCount(),
                o.getAverageServiceTime()
        );
    }
}

