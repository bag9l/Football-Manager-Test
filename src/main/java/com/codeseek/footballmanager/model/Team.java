package com.codeseek.footballmanager.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "`team`")
@Entity
public class Team {

    @Id
    private String id;

    private String name;

    //0-10
    private Byte commission;

    @Column(name = "`cash account`")
    private BigDecimal cashAccount;

    @OneToMany(targetEntity = FootballPlayer.class, fetch = FetchType.EAGER, mappedBy = "team", cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    private List<FootballPlayer> teamMembers;

    public void setTeamMembers(List<FootballPlayer> teamMembers) {
        if (teamMembers != null) {
            teamMembers.forEach(a ->
                    a.setTeam(this));
        }
        this.teamMembers = teamMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return id.equals(team.id)
                && name.equals(team.name)
                && Objects.equals(commission, team.commission)
                && Objects.equals(cashAccount, team.cashAccount)
                && Objects.equals(teamMembers, team.teamMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                commission,
                cashAccount,
                teamMembers
        );
    }
}
