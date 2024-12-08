package com.example.game_manager.service;

import com.example.game_manager.dto.CreateParticipationDTO;
import com.example.game_manager.entity.Participation;

public interface IParticipationService {
    Participation saveParticipation(CreateParticipationDTO participation);

    Participation findParticipationById(Long id);

    void deleteParticipation(Long id);
}
