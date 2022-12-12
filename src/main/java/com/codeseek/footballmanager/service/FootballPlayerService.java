package com.codeseek.footballmanager.service;

import com.codeseek.footballmanager.repository.FootballPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FootballPlayerService {

    private final FootballPlayerRepository footballPlayerRepository;

    @Autowired
    public FootballPlayerService(FootballPlayerRepository footballPlayerRepository) {
        this.footballPlayerRepository = footballPlayerRepository;
    }
}
