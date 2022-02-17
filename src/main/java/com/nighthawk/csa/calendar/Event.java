package com.nighthawk.csa.calendar;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Size(min=1,max=100)
    private String event;

    public Event(Long id, String event) {
        this.id = id;
        this.event = event;
    }
}
