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

    /* -----------------------------
       BASIC QUERIES
       ----------------------------- */

    Optional<QueueEntry> findByAppointmentId(Long appointmentId);

    Optional<QueueEntry> findFirstByOrganizerIdAndEventIdAndStatus(
            Long organizerId,
            Long eventId,
            QueueStatus status
    );

    /* -----------------------------
       QUEUE NUMBER GENERATION (LOCKED)
       ----------------------------- */

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT q
        FROM QueueEntry q
        WHERE q.organizer.id = :organizerId
          AND q.event.id = :eventId
        ORDER BY q.queueNumber DESC
    """)
    List<QueueEntry> lockQueueForEvent(
            Long organizerId,
            Long eventId,
            Pageable pageable
    );

    /* -----------------------------
       SERVING LOGIC (LOCKED)
       ----------------------------- */

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT q
        FROM QueueEntry q
        WHERE q.organizer.id = :organizerId
          AND q.event.id = :eventId
          AND q.status = 'WAITING'
        ORDER BY q.queueNumber
    """)
    List<QueueEntry> findNextWaitingForUpdate(
            Long organizerId,
            Long eventId,
            Pageable pageable
    );

    /* -----------------------------
       ETA CALCULATION
       ----------------------------- */

    @Query("""
        SELECT COALESCE(SUM(a.totalDurationMinutes), 0)
        FROM QueueEntry q
        JOIN q.appointment a
        WHERE q.organizer.id = :organizerId
          AND q.event.id = :eventId
          AND q.queueNumber < :queueNumber
          AND q.status IN ('WAITING', 'SERVING')
    """)
    Integer calculateEtaMinutes(
            Long organizerId,
            Long eventId,
            int queueNumber
    );

    /* -----------------------------
       LIVE QUEUE DISPLAY
       ----------------------------- */

    @Query("""
        SELECT q
        FROM QueueEntry q
        JOIN FETCH q.appointment a
        WHERE q.organizer.id = :organizerId
          AND q.event.id = :eventId
          AND q.status IN ('WAITING', 'SERVING')
        ORDER BY q.queueNumber
    """)
    List<QueueEntry> findLiveQueue(
            Long organizerId,
            Long eventId
    );

    /* -----------------------------
       CANCELLATION
       ----------------------------- */

    @Modifying
    @Query("""
        UPDATE QueueEntry q
        SET q.status = 'CANCELLED'
        WHERE q.appointment.id = :appointmentId
          AND q.organizer.id = :organizerId
    """)
    void cancelByAppointment(
            Long appointmentId,
            Long organizerId
    );

    Optional<QueueEntry> findFirstByOrganizerIdAndEventIdAndStatus(
            Long organizerId,
            Long eventId,
            QueueStatus status
    );

    @Query("""
    SELECT COALESCE(MAX(q.queueNumber), 0)
    FROM QueueEntry q
    WHERE q.organizer.id = :organizerId
      AND q.event.id = :eventId
""")
    int findLastQueueNumber(Long organizerId, Long eventId);

    @Query("""
    SELECT COALESCE(SUM(a.totalDurationMinutes), 0)
    FROM QueueEntry q
    JOIN q.appointment a
    WHERE q.organizer.id = :organizerId
      AND q.event.id = :eventId
      AND q.queueNumber < :queueNumber
      AND q.status IN ('WAITING', 'SERVING')
""")
    Integer calculateEtaMinutes(
            Long organizerId,
            Long eventId,
            int queueNumber
    );

    @Query("""
    SELECT q
    FROM QueueEntry q
    JOIN FETCH q.appointment a
    WHERE q.organizer.id = :organizerId
      AND q.event.id = :eventId
      AND q.status IN ('WAITING', 'SERVING')
    ORDER BY q.queueNumber
""")
    List<QueueEntry> findLiveQueue(
            Long organizerId,
            Long eventId
    );

    @Modifying
    @Query("""
    UPDATE QueueEntry q
    SET q.status = 'COMPLETED',
        q.completedAt = CURRENT_TIMESTAMP
    WHERE q.id = :queueEntryId
""")
    void completeQueueEntry(Long queueEntryId);

    @Query("""
    SELECT q
    FROM QueueEntry q
    WHERE q.organizer.id = :organizerId
      AND q.event.id = :eventId
      AND q.status = 'WAITING'
    ORDER BY q.queueNumber
""")
    List<QueueEntry> findNextWaiting(Long organizerId, Long eventId, Pageable pageable);

    @Modifying
    @Query("""
    UPDATE QueueEntry q
    SET q.status = 'CANCELLED'
    WHERE q.appointment.id = :appointmentId
      AND q.organizer.id = :organizerId
""")
    void cancelByAppointment(Long appointmentId, Long organizerId);

    @Query("""
    SELECT q.queueNumber
    FROM QueueEntry q
    WHERE q.appointment.id = :appointmentId
""")
    Integer findQueueNumberByAppointment(Long appointmentId);

}
