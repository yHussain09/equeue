package com.equeue.queue.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisQueueService {

    private final RedisTemplate<String, String> redis;

    private String waitingKey(Long queueId) {
        return "queue:" + queueId + ":waiting";
    }

    private String servingKey(Long queueId) {
        return "queue:" + queueId + ":serving";
    }

    /* ================= ENQUEUE ================= */

    public void enqueue(Long queueId, Long appointmentId) {
        redis.opsForZSet().add(
                waitingKey(queueId),
                appointmentId.toString(),
                System.currentTimeMillis());
    }

    /* ================= DEQUEUE ================= */

    public Long dequeue(Long queueId) {
        Set<String> items =
                redis.opsForZSet().range(waitingKey(queueId), 0, 0);

        if (items == null || items.isEmpty()) return null;

        String appointmentId = items.iterator().next();
        redis.opsForZSet().remove(waitingKey(queueId), appointmentId);
        redis.opsForValue().set(servingKey(queueId), appointmentId);

        return Long.valueOf(appointmentId);
    }

    /* ================= FINISH ================= */

    public void finish(Long queueId) {
        redis.delete(servingKey(queueId));
    }

    /* ================= POSITION ================= */

    public Long position(Long queueId, Long appointmentId) {
        Long rank =
                redis.opsForZSet()
                        .rank(waitingKey(queueId), appointmentId.toString());

        return rank == null ? null : rank + 1;
    }

    /* ================= SIZE ================= */

    public Long size(Long queueId) {
        return redis.opsForZSet().zCard(waitingKey(queueId));
    }
}

