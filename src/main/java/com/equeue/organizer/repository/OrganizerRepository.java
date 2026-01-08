package com.equeue.organizer.repository;

import com.equeue.organizer.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    Optional<Organizer> findByName(String name);
}
