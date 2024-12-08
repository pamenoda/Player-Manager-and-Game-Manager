package com.example.game_manager.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class CreateParticipationDTO {


    private Long gameId;   // ID of the game

    @NotNull(message = "player of id required")
    private Long playerId; // ID of the player
    @NotNull(message = "score required")
    private int score;     // Score achieved by the player
    
    private boolean isWin; // Whether the player won the game

    
    

}
