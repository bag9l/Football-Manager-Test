package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.service.FootballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public FootballPlayer addFootballPlayer(@RequestBody FootballPlayer footballPlayer){
        return footballPlayerService.addFootballPlayer(footballPlayer);
    }
}
