package com.nighthawk.csa.mvc.database.note;

import com.nighthawk.csa.mvc.database.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteJpaRepository extends JpaRepository<Note, Long> {
}

