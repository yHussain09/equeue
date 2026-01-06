package com.equeue.services;

import com.equeue.entities.Event;
import com.equeue.repositories.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional(readOnly = true)
    public Event getEvent(Long eventId, Long organizerId) {
        return eventRepository
                .findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow();
    }
}
