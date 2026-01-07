package com.equeue.repositories;

import com.equeue.entities.ServiceCatalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceCatalog,Long> {

    List<ServiceCatalog> findByOrganizerIdAndEventId(
            Long organizerId,
            Long eventId
    );

    Optional<ServiceCatalog> findByIdAndOrganizerId(
            Long id,
            Long organizerId
    );
}
