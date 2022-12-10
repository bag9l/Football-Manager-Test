package com.codeseek.footballmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`football player`")
@Entity
public class FootballPlayer {

    @Id
    private String id;

    private String firstname;

    private String lastname;

    @Column(name = "`date of birth`")
    private LocalDate dateOfBirth;

    @Column(name = "`date of becoming professional football player`")
    private LocalDate dateOfBecomingProfessionalFootballPlayer;

    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "team_id")
    private Team team;
}
