package com.equeue.services;

import com.equeue.event.entity.Event;
import com.equeue.organizer.entity.Organizer;
import com.equeue.service.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public com.equeue.service.entity.Service createService(
            Organizer organizer,
            Event event,
            String name,
            BigDecimal price,
            int durationMinutes
    ) {
        com.equeue.service.entity.Service service = new com.equeue.service.entity.Service();
//        service.setOrganizer(organizer);
        service.setEvent(event);
        service.setName(name);
        service.setPrice(price);
        service.setDurationMinutes(durationMinutes);

        return serviceRepository.save(service);
    }

    @Transactional(readOnly = true)
    public List<com.equeue.service.entity.Service> listServices(
            Long organizerId,
            Long eventId
    ) {
        return serviceRepository.findByOrganizerIdAndEventId(
                organizerId,
                eventId
        );
    }
}
