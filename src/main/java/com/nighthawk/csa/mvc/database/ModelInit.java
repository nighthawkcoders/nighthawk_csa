package com.nighthawk.csa.mvc.database;

import com.nighthawk.csa.mvc.database.note.NoteJpaRepository;
import com.nighthawk.csa.mvc.database.Chapter1.Person;
import com.nighthawk.csa.mvc.database.role.Role;
import com.nighthawk.csa.mvc.database.role.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {
    // Inject repositories
    @Autowired NoteJpaRepository noteJpaRepository;
    @Autowired RoleJpaRepository roleJpaRepository;
    @Autowired ModelRepository modelRepository;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {
            // Fail safe data validations

            // make sure Role database is populated with defaults
            String[] roles = {"ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN", "ROLE_TESTER"};
            for (String role : roles) {
                if (roleJpaRepository.findByName(role) == null)
                    roleJpaRepository.save(new Role(null, role));
            }

            // make sure every record added has a Default encrypted password and ROLE_STUDENT
            modelRepository.defaults("123querty", "ROLE_STUDENT");

            // make sure privileged roles exist for Teacher
            modelRepository.addRoleToPerson("jmort1021@gmail.com", "ROLE_TEACHER");
            modelRepository.addRoleToPerson("jmort1021@gmail.com", "ROLE_ADMIN");

            Person person = modelRepository.getByEmail("jmort1021@gmail.com");

        };
    }
}
