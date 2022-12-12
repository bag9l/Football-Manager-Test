package com.codeseek.footballmanager.repository;

import com.codeseek.footballmanager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, String> {
}
