package org.example.web.model;

import java.util.UUID;

public record GameResponseDTO(
    UUID uuid,
    StateDTO state,
    UUID uuidCurrentPlayer,
    UUID uuidWinner,
    UUID uuidPlayer1,
    UUID uuidPlayer2,
    String symbolPlayer1,
    String symbolPlayer2,
    GridDTO grid,
    GameTypeDTO gameType
){}

