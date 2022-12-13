package com.codeseek.footballmanager.config;

import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.model.Team;
import com.codeseek.footballmanager.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

//@Configuration
public class TeamConfig {

//    @Bean
//    CommandLineRunner commandLineRunner(
//            TeamRepository repository){
//        return args ->{
//            FootballPlayer ladic = new FootballPlayer(
//                    "Dražen",
//                    "Ladić",
//                    LocalDate.of(1991, Month.MARCH, 10),
//                    LocalDate.of(2010, Month.APRIL, 13)
//            );
//            FootballPlayer mandzukic = new FootballPlayer(
//                    "Mario",
//                    "Mandžukić",
//                    LocalDate.of(1995, Month.FEBRUARY, 1),
//                    LocalDate.of(2017, Month.JANUARY, 24)
//            );
//
//            Team croatia = new Team(
//                    "Croatia",
//                    (byte) 10,
//                    new BigDecimal(800000000.0),
//                    List.of(ladic, mandzukic));
//
//            repository.save(croatia);
//        };
//    }
}
