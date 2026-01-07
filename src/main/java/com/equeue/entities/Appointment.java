package com.equeue.entities;

import com.equeue.base.BaseTenantEntity;
import com.equeue.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "appointments") // (booking)
public class Appointment extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;
*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    private ServiceCatalog serviceCatalog;

    @Column(nullable = false)
    private String customerName;
    private String customerMobile;

    private LocalDate appointmentDate;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private BigDecimal totalAmount;
    private Integer totalDurationMinutes;

    /*@OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentService> services = new ArrayList<>();*/
}

