package com.equeue.common.entity;

public class TenantContext {

    private static final ThreadLocal<Long> ORGANIZER_ID = new ThreadLocal<>();

    public static void setOrganizerId(Long organizerId) {
        ORGANIZER_ID.set(organizerId);
    }

    public static Long getOrganizerId() {
        return ORGANIZER_ID.get();
    }

    public static void clear() {
        ORGANIZER_ID.remove();
    }
}

