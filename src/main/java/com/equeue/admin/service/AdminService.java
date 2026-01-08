package com.equeue.admin.service;

import com.equeue.event.entity.Event;
import com.equeue.event.enums.EventType;
import com.equeue.event.repository.EventRepository;
import com.equeue.organizer.entity.Organizer;
import com.equeue.organizer.repository.OrganizerRepository;
import com.equeue.queue.entity.Queue;
import com.equeue.queue.repository.QueueRepository;
import com.equeue.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final OrganizerRepository organizerRepository;
    private final EventRepository eventRepository;
    private final QueueRepository queueRepository;
    private final ServiceRepository serviceRepository;

    public Organizer createOrganizer(String name) {
        Organizer o = new Organizer();
        o.setName(name);
        return organizerRepository.save(o);
    }

    public Event createEvent(Long organizerId, String name, String type, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {

        Event event = new Event();
        event.setOrganizer(this.organizerRepository.getReferenceById(organizerId));
        event.setName(name);
        event.setType(EventType.valueOf(type));
        event.setStartDate(startDate);
        event.setStartTime(startTime);
        event.setEndDate(endDate);
        event.setEndTime(endTime);

        return eventRepository.save(event);
    }

    public Queue createQueue(Long eventId, String name, Integer averageElapsedTimeMinutes) {

        Queue queue = new Queue();
        queue.setEvent(this.eventRepository.getReferenceById(eventId));
        queue.setName(name);
        queue.setAverageElapsedTimeMinutes(averageElapsedTimeMinutes);

        return queueRepository.save(queue);
    }

    public com.equeue.service.entity.Service createService(
            Long eventId,
            String name,
            BigDecimal price,
            Integer durationMinutes) {

        com.equeue.service.entity.Service service = new com.equeue.service.entity.Service();
//        service.setEvent(this.eventRepository.getReferenceById(eventId)); ????
        service.setName(name);
        service.setPrice(price);
        service.setDurationMinutes(durationMinutes);

        return serviceRepository.save(service);
    }
}

