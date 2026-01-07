package com.equeue.services;

import com.equeue.config.QueueWebSocketPublisher;
import com.equeue.entities.QueueEntry;
import com.equeue.repositories.QueueEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;

@Service
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


}
