package org.example.datasource.mapper;

import org.example.datasource.model.GameEntity;
import org.example.datasource.model.GameTypeEntity;
import org.example.datasource.model.GridEntity;
import org.example.datasource.model.StateEntity;
import org.example.domain.model.Game;
import org.example.domain.model.GameType;
import org.example.domain.model.Grid;
import org.example.domain.model.State;

public class GameEntityMapper {
    private final StateEntityMapper stateEntityMapper;
    private final GameTypeEntityMapper gameTypeEntityMapper;

    public GameEntityMapper(StateEntityMapper stateEntityMapper, GameTypeEntityMapper gameTypeEntityMapper){
        this.stateEntityMapper = stateEntityMapper;
        this.gameTypeEntityMapper = gameTypeEntityMapper;
    }

    public Game toDomain(GameEntity gameEntity){
        State state = stateEntityMapper.toDomain(gameEntity.getStateEntity());
        GameType gameType = gameTypeEntityMapper.toDomain(gameEntity.getGameTypeEntity());
        Grid grid = new Grid();
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                grid.setCell(i, j, gameEntity.getGrid().getTypeCell(i, j));
            }
        }
        Game game = new Game(gameEntity.getUuid(), state, gameEntity.getUuidCurrentPlayer(), gameEntity.getUuidWinner(),
                gameEntity.getUuidPlayer1(), gameEntity.getUuidPlayer2(), grid, gameType);
        return game;
    }

    public GameEntity toEntity(Game game){
        StateEntity stateEntity = stateEntityMapper.toEntity(game.getState());
        GameTypeEntity gameTypeEntity = gameTypeEntityMapper.toEntity(game.getGameType());
        GridEntity gridEntity = new GridEntity();
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                gridEntity.setCell(i, j, game.getGrid().getTypeCell(i, j));
            }
        }
        GameEntity gameEntity = new GameEntity(game.getUuid(), stateEntity,
                game.getUuidCurrentPlayer(), game.getUuidWinner(), game.getUuidPlayer1(),
                game.getUuidPlayer2(), gridEntity, gameTypeEntity);
        return gameEntity;
    }
}
