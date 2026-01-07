package com.equeue.services;

import com.equeue.entities.Event;
import com.equeue.entities.Organizer;
import com.equeue.entities.ServiceCatalog;
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
    public ServiceCatalog createService(
            Organizer organizer,
            Event event,
            String name,
            BigDecimal price,
            int durationMinutes
    ) {
        ServiceCatalog serviceCatalog = new ServiceCatalog();
        serviceCatalog.setOrganizer(organizer);
        serviceCatalog.setEvent(event);
        serviceCatalog.setName(name);
        serviceCatalog.setPrice(price);
        serviceCatalog.setDurationMinutes(durationMinutes);

        return serviceRepository.save(serviceCatalog);
    }

    @Transactional(readOnly = true)
    public List<ServiceCatalog> listServices(
            Long organizerId,
            Long eventId
    ) {
        return serviceRepository.findByOrganizerIdAndEventId(
                organizerId,
                eventId
        );
    }
}
