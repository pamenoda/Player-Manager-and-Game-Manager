package com.example.game_manager.dto;

import com.example.game_manager.entity.GameType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateGameDTO {
    
   
    private GameType gameType;
    private int maxScore;
}
