package com.equeue.entities;

import com.equeue.base.BaseAuditEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "organizers") // Business / Company
public class Organizer extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String name;

}
