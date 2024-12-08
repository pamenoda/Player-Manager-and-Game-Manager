package com.example.game_manager.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long gameId;   // ID of the game
    private Long playerId; // ID of the player
    private int score;     // Score achieved by the player
    
    private boolean isWin; // Whether the player won the game

    public Participation(Long gameId, Long playerId, int score, boolean isWin) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.score = score;
        this.isWin = isWin;
    }
    
}
