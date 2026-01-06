package com.equeue.services;

import com.equeue.entities.Event;
import com.equeue.entities.Organizer;
import com.equeue.entities.Service;
import com.equeue.repositories.ServiceRepository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class ServiceCatalogService {

    private final ServiceRepository serviceRepository;

    public ServiceCatalogService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public Service createService(
            Organizer organizer,
            Event event,
            String name,
            BigDecimal price,
            int durationMinutes
    ) {
        Service service = new Service();
        service.setOrganizer(organizer);
        service.setEvent(event);
        service.setName(name);
        service.setPrice(price);
        service.setDurationMinutes(durationMinutes);

        return serviceRepository.save(service);
    }

    @Transactional(readOnly = true)
    public List<Service> listServices(
            Long organizerId,
            Long eventId
    ) {
        return serviceRepository.findByOrganizerIdAndEventId(
                organizerId,
                eventId
        );
    }
}
