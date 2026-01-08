package com.equeue.service.repository;

import com.equeue.service.entity.Service;
import com.equeue.service.entity.ServiceCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service,Long> {

    List<Service> findByOrganizerIdAndEventId(
            Long organizerId,
            Long eventId
    );

    Optional<Service> findByIdAndOrganizerId(
            Long id,
            Long organizerId
    );
}
