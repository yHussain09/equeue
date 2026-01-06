package com.equeue.services;

import com.equeue.entities.Appointment;
import com.equeue.entities.Organizer;
import com.equeue.entities.Payment;
import com.equeue.enums.PaymentStatus;
import com.equeue.repositories.AppointmentRepository;
import com.equeue.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public PaymentService(PaymentRepository paymentRepository, AppointmentRepository appointmentRepository) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public Payment createPayment(
            Long appointmentId,
            Organizer organizer,
            PaymentMethod method
    ) {
        Appointment appointment = appointmentRepository
                .findByIdAndOrganizerId(appointmentId, organizer.getId())
                .orElseThrow();

        Payment payment = new Payment();
        payment.setOrganizer(organizer);
        payment.setAppointment(appointment);
        payment.setAmount(appointment.getTotalAmount());
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(Instant.now());

        return paymentRepository.save(payment);
    }

    @Transactional
    public void markPaymentSuccess(Long paymentId, String ref) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow();

        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setExternalReference(ref);
        payment.setPaidAt(Instant.now());
    }

    @Transactional
    public void markPaymentFailed(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow();

        payment.setStatus(PaymentStatus.FAILED);
    }
}
