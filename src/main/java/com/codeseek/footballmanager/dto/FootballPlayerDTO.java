package com.codeseek.footballmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FootballPlayerDTO {

    private String id;

    private String firstname;

    private String lastname;

    private LocalDate dateOfBirth;

    private Integer age;

    private LocalDate dateOfBecomingProfessionalFootballPlayer;

    private String teamId;
}
