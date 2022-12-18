package com.codeseek.footballmanager.dto;

import com.codeseek.footballmanager.model.FootballPlayer;
import com.codeseek.footballmanager.validator.NameConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private String id;

    @NotNull
    @NameConstraint
    private String name;

    @NotNull
    @Min(0)
    @Max(10)
    private Byte commission;

    @NotNull
    private BigDecimal cashAccount;

    private List<FootballPlayer> teamMembers;
}
