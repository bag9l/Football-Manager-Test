package com.codeseek.footballmanager.service.impl;

import com.codeseek.footballmanager.dto.FootballPlayerDTO;
import com.codeseek.footballmanager.exception.EntityNotExistsException;
import com.codeseek.footballmanager.helper.NullPropertyFinder;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import com.codeseek.footballmanager.repository.TeamRepository;
import com.codeseek.footballmanager.service.FootballPlayerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FootballPlayerServiceImpl implements FootballPlayerService {

    private final FootballPlayerRepository footballPlayerRepository;

    private final TeamRepository teamRepository;

    @Autowired
    public FootballPlayerServiceImpl(FootballPlayerRepository footballPlayerRepository, TeamRepository teamRepository) {
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
        return footballPlayerRepository.findById(id).orElseThrow(() ->
                new EntityNotExistsException("Football player with id:" + id + " not found"));
    }

    public FootballPlayer updateFootballPlayer(FootballPlayerDTO footballPlayerDTO, String id) {
        return footballPlayerRepository.findById(id)
                .map(footballPlayer -> {
                    BeanUtils.copyProperties(footballPlayerDTO, footballPlayer, NullPropertyFinder.getNullPropertyNames(footballPlayerDTO));
                    footballPlayer.setTeam(getTeamById(footballPlayerDTO.getTeamId()));
                    return footballPlayerRepository.save(footballPlayer);
                }).orElseThrow(() ->
                        new EntityNotExistsException("Football player is not exists"));
    }

    @Transactional
    public void deleteFootballPlayer(String id) {
        footballPlayerRepository.deleteById(id);
    }


    private Team getTeamById(String id) {
        return teamRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotExistsException("Team with id:" + id + " not found"));
    }
}
