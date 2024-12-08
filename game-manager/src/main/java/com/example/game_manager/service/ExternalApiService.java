package com.example.game_manager.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.Getter;


@Service
@Getter
public class ExternalApiService implements IExternalApiService {

    private final WebClient webClient;

    public ExternalApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public boolean checkIfPlayerExists(Long playerId) {
        try {
            webClient.get()
                     .uri("/players/{id}", playerId)
                     .retrieve()
                     .toBodilessEntity()
                     .block();
            return true;
        } catch (WebClientResponseException.NotFound e) {
            return false;
        }
    }

    @Override
    public void updatePlayerStats(Long playerId, int scoreToAdd) {
        // Créer un payload JSON contenant uniquement les informations nécessaires
        Map<String, Object> updatePayload = new HashMap<>();
        updatePayload.put("scoreToAdd", scoreToAdd);
    
        // Appeler directement l'endpoint de mise à jour des statistiques
        webClient.post()
                .uri("/players/" + playerId + "/update-stats")
                .bodyValue(updatePayload)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
    
}
