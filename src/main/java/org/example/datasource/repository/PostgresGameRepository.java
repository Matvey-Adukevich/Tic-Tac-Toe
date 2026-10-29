package org.example.datasource.repository;

import org.example.datasource.mapper.GameEntityMapper;
import org.example.datasource.mapper.StateEntityMapper;
import org.example.datasource.model.GameEntity;
import org.example.datasource.model.StateEntity;
import org.example.domain.model.Game;
import org.example.domain.model.State;

import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Repository
public class PostgresGameRepository implements Repository{
    private final JpaGameRepository jpaGameRepository;
    private final GameEntityMapper gameEntityMapper;
    private final StateEntityMapper stateEntityMapper;

    public PostgresGameRepository(JpaGameRepository jpaGameRepository,GameEntityMapper gameEntityMapper,
                                  StateEntityMapper stateEntityMapper){
        this.jpaGameRepository = jpaGameRepository;
        this.gameEntityMapper = gameEntityMapper;
        this.stateEntityMapper = stateEntityMapper;
    }

    @Override
    public void saveGame(Game game) {
        GameEntity gameEntity = gameEntityMapper.toEntity(game);
        jpaGameRepository.save(gameEntity);
    }

    @Override
    public Game getGameByUUID(UUID uuid) {
        GameEntity gameEntity = jpaGameRepository.findById(uuid).orElse(null);
        if(gameEntity == null){
            System.out.println("Game not in db. Incorrect uuid.");
            throw new IllegalArgumentException("Not correct uuid: "+uuid);
        }
        Game game = gameEntityMapper.toDomain(gameEntity);
        return game;
    }

    @Override
    public List<Game> getGamesByState(State state) {
        StateEntity stateEntity = stateEntityMapper.toEntity(state);
        List<GameEntity> games = jpaGameRepository.findByStateEntity(stateEntity);
        return games.stream()
                .map(gameEntityMapper::toDomain)
                .toList();
    }
}
