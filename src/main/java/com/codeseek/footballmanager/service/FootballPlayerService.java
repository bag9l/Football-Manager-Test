package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.dto.FootballPlayerDTO;
import com.codeseek.footballmanager.model.FootballPlayer;

import java.util.List;

public interface FootballPlayerService {
    List<FootballPlayer> getAllFootballPlayers();
    FootballPlayer addFootballPlayer(FootballPlayerDTO dto);
    FootballPlayer getFootballPlayerById(String id);
    FootballPlayer updateFootballPlayer(FootballPlayerDTO footballPlayerDTO, String id);
    void deleteFootballPlayer(String id);
}
