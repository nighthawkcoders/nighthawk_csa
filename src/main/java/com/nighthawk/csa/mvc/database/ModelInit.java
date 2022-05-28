package com.nighthawk.csa.mvc.database;

import com.nighthawk.csa.mvc.database.person.Person;
import com.nighthawk.csa.mvc.database.role.Role;
import com.nighthawk.csa.mvc.database.role.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {
    // Inject repositories

    @Autowired RoleJpaRepository roleJpaRepository;
    @Autowired ModelRepository modelRepository;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {
            // Fail safe data validations

            // make sure Role database is populated with defaults
            String[] roles = {"ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN"};
            for (String role : roles) {
                if (roleJpaRepository.findByName(role) == null)
                    roleJpaRepository.save(new Role(null, role));
            }

            // make sure every record added has a Default encrypted password and ROLE_STUDENT
            modelRepository.defaults("123qwerty", "ROLE_STUDENT");

            // make sure privileged roles exist for Teacher
            modelRepository.addRoleToPerson("TSwanson@powayusd.com", "ROLE_TEACHER");
            modelRepository.addRoleToPerson("TSwanson@powayusd.com", "ROLE_ADMIN");

            Person person = modelRepository.getByEmail("TSwanson@powayusd.com");
            if(person == null) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Person teacher = new Person("TSwanson@powayusd.com", "apush", "Tom Swanson", formatter.parse("1998-12-12"), modelRepository.findRole("ROLE_ADMIN"));
                modelRepository.save(teacher);
            }

        };
    }
}
