package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TeamController {

    private final TeamService teamService;


    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("teams")
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.getAllTeams());
    }

    @PostMapping("team")
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.addTeam(team));
    }

    @GetMapping("team/{id}")
    public ResponseEntity<Team> getTeam(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.getTeamById(id));
    }

    @PutMapping("team/{id}")
    public ResponseEntity<Team> updateTeam(@RequestBody TeamDTO teamDTO, @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.updateTeam(teamDTO, id));
    }

    @DeleteMapping("team/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") String id) {
        teamService.deleteTeam(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
