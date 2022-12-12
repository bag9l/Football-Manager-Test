package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

    private final TeamService teamService;


    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
}
