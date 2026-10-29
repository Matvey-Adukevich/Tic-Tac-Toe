package org.example.web.mapper;

import org.example.domain.model.GameType;
import org.example.web.model.GameTypeDTO;

public class GameTypeWebMapper {
    public GameType toDomain(GameTypeDTO gameTypeDTO){
        if (gameTypeDTO == null) return null;
        return GameType.valueOf(gameTypeDTO.name());
    }

    public GameTypeDTO toWeb(GameType gameType){
        if (gameType == null) return null;
        return GameTypeDTO.valueOf(gameType.name());
    }
}
