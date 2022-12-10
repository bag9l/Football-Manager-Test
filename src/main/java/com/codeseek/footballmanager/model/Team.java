package com.codeseek.footballmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`team`")
@Entity
public class Team {

    @Id
    private String id;

    private String name;

    //0-10
    private byte commission;

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
}
