package com.codeseek.footballmanager.dto;

import com.codeseek.footballmanager.validator.NameConstraint;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NameConstraint
    private String firstname;

    @NotNull
    @NameConstraint
    private String lastname;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate dateOfBecomingProfessionalFootballPlayer;

    private String teamId;
}
