package com.example.player_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FriendRequestDTO {

    public FriendRequestDTO(long playerId, long friendId) {
        this.playerId = playerId;
        this.friendId = friendId;
    }

    @NonNull
    private Long playerId;

    @NonNull
    private Long friendId;

}
