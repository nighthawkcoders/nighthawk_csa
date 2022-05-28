package com.nighthawk.csa.mvc.database.notes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Extends the JpaRepository interface from Spring Data JPA.
-- Java Persistent API (JPA) - Hibernate: map, store, update and retrieve database
-- JpaRepository defines standard CRUD methods
-- Via JPA the developer can retrieve database from relational databases to Java objects and vice versa.
 */
public interface NotesJpaRepository extends JpaRepository<Notes, Long> {
    Notes findByName(String name);

//    Notes findByChapter(String chapter);

    List<Notes> findAllByOrderByNameAsc();

    List<Notes> findByChapterId(long Id);

    // JPA query, findBy does JPA magic with "Name", "Containing", "Or", "Email", "IgnoreCase"
//    List<Chapter> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
    /* Custom JPA query articles, there are articles that show custom SQL as well
       https://springframework.guru/spring-data-jpa-query/
       https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
     */

    // Custom JPA query
//    @Query(
//            value = "SELECT * FROM Person p WHERE p.name LIKE ?1 or p.email LIKE ?2",
//            nativeQuery = true)
//    List<Chapter> findByLikeTermNative(String term);
    /*
        https://www.baeldung.com/spring-data-jpa-query
     */
}