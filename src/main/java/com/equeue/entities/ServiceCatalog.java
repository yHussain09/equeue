package com.equeue.entities;

import com.equeue.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "services")
public class ServiceCatalog extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @Column(nullable = false)
    private String name;

    private String description;

    private BigDecimal price;
    private Integer durationMinutes;

    private Boolean isActive = true;

    @ManyToOne
    private Event event;
}
