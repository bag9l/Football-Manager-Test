package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.dto.FootballPlayerDTO;
import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team getTeamById(String id) {
        return teamRepository.findById(id).orElseThrow(()->
                new IllegalStateException("Team with id:" + id + " not found"));
    }

    public Team updateTeam(TeamDTO teamDTO, String id) {
        return teamRepository.findById(id)
                .map(team -> {
                    BeanUtils.copyProperties(teamDTO, team);
                    team.setId(id);
                    return teamRepository.save(team);
                }).orElseThrow(()->
                        new IllegalStateException("Team is not exists"));
    }
}
