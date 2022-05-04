package com.nighthawk.csa;

import com.nighthawk.csa.model.SqlRepository;
import com.nighthawk.csa.model.role.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // CommandLineRunner provides options to up preliminary data
    @Bean
    CommandLineRunner run(SqlRepository sql) { // testing the database with role adds etc
        return args -> {
            // make sure every record added has Default encrypted password and ROLE_STUDENT
            sql.defaults("123querty", "ROLE_STUDENT");

            // add privileged roles
            sql.addRoleToPerson("jmort1021@gmail.com", "ROLE_TEACHER");
            sql.addRoleToPerson("jmort1021@gmail.com", "ROLE_ADMIN");

            // output to console
            System.out.println(sql.listAll());
            System.out.println(sql.listAllRoles());
        };
    }

}
