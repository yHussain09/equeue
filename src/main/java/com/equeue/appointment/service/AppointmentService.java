package com.equeue.appointment.service;

import com.equeue.appointment.entity.Appointment;
import com.equeue.appointment.enums.AppointmentStatus;
import com.equeue.appointment.repository.AppointmentRepository;
import com.equeue.appointment.repository.AppointmentServiceRepository;
import com.equeue.event.entity.Event;
import com.equeue.event.repository.EventRepository;
import com.equeue.queue.entity.Queue;
import com.equeue.queue.entity.QueueEntry;
import com.equeue.queue.repository.QueueEntryRepository;
import com.equeue.security.entity.User;
import com.equeue.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentServiceRepository  appointmentServiceRepository;
    private final EventRepository eventRepository;
    private final QueueEntryRepository queueEntryRepository;

    /**
     * Book an appointment
     */
    public Appointment book(
            Long eventId,
            List<Long> serviceIds,
            User user,
            LocalDateTime appointmentTime) {

        Event event = eventRepository.findById(eventId).orElseThrow();


        com.equeue.service.entity.Service service = serviceRepository.findById(serviceId).orElseThrow();

        Appointment appointment = new Appointment();
        appointment.setEvent(event);
        appointment.setService(service);
        appointment.setUser(user);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus(AppointmentStatus.BOOKED);

        return appointmentRepository.save(appointment);
    }

    /**
     * Check-in → move to queue
     */
    public Appointment checkIn(
            Long appointmentId,
            Queue queue) {

        Appointment appointment =
                appointmentRepository.findById(appointmentId).orElseThrow();

        int nextPosition =
                queueEntryRepository.findMaxPosition(queue.getId()) + 1;

        QueueEntry entry = new QueueEntry();
        entry.setQueue(queue);
        entry.setPosition(nextPosition);
        entry.setDisplayName(appointment.getUser().getFullName());

        queueEntryRepository.save(entry);

        appointment.setQueueEntry(entry);
        appointment.setStatus(AppointmentStatus.IN_QUEUE);
        return appointment;
    }
}


/*@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final ServiceRepository serviceRepository;

    public Appointment bookAppointment(
            Long eventId,
            Long serviceId,
            LocalDateTime start,
            String name,
            String mobile
    ) {
        ServiceCatalog serviceCatalog = serviceRepository.findById(serviceId)
                .orElseThrow();

        LocalDateTime end = start.plusMinutes(serviceCatalog.getDurationMinutes());

        // conflict check
        if (!appointmentRepository
                .findConflictingAppointments(eventId, start, end)
                .isEmpty()) {
            throw new IllegalStateException("Slot not available");
        }

        Appointment appt = new Appointment();
        appt.setAppointmentStart(start);
        appt.setAppointmentEnd(end);
        appt.setCustomerName(name);
        appt.setCustomerMobile(mobile);
        appt.setServiceCatalog(serviceCatalog);
        appt.setStatus(AppointmentStatus.BOOKED);

        return appointmentRepository.save(appt);
    }

}*/
