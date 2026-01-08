package com.equeue.repositories;

import com.equeue.appointment.entity.AppointmentService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface AppointmentServiceRepository extends JpaRepository<AppointmentService, Long> {

    List<AppointmentService> findByAppointmentId(Long appointmentId);

    @Query("""
        SELECT COALESCE(SUM(
            aps.serviceDurationSnapshotMinutes * aps.quantity
        ), 0)
        FROM AppointmentService aps
        WHERE aps.appointment.id = :appointmentId
    """)
    Integer calculateTotalDuration(Long appointmentId);

    @Query("""
        SELECT COALESCE(SUM(
            aps.servicePriceSnapshot * aps.quantity
        ), 0)
        FROM AppointmentService aps
        WHERE aps.appointment.id = :appointmentId
    """)
    BigDecimal calculateTotalAmount(Long appointmentId);
}
