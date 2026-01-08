package com.equeue.queue.service;

import com.equeue.appointment.entity.Appointment;
import com.equeue.queue.entity.Queue;
import com.equeue.queue.entity.QueueEntry;
import com.equeue.queue.redis.RedisQueueService;
import com.equeue.queue.repository.QueueEntryRepository;
import com.equeue.queue.repository.QueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class QueueService {

    private final QueueEntryRepository entryRepo;
    private final RedisQueueService redisQueueService;

    public QueueEntry checkIn(
            Appointment appointment,
            Queue queue) {

        QueueEntry entry =
                entryRepo.save(
                        QueueEntry.waiting(appointment, queue));

        redisQueueService.enqueue(queue.getId(), appointment.getId());

        return entry;
    }

    public QueueEntry startNext(Long queueId) {

        Long appointmentId =
                redisQueueService.dequeue(queueId);

        if (appointmentId == null) return null;

        QueueEntry entry =
                entryRepo.findByAppointmentId(appointmentId)
                        .orElseThrow();

        entry.startServing();
        return entry;
    }

    public QueueEntry finishCurrent(Long queueId) {

        QueueEntry entry =
                entryRepo.findCurrentServing(queueId)
                        .orElseThrow();

        entry.finish();
        redisQueueService.finish(queueId);

        return entry;
    }

    public Duration calculateEta(Long queueId, int position) {

        long avgMinutes = 10; // from Queue or Service

        return Duration.ofMinutes(avgMinutes * position);
    }
}



/*@Service
@RequiredArgsConstructor
@Transactional
public class QueueService {

    private final QueueEntryRepository queueEntryRepository;
    private final QueueRepository queueRepository;

    *//**
     * Start serving next person
     *//*
    public QueueEntry startNext(Long queueId) {

        Queue queue = queueRepository.findById(queueId).orElseThrow();

        // Ensure no one is currently being served
        queueEntryRepository
                .findCurrentlyServing(queueId)
                .ifPresent(q -> {
                    throw new IllegalStateException("Already serving someone");
                });

        QueueEntry next =
                queueEntryRepository.findWaiting(queueId)
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException("Queue is empty"));

        next.setStartedAt(Instant.now());
        return next;
    }

    *//**
     * Finish current serving
     *//*
    public QueueEntry finishCurrent(Long queueId) {

        QueueEntry current =
                queueEntryRepository.findCurrentlyServing(queueId)
                        .orElseThrow(() ->
                                new IllegalStateException("No active serving"));

        current.setFinishedAt(Instant.now());
        return current;
    }

    *//**
     * Calculate ETA (minutes)
     *//*
    @Transactional(readOnly = true)
    public int calculateEta(Long queueId, Integer position) {

        Integer minutes =
                queueEntryRepository.calculateEtaMinutes(queueId, position);

        return minutes != null ? minutes : 0;
    }
}*/




/*@Service
@RequiredArgsConstructor
@Transactional
public class QueueService {

    private final QueueEntryRepository queueEntryRepo;
    private final QueueWebSocketPublisher queueWebSocketPublisher;
    private final PushNotificationService pushNotificationService;

    public Duration calculateETA(Long queueId, Integer queueNumber, int avgTime) {
        long peopleAhead = queueEntryRepo.countAhead(queueId, queueNumber);
        return Duration.ofMinutes(peopleAhead * avgTime);
    }

    @Transactional
    public QueueEntry moveToNext(
            Long queueId,
            Long organizerId,
            Long userId) {

        QueueEntry entry = advanceQueue(queueId, organizerId, userId);

        QueueOverview overview =
                calculateOverview(queueId, organizerId);

        queueWebSocketPublisher.publishQueueUpdate(
                queueId,
                overview
        );

        return entry;
    }

    public void notifyNextUser(
            QueueEntry entry,
            int currentServing) {

        pushNotificationService.notifyUser(
                entry.getUserId(),
                "You're next!",
                "Now serving: " + currentServing,
                Map.of(
                        "queueId", entry.getQueueId().toString(),
                        "position", entry.getPosition().toString()
                )
        );
    }
}*/
