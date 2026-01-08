package com.equeue.queue.repository;

import com.equeue.queue.entity.Queue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueueRepository extends JpaRepository<Queue, Long> {

    Optional<Queue> findByEventId(Long eventId);
}

