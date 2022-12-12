package com.codeseek.footballmanager.controller;

import com.codeseek.footballmanager.service.FootballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FootballPlayerController {

    private final FootballPlayerService footballPlayerService;

    @Autowired
    public FootballPlayerController(FootballPlayerService footballPlayerService) {
        this.footballPlayerService = footballPlayerService;
    }
}
