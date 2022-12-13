package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.dto.FootballPlayerDTO;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.service.FootballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FootballPlayerController {

    private final FootballPlayerService footballPlayerService;

    @Autowired
    public FootballPlayerController(FootballPlayerService footballPlayerService) {
        this.footballPlayerService = footballPlayerService;
    }

    @GetMapping("players")
    public List<FootballPlayer> getFootballPlayers(){
        return footballPlayerService.getAllFootballPlayers();
    }

    @PostMapping("player")
    public FootballPlayer addFootballPlayer(@RequestBody FootballPlayerDTO dto){
        return footballPlayerService.addFootballPlayer(dto);
    }

    @GetMapping("player/{id}")
    public FootballPlayer getTeam(@PathVariable("id") String id){
        return footballPlayerService.getFootballPlayerById(id);
    }
}
