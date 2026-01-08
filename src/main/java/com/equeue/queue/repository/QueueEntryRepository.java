package com.equeue.queue.repository;

import com.equeue.queue.entity.QueueEntry;
import com.equeue.enums.QueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {

    /**
     * Current serving entry
     */
    @Query("""
            select qe from QueueEntry qe
            where qe.queue.id = :queueId
              and qe.startedAt is not null
              and qe.finishedAt is null
            """)
    Optional<QueueEntry> findCurrentlyServing(Long queueId);

    /**
     * Next waiting entry
     */
    @Query("""
            select qe from QueueEntry qe
            where qe.queue.id = :queueId
              and qe.startedAt is null
            order by qe.position asc
            """)
    List<QueueEntry> findWaiting(Long queueId);

    /**
     * Count people ahead
     */
    @Query("""
            select count(qe) from QueueEntry qe
            where qe.queue.id = :queueId
              and qe.position < :position
              and qe.startedAt is null
            """)
    long countAhead(Long queueId, Integer position);

    /**
     * Max position (for enqueue)
     */
    @Query("""
            select coalesce(max(qe.position), 0)
            from QueueEntry qe
            where qe.queue.id = :queueId
            """)
    int findMaxPosition(Long queueId);

    /**
     * ETA calculation (minutes)
     */
    @Query("""
            select sum(s.durationMinutes)
            from QueueEntry qe
            join Appointment a on a.queueEntry = qe
            join Service s on a.service = s
            where qe.queue.id = :queueId
              and qe.position < :position
              and qe.startedAt is null
            """)
    Integer calculateEtaMinutes(Long queueId, Integer position);

    @Query("""
               select qe
               from QueueEntry qe
               where qe.status = 'WAITING'
            """)
    List<QueueEntry> findAllWaiting();

}


/*public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {

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
}*/
