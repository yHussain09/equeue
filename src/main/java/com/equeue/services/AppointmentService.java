package com.equeue.services;

import com.equeue.entities.Appointment;
import com.equeue.entities.ServiceCatalog;
import com.equeue.enums.AppointmentStatus;
import com.equeue.repositories.AppointmentRepository;
import com.equeue.repositories.AppointmentServiceRepository;
import com.equeue.repositories.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
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

}
