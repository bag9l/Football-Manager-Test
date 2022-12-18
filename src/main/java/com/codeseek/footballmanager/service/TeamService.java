package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.dto.TransferFootballPlayerDTO;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import com.codeseek.footballmanager.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
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

    public Team addTeam(TeamDTO teamDTO) {
        Team team = new Team();
        BeanUtils.copyProperties(teamDTO, team, "id");
        return teamRepository.save(team);
    }

    public Team getTeamById(String id) {
        return teamRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Team with id:" + id + " not found"));
    }

    public Team updateTeam(TeamDTO teamDTO, String id) {
        return teamRepository.findById(id)
                .map(team -> {
                    BeanUtils.copyProperties(teamDTO, team);
                    team.setId(id);
                    return teamRepository.save(team);
                }).orElseThrow(() ->
                        new IllegalStateException("Team is not exists"));
    }

    public void deleteTeam(String id) {
        teamRepository.deleteById(id);
    }

    @Transactional
    public void buyFootballPlayer(TransferFootballPlayerDTO transferFootballPlayerDTO) {
        FootballPlayer footballPlayer = footballPlayerRepository.findById(
                transferFootballPlayerDTO.getFootballPlayerId()).orElseThrow(() ->
                new IllegalStateException("Football player not found"));

        Team buyingTeam = teamRepository.findById(transferFootballPlayerDTO.getBuyingTeamId()).orElseThrow(() ->
                new IllegalStateException("Team not found"));
        Team salesTeam = footballPlayer.getTeam();

        if (salesTeam == null) {
            throw new IllegalStateException("This football player does not belong to the team");
        }

        if (footballPlayer.getTeam().equals(buyingTeam)) {
            throw new IllegalStateException("This football player is already on your team");
        }

        double footballPlayerPrice = footballPlayer.getMonthsOfExperience() * 100000 / footballPlayer.getAge();
        double teamCommission = salesTeam.getCommission() / 100 * footballPlayerPrice;
        double transferPrice = footballPlayerPrice + teamCommission;

        log.info("number of months experience: " + footballPlayer.getMonthsOfExperience());

        if (buyingTeam.getCashAccount().doubleValue() > transferPrice) {
            buyingTeam.setCashAccount(new BigDecimal(buyingTeam.getCashAccount().doubleValue() - transferPrice));
            salesTeam.setCashAccount(new BigDecimal(salesTeam.getCashAccount().doubleValue() + transferPrice));
            footballPlayer.setTeam(buyingTeam);
            teamRepository.saveAll(List.of(buyingTeam, salesTeam));
            footballPlayerRepository.save(footballPlayer);
        } else {
            throw new IllegalStateException("Insufficient funds to purchase a player");
        }
    }
}
