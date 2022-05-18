package com.nighthawk.csa.mvc.database.scrum;

import com.nighthawk.csa.mvc.database.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Extends the JpaRepository interface from Spring Data JPA.
-- Java Persistent API (JPA) - Hibernate: map, store, update and retrieve database
-- JpaRepository defines standard CRUD methods
-- Via JPA the developer can retrieve database from relational databases to Java objects and vice versa.
 */
public interface ScrumJpaRepository extends JpaRepository<Scrum, Long> {
    List<Scrum> findAllByOrderByNameAsc();
}