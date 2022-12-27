package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.dto.TeamDTO;
import com.codeseek.footballmanager.dto.TransferFootballPlayerDTO;
import com.codeseek.footballmanager.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();
    Team addTeam(TeamDTO teamDTO);
    Team getTeamById(String id);
    Team updateTeam(TeamDTO teamDTO, String id);
    void deleteTeam(String id);
    void buyFootballPlayer(TransferFootballPlayerDTO transferFootballPlayerDTO);

}
