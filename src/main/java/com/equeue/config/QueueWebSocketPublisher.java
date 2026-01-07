package com.equeue.config;

import com.equeue.dto.response.QueueOverviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueWebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publishQueueUpdate(
            Long queueId,
            QueueOverview overview) {

        messagingTemplate.convertAndSend(
                "/topic/queues/" + queueId,
                QueueOverviewResponse.from(overview)
        );
    }
}

