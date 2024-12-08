package com.example.game_manager.dto;

import com.example.game_manager.entity.GameType;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateGameDTO {
    public CreateGameDTO(GameType multiplayer, int maxScore, long hostId) {
        this.gameType = multiplayer;
        this.maxScore = maxScore;
        this.hostId = hostId;
    }

    @NotNull(message = "Game type is required")
    private GameType gameType;

    private int maxScore;

    @NotNull(message = "Host ID is required")
    private Long hostId;
}
