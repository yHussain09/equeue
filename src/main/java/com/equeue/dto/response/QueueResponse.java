package com.equeue.dto.response;

import com.equeue.entities.Queue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueueResponse {

    private Long id;
    private String name;
    private Integer currentlyServing;

    public static QueueResponse from(Queue q) {
        return new QueueResponse(
                q.getId(),
                q.getName(),
                q.getCurrentlyServing()
        );
    }
}

