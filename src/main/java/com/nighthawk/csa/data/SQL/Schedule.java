package com.nighthawk.csa.data.SQL;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @NonNull
    @Size(min = 2, max = 30, message = "Email (2 to 30 chars)")
    private String email;


    private Integer zip;


    private String help;

    public Schedule(String name, String email, Integer zip, String help) {
        this.name = name;
        this.email = email;
        this.zip = zip;
        this.help = help;
    }


    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return email;
    }

    public String getHelp(){
        return help;
    }

    public Integer getZip(){
        return zip;
    }




}
