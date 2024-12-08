package com.example.game_manager.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull; // Cette importation doit fonctionner si tu as ajouté la dépendance
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
@Entity
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date; 

    @NotNull(message = "Game type is required")
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    private int maxScore; 

    @NotNull(message = "Host ID is required")
    private Long hostId; // Identifiant de l'hôte de la partie

    public Game( LocalDate date,
            @NotNull(message = "Game type is required") GameType gameType, int maxScore,
            @NotNull(message = "Host ID is required") Long hostId) {
        this.date = date;
        this.gameType = gameType;
        this.maxScore = maxScore;
        this.hostId = hostId;
    }
}
