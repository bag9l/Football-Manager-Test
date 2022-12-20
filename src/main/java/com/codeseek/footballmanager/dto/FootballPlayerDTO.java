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

    @NotNull(message = "must not be null")
    @NameConstraint
    private String firstname;

    @NotNull(message = "must not be null")
    @NameConstraint
    private String lastname;

    @NotNull(message = "must not be null")
    private LocalDate dateOfBirth;

    @NotNull(message = "must not be null")
    private LocalDate dateOfBecomingProfessionalFootballPlayer;

    private String teamId;
}
