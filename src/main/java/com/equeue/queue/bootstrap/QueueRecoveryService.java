package com.equeue.queue.bootstrap;

import com.equeue.queue.entity.QueueEntry;
import com.equeue.queue.redis.RedisQueueService;
import com.equeue.queue.repository.QueueEntryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueRecoveryService {

    private final QueueEntryRepository queueEntryRepository;
    private final RedisQueueService redisQueueService;

    @PostConstruct
    public void rebuildQueues() {

        // Clear existing Redis queues (optional but recommended)
        // redisQueueService.clearAll();

        List<QueueEntry> waitingEntries =
                queueEntryRepository.findAllWaiting();

        waitingEntries.forEach(entry ->
                redisQueueService.enqueue(
                        entry.getQueue().getId(),
                        entry.getAppointment().getId()));
    }
}

