package com.equeue.organizer.entity;

import com.equeue.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "organizers") // Business / Company
@Getter
@Setter
public class Organizer extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private boolean active = true;

}
