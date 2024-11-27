package com.example.game_manager.dto;

import com.example.game_manager.entity.GameType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameDTO {
    
    private GameType gameType;
    private int maxScore;
}
