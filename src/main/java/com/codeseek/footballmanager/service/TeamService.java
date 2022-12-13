package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.dto.TransferFootballPlayerDTO;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import com.codeseek.footballmanager.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    private final FootballPlayerRepository footballPlayerRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, FootballPlayerRepository footballPlayerRepository) {
        this.teamRepository = teamRepository;
        this.footballPlayerRepository = footballPlayerRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team getTeamById(String id) {
        return teamRepository.findById(id).orElseThrow(()->
                new IllegalStateException("Team with id:" + id + " not found"));
    }

    public Team updateTeam(TeamDTO teamDTO, String id) {
        return teamRepository.findById(id)
                .map(team -> {
                    BeanUtils.copyProperties(teamDTO, team);
                    team.setId(id);
                    return teamRepository.save(team);
                }).orElseThrow(()->
                        new IllegalStateException("Team is not exists"));
    }

    public void deleteTeam(String id) {
        teamRepository.deleteById(id);
    }

    @Transactional
    public void buyFootballPlayer(TransferFootballPlayerDTO transferFootballPlayerDTO) {
        FootballPlayer footballPlayer = footballPlayerRepository.findById(
                transferFootballPlayerDTO.getFootballPlayerId()).orElseThrow(()->
                new IllegalStateException("Football player not found"));

        Team buyingTeam = teamRepository.findById(transferFootballPlayerDTO.getBuyingTeamId()).orElseThrow(()->
                new IllegalStateException("Team not found"));
        Team salesTeam = teamRepository.findById(transferFootballPlayerDTO.getSalesTeamId()).orElseThrow(()->
                new IllegalStateException("Team not found"));

        if(footballPlayer.getTeam().equals(buyingTeam)){
            throw new IllegalStateException("This football player is already on your team");
        }

        double footballPlayerPrice = footballPlayer.getMonthsOfExperience() * 100000 / footballPlayer.getAge();
        double teamCommission = salesTeam.getCommission()/100 * footballPlayerPrice;
        double transferPrice = footballPlayerPrice + teamCommission;

        if(buyingTeam.getCashAccount().doubleValue()>transferPrice) {
            buyingTeam.setCashAccount(new BigDecimal(buyingTeam.getCashAccount().doubleValue() - transferPrice));
            salesTeam.setCashAccount(new BigDecimal(salesTeam.getCashAccount().doubleValue()+transferPrice));
            footballPlayer.setTeam(buyingTeam);
            teamRepository.saveAll(List.of(buyingTeam, salesTeam));
            footballPlayerRepository.save(footballPlayer);
        }else {
            throw new IllegalStateException("Insufficient funds to purchase a player");
        }
    }
}
