package com.example.game_manager.service;

import com.example.game_manager.dao.IParticipationDAO;
import com.example.game_manager.dto.CreateParticipationDTO;
import com.example.game_manager.entity.Participation;
import org.springframework.stereotype.Service;

@Service
public class ParticipationService implements IParticipationService {

    private final IParticipationDAO participationDAO;
    private final IGameService gameService;
    private final IExternalApiService externalApiService;

    public ParticipationService(IParticipationDAO participationDAO, IGameService gameService, IExternalApiService externalApiService) {
        this.participationDAO = participationDAO;
        this.gameService = gameService;
        this.externalApiService = externalApiService;
    }

    @Override
    public Participation saveParticipation(CreateParticipationDTO participationDTO) {
        gameService.validateGameExists(participationDTO.getGameId());

        if (!externalApiService.checkIfPlayerExists(participationDTO.getPlayerId())) {
            throw new IllegalArgumentException("Player with ID " + participationDTO.getPlayerId() + " does not exist.");
        }
        // Check if the player has already participated in the game
        if (participationDAO.existsByGameIdAndPlayerId(participationDTO.getGameId(), participationDTO.getPlayerId())) {
            throw new IllegalArgumentException("Player with ID " + participationDTO.getPlayerId() + " has already participated in the game with ID " + participationDTO.getGameId() + ".");
        }

        Participation participation = new Participation(participationDTO.getGameId(), participationDTO.getPlayerId(), participationDTO.getScore(), participationDTO.isWin());
        externalApiService.updatePlayerStats(participationDTO.getPlayerId(), participation.getScore());
        return participationDAO.save(participation);
    }

    @Override
    public Participation findParticipationById(Long id) {
        return participationDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participation with ID " + id + " not found."));
    }

    @Override
    public void deleteParticipation(Long id) {
        participationDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participation with ID " + id + " not found."));
        participationDAO.deleteById(id);
    }
}
