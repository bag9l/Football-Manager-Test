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

    @NotNull(message = "must not be null")
    @NameConstraint
    private String name;

    @NotNull(message = "must not be null")
    @Min(value = 0, message = "must be bigger or equals to 0")
    @Max(value = 10, message = "must be less or equals to 10")
    private Byte commission;

    @NotNull(message = "must not be null")
    private BigDecimal cashAccount;

    private List<FootballPlayer> teamMembers;
}
