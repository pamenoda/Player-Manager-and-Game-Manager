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
       
        // create a body 
        Map<String, Object> updatePayload = new HashMap<>();
        updatePayload.put("scoreToAdd", scoreToAdd);
    

        // send body by post with the endpoint of player management
        webClient.post()
                .uri("/players/" + playerId + "/update-stats")
                .bodyValue(updatePayload)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
    
}
