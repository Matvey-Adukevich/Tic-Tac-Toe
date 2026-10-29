package org.example.web.mapper;

import org.example.domain.model.Game;
import org.example.web.model.*;

public class GameWebMapper {

    private final GridWebMapper gridWebMapper;
    private final StateWebMapper stateWebMapper;
    private final GameTypeWebMapper gameTypeWebMapper;

    public GameWebMapper(GridWebMapper gridWebMapper, StateWebMapper stateWebMapper, GameTypeWebMapper gameTypeWebMapper){
        this.gridWebMapper = gridWebMapper;
        this.stateWebMapper = stateWebMapper;
        this.gameTypeWebMapper = gameTypeWebMapper;
    }

    public Game toDomain(GameRequestDTO gameRequestDTO) {
        Game game = new Game(gameRequestDTO.uuid());
        game.setGameType(gameTypeWebMapper.toDomain(gameRequestDTO.gameType()));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game.getGrid().setCell(i, j, gameRequestDTO.grid().getTypeCell(i, j));
            }
        }
        return game;
    }

    public GameResponseDTO toWeb(Game game) {
        GridDTO gridDTO = gridWebMapper.toWeb(game.getGrid());
        StateDTO stateDTO = stateWebMapper.toWeb(game.getState());
        GameTypeDTO gameTypeDTO = gameTypeWebMapper.toWeb(game.getGameType());
        GameResponseDTO gameResponseDTO = new GameResponseDTO(game.getUuid(), stateDTO,
                game.getUuidCurrentPlayer(), game.getUuidWinner(),
                game.getUuidPlayer1(), game.getUuidPlayer2(),
                "X", "O", gridDTO, gameTypeDTO);
        return gameResponseDTO;
    }
}
