package com.nighthawk.csa.mvc.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {
    // The run() method of the MyRunner will be executed after the application starts
    @Bean
    CommandLineRunner run(ModelRepository modelRepository) {
        return args -> {
            // make sure every record added has a Default encrypted password and ROLE_STUDENT
            modelRepository.defaults("123querty", "ROLE_STUDENT");

            // make sure privileged roles exist for Teacher
            modelRepository.addRoleToPerson("jmort1021@gmail.com", "ROLE_TEACHER");
            modelRepository.addRoleToPerson("jmort1021@gmail.com", "ROLE_ADMIN");

            // Validate/test by performing output to console
            System.out.println(modelRepository.listAll());
            System.out.println(modelRepository.listAllRoles());
        };
    }
}
