package com.nighthawk.csa.mvc.database.scrum;

// https://projectlombok.org/features/all

import com.nighthawk.csa.mvc.database.person.Person;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

/*
Family Information Class
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Scrum {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    /* In JPA the main difference between a OneToOne and a ManyToOne relationship ...
     a ManyToOne always contains a foreign key from the source object's table
     a OneToOne relationship the foreign key may either be in the source object's table or the target object's table.
     */
    @OneToOne
    @JoinColumn(name = "primary_id")    //foreign key is stored in table
    private Person primary;

    @OneToOne
    @JoinColumn(name = "member1_id")
    private Person member1;

    @OneToOne
    @JoinColumn(name = "member2_id")
    private Person member2;

    @OneToOne
    @JoinColumn(name = "member3_id")
    private Person member3;

    @OneToOne
    @JoinColumn(name = "member4_id")
    public Person member4;
}
