package com.codeseek.footballmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "`football_player`")
@Entity
public class FootballPlayer {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String firstname;
    private String lastname;

    @Column(name = "`date of birth`")
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    @Transient
    private Integer monthsOfExperience;

    @Column(name = "`date of becoming professional football player`")
    private LocalDate dateOfBecomingProfessionalFootballPlayer;

    @JsonIgnore
    @ManyToOne(cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JoinColumn(name = "team_id")
    private Team team;

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public Integer getMonthsOfExperience() {
        return Math.toIntExact(Period.between(this.dateOfBecomingProfessionalFootballPlayer, LocalDate.now()).toTotalMonths());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FootballPlayer)) return false;
        FootballPlayer that = (FootballPlayer) o;
        return id.equals(that.id)
                && firstname.equals(that.firstname)
                && lastname.equals(that.lastname)
                && dateOfBirth.equals(that.dateOfBirth)
                && dateOfBecomingProfessionalFootballPlayer.equals(that.dateOfBecomingProfessionalFootballPlayer)
                && Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                firstname,
                lastname,
                dateOfBirth,
                dateOfBecomingProfessionalFootballPlayer,
                team
        );
    }
}
