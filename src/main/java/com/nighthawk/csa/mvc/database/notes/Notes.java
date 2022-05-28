package com.nighthawk.csa.mvc.database.notes;

import com.nighthawk.csa.mvc.database.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;

/*
Chapter is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notes {
    // automatic unique identifier for Chapter record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @NotEmpty
    @Size(min = 1, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @NonNull
    @NotEmpty
    @Size(min = 1, max = 30, message = "Name (2 to 30 chars)")
    private String chapter;

    // email, password, roles are key to login and authentication
//    @Size(min=5)
//    @Column(unique=true)
//    @Email
//    private String email;

    @NotEmpty
    private String link;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    // @NonNull: Places this in @RequiredArgsConstructor


//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date dob;

    // Initializer used when setting database from an API
    public Notes(String name, String link, String chapter) {
//        this.email = email;
        this.name = name;
        this.link = link;
        this.chapter = chapter;
//        this.dob = dob;
    }

    // A custom getter to return age from dob calculation
//    public int getAge() {
//        if (this.dob != null) {
//            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            return Period.between(birthDay, LocalDate.now()).getYears(); }
//        return -1;
//    }

}