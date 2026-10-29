package org.example.domain.service;

import org.example.domain.model.Game;

import java.util.List;
import java.util.UUID;

public interface Service {
    int[] nextTurn(Game game);
    boolean isCorrectStateGame(Game game);
    boolean isEndGame(Game game);
    void move(Game game, int[] coords);
    UUID newGame(Game game);
    Game play(Game game);
    Game joinGame(Game game);
    List<Game> getWaitingGames();
    Game getGameByUuid(Game game);
}
