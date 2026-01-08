package com.equeue.dto.response;

import com.equeue.event.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public static EventResponse from(Event e) {
        return new EventResponse(
                e.getId(),
                e.getName(),
                e.getStartDate(),
                e.getEndDate(),
                e.getStartTime(),
                e.getEndTime()
        );
    }
}

