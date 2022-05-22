package com.nighthawk.csa.mvc.database.person;

import com.nighthawk.csa.mvc.database.note.Note;
import com.nighthawk.csa.mvc.database.role.Role;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // username, password, and role handling
    @NotEmpty
    @Column(unique=true)
    private String username; // username should be NonEmpty and unique

    @NotEmpty
    private String password; // password should be NonEmpty (introduce hashing requirements later? UPDATE: apparently this uses bcrypt)

    private Date dob;

    @NotEmpty
    @Column(unique=true)
    private String name; // name should also be NonEmpty and unique...probably

    @ManyToMany(fetch = EAGER) // not sure if we should use this or lazy, going to use eager for now
    private Collection<Role> roles = new ArrayList<>();

    // Kinda jank hack for DOB to age
    public Integer getAge(Date dob){
        if (dob == null) return 0;
        Calendar birth = Calendar.getInstance(Locale.US);
        Calendar current = Calendar.getInstance(Locale.US);
        birth.setTime(dob);
        Period age = Period.between(
                LocalDate.ofInstant(birth.toInstant(), birth.getTimeZone().toZoneId()),
                LocalDate.ofInstant(current.toInstant(), current.getTimeZone().toZoneId()));

        return age.getYears();
    }

    // Initializer used when setting database from an API
    public Person(String username, String password, String name, Role role, Date dob) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.roles.add(role);
        this.dob = dob;

    }
}