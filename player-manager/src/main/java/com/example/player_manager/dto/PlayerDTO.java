package com.example.player_manager.dto;

import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO  {

    @Nullable
    private String username;

    @Nullable
    private int level;

    @Nullable
    private int totalPoints;

}
