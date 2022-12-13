package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.dto.FootballPlayerDTO;
import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.service.FootballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("footballPlayers")
public class FootballPlayerController {

    private final FootballPlayerService footballPlayerService;

    @Autowired
    public FootballPlayerController(FootballPlayerService footballPlayerService) {
        this.footballPlayerService = footballPlayerService;
    }

    @GetMapping()
    public ResponseEntity<List<FootballPlayer>> getFootballPlayers() {
        return ResponseEntity.status(HttpStatus.OK).body(
                        footballPlayerService.getAllFootballPlayers());
    }

    @PostMapping("create")
    public ResponseEntity<FootballPlayer> addFootballPlayer(@RequestBody FootballPlayerDTO dto) {
        return  ResponseEntity.status(HttpStatus.OK).body(
                footballPlayerService.addFootballPlayer(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<FootballPlayer> getTeam(@PathVariable("id") String id) {
        return  ResponseEntity.status(HttpStatus.OK).body(
                footballPlayerService.getFootballPlayerById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<FootballPlayer> updateFootballPlayer(@RequestBody FootballPlayerDTO footballPlayerDTO,
                                                               @PathVariable("id") String id) {
        return  ResponseEntity.status(HttpStatus.OK).body(
                footballPlayerService.updateFootballPlayer(footballPlayerDTO, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFootballPlayer(@PathVariable("id") String id) {
        footballPlayerService.deleteFootballPlayer(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
