package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.dto.TransferFootballPlayerDTO;
import com.codeseek.footballmanager.exception.EntityNotExistsException;
import com.codeseek.footballmanager.exception.TransferException;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import com.codeseek.footballmanager.repository.TeamRepository;
import jakarta.persistence.PreRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.codeseek.footballmanager.helper.NullPropertyFinder.getNullPropertyNames;

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
        BeanUtils.copyProperties(teamDTO, team, getNullPropertyNames(teamDTO));
        return teamRepository.save(team);
    }

    public Team getTeamById(String id) {
        return teamRepository.findById(id).orElseThrow(() ->
                new EntityNotExistsException("Team with id:" + id + " not found"));
    }

    public Team updateTeam(TeamDTO teamDTO, String id) {
        return teamRepository.findById(id)
                .map(team -> {
                    BeanUtils.copyProperties(teamDTO, team, getNullPropertyNames(teamDTO));
                    return teamRepository.save(team);
                }).orElseThrow(() ->
                        new EntityNotExistsException("Team is not exists"));
    }

    @PreRemove
    public void deleteTeam(String id){
        Team team = teamRepository.findById(id).orElseThrow(()->
                new EntityNotExistsException("Team is not exists"));
        team.getTeamMembers().forEach((member)->{
            member.setTeam(null);
        });

        teamRepository.save(team);

        teamRepository.deleteById(id);
    }

    @Transactional
    public void buyFootballPlayer(TransferFootballPlayerDTO transferFootballPlayerDTO) {
        FootballPlayer footballPlayer = footballPlayerRepository.findById(
                transferFootballPlayerDTO.getFootballPlayerId()).orElseThrow(() ->
                new EntityNotExistsException("Football player not found"));

        Team buyingTeam = teamRepository.findById(transferFootballPlayerDTO.getBuyingTeamId()).orElseThrow(() ->
                new EntityNotExistsException("Team not found"));
        Team salesTeam = footballPlayer.getTeam();

        if (salesTeam == null) {
            throw new TransferException("This football player does not belong to the team");
        }

        if (footballPlayer.getTeam().equals(buyingTeam)) {
            throw new TransferException("This football player is already on your team");
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
            throw new TransferException("Insufficient funds to purchase a player");
        }
    }
}
