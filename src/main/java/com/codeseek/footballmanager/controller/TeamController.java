package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.dto.TransferFootballPlayerDTO;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/teams")
public class TeamController {

    private final TeamService teamService;


    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping()
    public ResponseEntity<List<Team>> getTeams() {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.getAllTeams());
    }

    @PostMapping("create")
    public ResponseEntity<Team> addTeam(@RequestBody @Valid TeamDTO team) {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.addTeam(team));
    }

    @GetMapping("{id}")
    public ResponseEntity<Team> getTeam(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.getTeamById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Team> updateTeam(@RequestBody @Valid TeamDTO teamDTO, @PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                teamService.updateTeam(teamDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") String id) {
        teamService.deleteTeam(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("{id}/buyFootballPlayer")
    public ResponseEntity<Void> buyFootballPlayer(@RequestBody @Valid TransferFootballPlayerDTO transferFootballPlayerDTO, @PathVariable("id") String id) {
        transferFootballPlayerDTO.setBuyingTeamId(id);
        teamService.buyFootballPlayer(transferFootballPlayerDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
