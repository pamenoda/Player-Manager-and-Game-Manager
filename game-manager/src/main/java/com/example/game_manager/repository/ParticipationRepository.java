package com.example.game_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.game_manager.entity.Participation;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
