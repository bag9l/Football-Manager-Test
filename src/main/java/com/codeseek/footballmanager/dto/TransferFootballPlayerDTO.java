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

    @NotNull(message = "must not be null")
    private String footballPlayerId;

    @NotNull(message = "must not be null")
    private String buyingTeamId;
}
