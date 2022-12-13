package com.codeseek.footballmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private String id;

    private String name;

    private Byte commission;

    private BigDecimal cashAccount;
}
