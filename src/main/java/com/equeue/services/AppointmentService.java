package com.equeue.services;

import com.equeue.entities.Appointment;
import com.equeue.entities.Event;
import com.equeue.entities.Organizer;
import com.equeue.enums.AppointmentStatus;
import com.equeue.repositories.AppointmentRepository;
import com.equeue.repositories.AppointmentServiceRepository;
import com.equeue.repositories.ServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentServiceRepository appointmentServiceRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              AppointmentServiceRepository appointmentServiceRepository,
                              ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentServiceRepository = appointmentServiceRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public Appointment createAppointment(
            Organizer organizer,
            Event event,
            AppointmentType type
    ) {
        Appointment appointment = new Appointment();
        appointment.setOrganizer(organizer);
        appointment.setEvent(event);
        appointment.setType(type);
        appointment.setStatus(AppointmentStatus.CREATED);

        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void addServiceToAppointment(
            Appointment appointment,
            Long serviceId,
            int quantity
    ) {
        Service service = serviceRepository
                .findByIdAndOrganizerId(serviceId, appointment.getOrganizer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Service not found"));

        AppointmentService aps = new AppointmentService();
        aps.setAppointment(appointment);
        aps.setService(service);
        aps.setServiceNameSnapshot(service.getName());
        aps.setServicePriceSnapshot(service.getPrice());
        aps.setServiceDurationSnapshotMinutes(service.getDurationMinutes());
        aps.setQuantity(quantity);

        appointmentServiceRepository.save(aps);
    }

    @Transactional
    public void finalizeAppointment(Appointment appointment) {

        Integer totalDuration =
                appointmentServiceRepository.calculateTotalDuration(appointment.getId());

        BigDecimal totalAmount =
                appointmentServiceRepository.calculateTotalAmount(appointment.getId());

        appointment.setTotalDurationMinutes(totalDuration);
        appointment.setTotalAmount(totalAmount);
        appointment.setStatus(AppointmentStatus.IN_QUEUE);

        appointmentRepository.save(appointment);
    }

    @Transactional
    public void bookFlow(...) {

        Appointment appt = appointmentService.createAppointment(...);

        appointmentService.addServiceToAppointment(appt, serviceId1, 1);
        appointmentService.addServiceToAppointment(appt, serviceId2, 1);

        appointmentService.finalizeAppointment(appt);

        queueService.addToQueue(appt, organizerId, eventId);

        paymentService.createPayment(appt.getId(), organizer, PaymentMethod.CASH);
    }
}
