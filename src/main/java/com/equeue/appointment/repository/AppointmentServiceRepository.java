package com.equeue.appointment.repository;

import com.equeue.appointment.entity.AppointmentService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentServiceRepository extends JpaRepository<AppointmentService, Long> {
}
