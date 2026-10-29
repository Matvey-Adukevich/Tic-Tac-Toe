package org.example.web.model;

import java.util.UUID;

public record GameRequestDTO(
        UUID uuid,
        GridDTO grid,
        GameTypeDTO gameType,
        UUID userUuid
) {
    public GameRequestDTO(UUID uuid, GridDTO grid, GameTypeDTO gameType) {
        this(uuid, grid, gameType, null);
    }
}
