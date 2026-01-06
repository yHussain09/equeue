package com.equeue.repositories;

import com.equeue.entities.Appointment;
import com.equeue.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {

    Optional<Appointment> findByIdAndOrganizerId(
            Long id,
            Long organizerId
    );

    List<Appointment> findByOrganizerIdAndEventId(
            Long organizerId,
            Long eventId
    );

    List<Appointment> findByEventIdAndStatus(
            Long eventId,
            AppointmentStatus status
    );

    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.organizer.id = :organizerId
          AND a.status = 'IN_QUEUE'
    """)
    List<Appointment> findActiveAppointments(Long organizerId);

    @Query("""
        select a from Appointment a
        where a.event.id = :eventId
        and a.appointmentStart between :from and :to
    """)
    List<Appointment> findConflictingAppointments(
            Long eventId,
            LocalDateTime from,
            LocalDateTime to
    );
}
