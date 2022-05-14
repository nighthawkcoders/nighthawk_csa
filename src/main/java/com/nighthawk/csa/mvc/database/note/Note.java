package com.nighthawk.csa.mvc.database.note;

import com.nighthawk.csa.mvc.database.person.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    private Person person;

    @NotNull
    @Column(columnDefinition="TEXT")
    private String note;
}
