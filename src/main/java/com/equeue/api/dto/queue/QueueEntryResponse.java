package com.equeue.api.dto.queue;

import com.equeue.queue.entity.QueueEntry;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QueueEntryResponse {

    private Long appointmentId;
    private Long queueId;
    private String status;
    private Integer position;

    public static QueueEntryResponse from(QueueEntry entry) {
        return QueueEntryResponse.builder()
                .appointmentId(entry.getAppointment().getId())
                .queueId(entry.getQueue().getId())
                .status(entry.getStatus().name())
//                .position(entry.getPosition())
                .build();
    }
}

