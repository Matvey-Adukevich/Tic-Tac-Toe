package org.example.datasource.mapper;

import org.example.datasource.model.GameTypeEntity;
import org.example.domain.model.GameType;

public class GameTypeEntityMapper {
    public GameType toDomain(GameTypeEntity gameTypeEntity){
        return GameType.valueOf(gameTypeEntity.name());
    }

    public GameTypeEntity toEntity(GameType gameType){
        return GameTypeEntity.valueOf(gameType.name());
    }

}
