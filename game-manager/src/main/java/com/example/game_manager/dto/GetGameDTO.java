package com.example.game_manager.dto;

import java.time.LocalDate;

import com.example.game_manager.entity.GameType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class GetGameDTO {

    private LocalDate date;
    private GameType gameType;
    private int maxScore;
}
