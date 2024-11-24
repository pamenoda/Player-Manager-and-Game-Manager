package com.example.player_manager.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class FriendRequestDTO {

    @NonNull
    private Long playerId;

    @NonNull
    private Long friendId;

}
