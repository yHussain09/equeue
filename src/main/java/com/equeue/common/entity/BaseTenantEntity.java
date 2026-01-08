package com.equeue.common.entity;

import com.equeue.base.BaseAuditEntity;
import com.equeue.organizer.entity.Organizer;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@FilterDef(
        name = "tenantFilter",
        parameters = @ParamDef(name = "organizerId", type = Long.class)
)
@Filter(name = "tenantFilter", condition = "organizer_id = :organizerId")
public abstract class BaseTenantEntity extends BaseAuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false, updatable = false)
    private Organizer organizer;

}
