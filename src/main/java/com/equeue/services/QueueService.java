package com.equeue.services;

import com.equeue.entities.Appointment;
import com.equeue.entities.QueueEntry;
import com.equeue.enums.QueueStatus;
import com.equeue.repositories.EventRepository;
import com.equeue.repositories.QueueEntryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class QueueService {

    private final QueueEntryRepository queueRepository;
    private final EventRepository eventRepository;

    public QueueService(QueueEntryRepository queueRepository, EventRepository eventRepository) {
        this.queueRepository = queueRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public QueueEntry addToQueue(
            Appointment appointment,
            Long organizerId,
            Long eventId
    ) {
        // LOCK queue for event
        int lastQueueNumber = queueRepository
                .lockQueueForEvent(
                        organizerId,
                        eventId,
                        PageRequest.of(0, 1)
                )
                .stream()
                .map(QueueEntry::getQueueNumber)
                .findFirst()
                .orElse(0);

        QueueEntry entry = new QueueEntry();
        entry.setOrganizer(appointment.getOrganizer());
        entry.setEvent(eventRepository.getReferenceById(eventId));
        entry.setAppointment(appointment);
        entry.setQueueNumber(lastQueueNumber + 1);
        entry.setStatus(QueueStatus.WAITING);
        entry.setEnteredAt(Instant.now());

        return queueRepository.save(entry);
    }

    @Transactional
    public QueueEntry serveNext(Long organizerId, Long eventId) {

        List<QueueEntry> waiting =
                queueRepository.findNextWaitingForUpdate(
                        organizerId,
                        eventId,
                        PageRequest.of(0, 1)
                );

        if (waiting.isEmpty()) {
            return null;
        }

        QueueEntry next = waiting.get(0);
        next.setStatus(QueueStatus.SERVING);
        next.setStartedAt(Instant.now());

        return queueRepository.save(next);
    }

    @Transactional
    public void completeServing(Long queueEntryId) {
        QueueEntry entry = queueRepository.findById(queueEntryId)
                .orElseThrow();

        entry.setStatus(QueueStatus.COMPLETED);
        entry.setCompletedAt(Instant.now());
    }

    @Transactional
    public void cancelAppointment(
            Long appointmentId,
            Long organizerId
    ) {
        queueRepository.cancelByAppointment(appointmentId, organizerId);
    }

    @Transactional(readOnly = true)
    public Integer calculateEta(
            Long organizerId,
            Long eventId,
            int queueNumber
    ) {
        return queueRepository.calculateEtaMinutes(
                organizerId,
                eventId,
                queueNumber
        );
    }
}
