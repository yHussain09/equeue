package com.equeue.api.queue;

import com.equeue.api.dto.queue.EtaResponse;
import com.equeue.dto.response.QueueEntryResponse;
import com.equeue.queue.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/queues")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
public class QueueController {

    private final QueueService queueService;

    @PostMapping("/{queueId}/next")
    public QueueEntryResponse startNext(@PathVariable Long queueId) {
        return QueueEntryResponse.from(
                queueService.startNext(queueId));
    }

    @PostMapping("/{queueId}/finish")
    public QueueEntryResponse finish(@PathVariable Long queueId) {
        return QueueEntryResponse.from(
                queueService.finishCurrent(queueId));
    }

    @GetMapping("/{queueId}/eta")
    public EtaResponse eta(
            @PathVariable Long queueId,
            @RequestParam Integer position) {

        return new EtaResponse(
                queueService.calculateEta(queueId, position));
    }
}



/*@RestController
@RequestMapping("/queues")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    *//**
     * QUEUE OVERVIEW (DISPLAY SCREEN)
     *//*
    @GetMapping("/{queueId}/overview")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<QueueOverviewResponse> overview(
            @PathVariable Long queueId,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        QueueOverview overview =
                queueService.getOverview(
                        queueId,
                        user.getOrganizerId()
                );

        return ResponseEntity.ok(
                QueueOverviewResponse.from(overview)
        );
    }

    *//**
     * CURRENTLY SERVING
     *//*
    @GetMapping("/{queueId}/current")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<QueueEntryResponse> current(
            @PathVariable Long queueId,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        QueueEntry entry =
                queueService.getCurrentServing(
                        queueId,
                        user.getOrganizerId()
                );

        return ResponseEntity.ok(
                QueueEntryResponse.from(entry)
        );
    }

    *//**
     * NEXT IN QUEUE
     *//*
    @PostMapping("/{queueId}/next")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<QueueEntryResponse> next(
            @PathVariable Long queueId,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        QueueEntry entry =
                queueService.moveToNext(
                        queueId,
                        user.getOrganizerId(),
                        user.getId()
                );

        return ResponseEntity.ok(
                QueueEntryResponse.from(entry)
        );
    }

    *//**
     * PREVIOUS (OPTIONAL)
     *//*
    @PostMapping("/{queueId}/previous")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<QueueEntryResponse> previous(
            @PathVariable Long queueId,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        QueueEntry entry =
                queueService.moveToPrevious(
                        queueId,
                        user.getOrganizerId(),
                        user.getId()
                );

        return ResponseEntity.ok(
                QueueEntryResponse.from(entry)
        );
    }

    *//**
     * QUEUE STATS (WAITING COUNT, AVG TIME)
     *//*
    @GetMapping("/{queueId}/stats")
    @PreAuthorize("hasAnyRole('STAFF','ADMIN')")
    public ResponseEntity<QueueStatsResponse> stats(
            @PathVariable Long queueId,
            Authentication authentication) {

        UserPrincipal user =
                (UserPrincipal) authentication.getPrincipal();

        QueueStats stats =
                queueService.getStats(
                        queueId,
                        user.getOrganizerId()
                );

        return ResponseEntity.ok(
                QueueStatsResponse.from(stats)
        );
    }
}*/

