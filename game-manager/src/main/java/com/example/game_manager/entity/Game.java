package com.example.game_manager.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull; // Cette importation doit fonctionner si tu as ajouté la dépendance
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date is required")
    private LocalDate date; 

    @NotNull(message = "Game type is required")
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    private int maxScore; 

    @NotNull(message = "Host ID is required")
    private Long hostId; // Identifiant de l'hôte de la partie
}
