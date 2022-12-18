package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.dto.FootballPlayerDTO;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import com.codeseek.footballmanager.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballPlayerService {

    private final FootballPlayerRepository footballPlayerRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public FootballPlayerService(FootballPlayerRepository footballPlayerRepository, TeamRepository teamRepository) {
        this.footballPlayerRepository = footballPlayerRepository;
        this.teamRepository = teamRepository;
    }

    public List<FootballPlayer> getAllFootballPlayers() {
        return footballPlayerRepository.findAll();
    }


    public FootballPlayer addFootballPlayer(FootballPlayerDTO dto) {
        FootballPlayer footballPlayer = new FootballPlayer();
        BeanUtils.copyProperties(dto, footballPlayer);

        footballPlayer.setTeam(getTeamById(dto.getTeamId()));

        return footballPlayerRepository.save(footballPlayer);
    }

    public FootballPlayer getFootballPlayerById(String id) {
        return footballPlayerRepository.findById(id).orElseThrow(()->
                new IllegalStateException("Football player with id:" + id + " not found"));
    }

    public FootballPlayer updateFootballPlayer(FootballPlayerDTO footballPlayerDTO, String id) {
        return footballPlayerRepository.findById(id)
                .map(footballPlayer ->{
                    BeanUtils.copyProperties(footballPlayerDTO, footballPlayer, "id");
                    footballPlayer.setTeam(getTeamById(footballPlayerDTO.getTeamId()));
                    return footballPlayerRepository.save(footballPlayer);
                }).orElseThrow(()->
                        new IllegalStateException("Football player is not exists"));
    }

    private Team getTeamById(String id){
    return  teamRepository.findById(id)
        .orElseThrow(() ->
                new IllegalStateException("Team with id:" + id + " not found"));
    }

    public void deleteFootballPlayer(String id) {
        footballPlayerRepository.deleteById(id);
    }
}
