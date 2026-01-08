package com.equeue.api.dto.queue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;

@Data
@AllArgsConstructor
public class EtaResponse {
    private Duration estimatedWaitTime;
}

