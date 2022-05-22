package com.nighthawk.csa.mvc.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String file;

    private String type;

    private Long size;
}