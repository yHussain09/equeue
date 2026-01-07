package com.equeue.dto.response;

import com.equeue.entities.QueueEntry;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueueEntryResponse {

    private Long appointmentId;
    private Integer queueNumber;
    private String customerName;
    private String status;

    public static QueueEntryResponse from(QueueEntry e) {
        return new QueueEntryResponse(
                e.getAppointmentId(),
                e.getQueueNumber(),
                e.getCustomerName(),
                e.getStatus().name()
        );
    }
}

