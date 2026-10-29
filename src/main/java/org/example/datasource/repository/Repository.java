package org.example.datasource.repository;

import org.example.domain.model.Game;
import org.example.domain.model.State;

import java.util.List;
import java.util.UUID;

public interface Repository {
    void saveGame(Game game);
    Game getGameByUUID(UUID uuid);
    List<Game> getGamesByState(State state);
}
