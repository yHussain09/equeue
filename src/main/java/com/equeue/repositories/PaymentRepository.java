package com.equeue.repositories;

import com.equeue.entities.Payment;
import com.equeue.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByAppointmentId(Long appointmentId);

    List<Payment> findByOrganizerIdAndStatus(
            Long organizerId,
            PaymentStatus status
    );

    @Query("""
        SELECT COALESCE(SUM(p.amount), 0)
        FROM Payment p
        WHERE p.organizer.id = :organizerId
          AND p.status = 'SUCCESS'
    """)
    BigDecimal calculateTotalRevenue(Long organizerId);
}
