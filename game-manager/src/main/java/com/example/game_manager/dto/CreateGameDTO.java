package com.example.game_manager.dto;

import java.time.LocalDate;

import com.example.game_manager.entity.GameType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateGameDTO {
    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Game type is required")
    private GameType gameType;

    private int maxScore;

    @NotNull(message = "Host ID is required")
    private Long hostId;
}
