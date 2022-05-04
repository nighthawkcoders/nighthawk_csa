package com.nighthawk.csa;

import com.nighthawk.csa.authenticate.model.person.PersonSqlRepository;
import com.nighthawk.csa.authenticate.model.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
/*
    @Bean
    CommandLineRunner run(PersonSqlRepository sql) { // testing the database with role adds etc
        return args -> {
            sql.saveRole(new Role(null, "ROLE_USER"));
            sql.saveRole(new Role(null, "ROLE_MANAGER"));
            sql.saveRole(new Role(null, "ROLE_ADMIN"));

            sql.addRoleToPerson("shay@gmail.com", "ROLE_USER");
            sql.addRoleToPerson("johntravolta@example.com", "ROLE_MANAGER");
        };
    }

 */

}
