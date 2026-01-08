package com.equeue.admin.controller;

import com.equeue.admin.service.AdminService;
import com.equeue.api.dto.admin.EventCreateRequest;
import com.equeue.api.dto.admin.QueueCreateRequest;
import com.equeue.api.dto.admin.ServiceCreateRequest;
import com.equeue.event.entity.Event;
import com.equeue.organizer.entity.Organizer;
import com.equeue.queue.entity.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/organizers")
    public ResponseEntity<Organizer> createOrganizer(@RequestParam String name) {
        Organizer organizer = adminService.createOrganizer(name);
        return ResponseEntity.ok().body(organizer);
    }

    @PostMapping("/events")
    public ResponseEntity<Event> createEvent(@RequestBody EventCreateRequest request) {
        Event event = adminService.createEvent(
                request.getOrganizerId(),
                request.getName(),
                request.getType(), request.getStartDate(), request.getStartTime(), request.getEndDate(), request.getEndTime());
        return ResponseEntity.ok(event);
    }

    @PostMapping("/queues")
    public ResponseEntity<Queue> createQueue(@RequestBody QueueCreateRequest request) {
        Queue queue = adminService.createQueue(request.getEventId(), request.getName(), request.getAverageElapsedTimeMinutes());
        return ResponseEntity.ok(queue);
    }

    @PostMapping("/services")
    public ResponseEntity<com.equeue.service.entity.Service> createService(
            @RequestBody ServiceCreateRequest request) {

        com.equeue.service.entity.Service service = adminService.createService(
                request.getEventId(),
                request.getName(),
                request.getPrice(),
                request.getDurationMinutes());
        return ResponseEntity.ok(service);
    }
}


/*@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ServiceCatalogService serviceCatalogService;
    private final EventService eventService;
    private final QueueManagementService queueManagementService;

    *//**
     * CREATE SERVICE (Salon / Clinic / Bank)
     *//*
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

    *//**
     * LIST SERVICES
     *//*
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

    *//**
     * CREATE EVENT (Clinic, Branch, Doctor)
     *//*
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

    *//**
     * CREATE QUEUE FOR EVENT
     *//*
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
}*/

