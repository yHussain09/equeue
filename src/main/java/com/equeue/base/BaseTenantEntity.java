package com.equeue.base;

import com.equeue.entities.Organizer;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Filter;

@MappedSuperclass
@Filter(name = "tenantFilter", condition = "organizer_id = :organizerId")
public abstract class BaseTenantEntity extends BaseAuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false, updatable = false)
    private Organizer organizer;

}
