package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;


    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("teams")
    public List<Team> getTeams(){
        return teamService.getAllTeams();
    }

    @PostMapping("team")
    public Team addTeam(@RequestBody Team team){
        return teamService.addTeam(team);
    }
}
