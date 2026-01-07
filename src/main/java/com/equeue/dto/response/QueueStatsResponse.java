package com.equeue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;

@Getter
@AllArgsConstructor
public class QueueStatsResponse {

    private Integer waitingCount;
    private Duration averageServiceTime;
    private Duration estimatedWaitTime;

    public static QueueStatsResponse from(QueueStats s) {
        return new QueueStatsResponse(
                s.getWaitingCount(),
                s.getAverageServiceTime(),
                s.getEstimatedWaitTime()
        );
    }
}

