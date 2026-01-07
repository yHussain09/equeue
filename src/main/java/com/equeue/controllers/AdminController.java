package com.equeue.controllers;

import com.equeue.dto.request.ServiceCreateRequest;
import com.equeue.dto.response.ServiceResponse;
import com.equeue.entities.ServiceCatalog;
import com.equeue.services.EventService;
import com.equeue.services.ServiceCatalogService;
import com.sun.security.auth.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ServiceCatalogService serviceCatalogService;
    private final EventService eventService;
    private final QueueManagementService queueManagementService;

    /**
     * CREATE SERVICE (Salon / Clinic / Bank)
     */
    @PostMapping("/services")
    public ResponseEntity<ServiceResponse> createService(
            @RequestBody @Valid ServiceCreateRequest request,
            Authentication authentication) {

        UserPrincipal admin =
                (UserPrincipal) authentication.getPrincipal();

        ServiceCatalog service =
                serviceCatalogService.createService(
                        admin.getOrganizerId(),
                        request
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ServiceResponse.from(service));
    }

    /**
     * LIST SERVICES
     */
    @GetMapping("/services")
    public ResponseEntity<List<ServiceResponse>> listServices(
            Authentication authentication) {

        UserPrincipal admin =
                (UserPrincipal) authentication.getPrincipal();

        return ResponseEntity.ok(
                serviceCatalogService
                        .list(admin.getOrganizerId())
                        .stream()
                        .map(ServiceResponse::from)
                        .toList()
        );
    }

    /**
     * CREATE EVENT (Clinic, Branch, Doctor)
     */
    @PostMapping("/events")
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody @Valid EventCreateRequest request,
            Authentication authentication) {

        UserPrincipal admin =
                (UserPrincipal) authentication.getPrincipal();

        Event event =
                eventService.create(
                        admin.getOrganizerId(),
                        request
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(EventResponse.from(event));
    }

    /**
     * CREATE QUEUE FOR EVENT
     */
    @PostMapping("/queues")
    public ResponseEntity<QueueResponse> createQueue(
            @RequestBody @Valid QueueCreateRequest request,
            Authentication authentication) {

        UserPrincipal admin =
                (UserPrincipal) authentication.getPrincipal();

        Queue queue =
                queueManagementService.create(
                        admin.getOrganizerId(),
                        request
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(QueueResponse.from(queue));
    }
}

