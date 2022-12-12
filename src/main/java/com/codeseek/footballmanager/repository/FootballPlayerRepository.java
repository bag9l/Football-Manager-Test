package com.codeseek.footballmanager.repository;

import com.codeseek.footballmanager.model.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballPlayerRepository extends JpaRepository<FootballPlayer, String> {
}
