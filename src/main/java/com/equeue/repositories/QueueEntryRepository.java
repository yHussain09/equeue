package com.equeue.repositories;

import com.equeue.entities.QueueEntry;
import com.equeue.enums.QueueStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {

    List<QueueEntry> findByQueueIdAndStatusOrderByQueueNumber(
            Long queueId,
            QueueStatus status
    );

    @Query("""
        select count(q) from QueueEntry q
        where q.queue.id = :queueId
        and q.queueNumber < :currentNumber
        and q.status = 'WAITING'
    """)
    long countAhead(Long queueId, Integer currentNumber);
}
