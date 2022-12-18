package com.codeseek.footballmanager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransferFootballPlayerDTO {

    @NotNull
    private String footballPlayerId;

    @NotNull
    private String buyingTeamId;
}
