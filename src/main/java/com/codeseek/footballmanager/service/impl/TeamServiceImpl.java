package com.codeseek.footballmanager.service.impl;

import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.dto.TransferFootballPlayerDTO;
import com.codeseek.footballmanager.exception.EntityNotExistsException;
import com.codeseek.footballmanager.exception.TransferException;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import com.codeseek.footballmanager.repository.TeamRepository;
import com.codeseek.footballmanager.service.TeamService;
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
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final FootballPlayerRepository footballPlayerRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, FootballPlayerRepository footballPlayerRepository) {
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
    public void deleteTeam(String id) {
        Team team = teamRepository.findById(id).orElseThrow(() ->
                new EntityNotExistsException("Team is not exists"));
        team.getTeamMembers().forEach((member) -> {
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

        double transferPrice = calculateTransferPrice(footballPlayer.getMonthsOfExperience(),
                footballPlayer.getAge(),
                salesTeam.getCommission());

        transferFootballPlayer(footballPlayer, buyingTeam, salesTeam, transferPrice);
    }

    private void transferFootballPlayer(FootballPlayer footballPlayer, Team buyingTeam, Team salesTeam, double transferPrice) {
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

    private double calculateTransferPrice(int monthsOfExperience, int footballPlayerAge, double teamCommission) {
        double footballPlayerPrice = monthsOfExperience * 100_000 / footballPlayerAge;
        double commission = teamCommission / 100 * footballPlayerPrice;
        return footballPlayerPrice + commission;
    }
}
