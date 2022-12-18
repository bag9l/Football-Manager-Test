package com.codeseek.footballmanager.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransferFootballPlayerDTO {
    private String footballPlayerId;
    private String buyingTeamId;
}
