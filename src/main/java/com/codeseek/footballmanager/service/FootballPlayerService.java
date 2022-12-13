package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballPlayerService {

    private final FootballPlayerRepository footballPlayerRepository;

    @Autowired
    public FootballPlayerService(FootballPlayerRepository footballPlayerRepository) {
        this.footballPlayerRepository = footballPlayerRepository;
    }

    public List<FootballPlayer> getAllFootballPlayers(){
        return footballPlayerRepository.findAll();
    }


    public FootballPlayer addFootballPlayer(FootballPlayer footballPlayer) {
        return footballPlayerRepository.save(footballPlayer);
    }
}
