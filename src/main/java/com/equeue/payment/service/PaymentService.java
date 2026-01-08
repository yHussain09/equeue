package com.equeue.payment.service;

import com.equeue.appointment.entity.Appointment;
import com.equeue.organizer.entity.Organizer;
import com.equeue.payment.entity.Payment;
import com.equeue.payment.enums.PaymentStatus;
import com.equeue.appointment.repository.AppointmentRepository;
import com.equeue.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public Payment initiate(Long appointmentId) {

        Appointment appointment =
                appointmentRepository.findById(appointmentId).orElseThrow();

        Payment payment = new Payment();
        payment.setAppointment(appointment);
        payment.setAmount(
                appointment.getService().getPrice());
        payment.setStatus(PaymentStatus.PENDING);

        return paymentRepository.save(payment);
    }

    public Payment markPaid(Long paymentId, String reference) {

        Payment payment =
                paymentRepository.findById(paymentId).orElseThrow();

        payment.setStatus(PaymentStatus.PAID);
        payment.setProviderReference(reference);

        return payment;
    }
}



/*@Service
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
}*/
