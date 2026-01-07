package com.equeue.interceptors;

import com.equeue.config.TenantContext;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class HibernateTenantInterceptor {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("tenantFilter")
                .setParameter("organizerId", TenantContext.getOrganizerId());
    }
}

