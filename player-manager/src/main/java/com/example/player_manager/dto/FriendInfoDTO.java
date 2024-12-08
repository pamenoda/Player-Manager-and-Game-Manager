package com.example.player_manager.dto;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendInfoDTO {

    public FriendInfoDTO(String username) {
        this.username = username;
    }


    @Nullable
    private String username;

    @Nullable
    private int level;

    @Nullable
    private int totalPoints;

}
