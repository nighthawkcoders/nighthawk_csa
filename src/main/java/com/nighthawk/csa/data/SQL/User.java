package com.nighthawk.csa.data.SQL;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @Size(min=5)
    private String username;

    /*
    @NonNull: Places this in @RequiredArgsConstructor
    @Size(min=2, max=30): Allows names between 2 and 30 characters long.
     */
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Size(min = 2, max = 10, message = "Type (tutor or tutee)")
    private String type;

    @Size(min = 2, max = 400, message = "A bio for the user")
    private String bio;

    @Size(min = 2, max = 30, message = "The User's password")
    private String password;

    /* Initializer used when setting data from an API */
    public User(Integer id, String username, String name, Date dob, String type, String bio, String password) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.dob = dob;
        this.type = type;
        this.bio = bio;
        this.password = password;
    }

    /* A custom getter to return age from dob calculation */
    public int getAge() {
        if (this.dob != null) {
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Period.between(birthDay, LocalDate.now()).getYears(); }
        return -1;
    }

    public long id() {
        return(this.id);
    }
}