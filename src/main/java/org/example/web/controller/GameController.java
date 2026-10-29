package org.example.web.controller;

import org.example.domain.model.Game;
import org.example.domain.service.Service;
import org.example.web.mapper.GameWebMapper;
import org.example.web.model.GameRequestDTO;
import org.example.web.model.GameResponseDTO;
import org.example.web.model.GameTypeDTO;
import org.example.web.model.GridDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class GameController {
    private final Service service;
    private final GameWebMapper gameWebMapper;

    public GameController(Service service, GameWebMapper gameWebMapper){
        this.service = service;
        this.gameWebMapper = gameWebMapper;
    }

    @PostMapping("/game/play/{uuid}")
    public ResponseEntity<GameResponseDTO> game(@PathVariable UUID uuid, @RequestBody GameRequestDTO gameRequestDTO){
        Game game = this.gameWebMapper.toDomain(gameRequestDTO);
        game.setUuid(uuid);
        game.setUuidCurrentPlayer(gameRequestDTO.userUuid());
        Game newGame = service.play(game);
        GameResponseDTO gameResponseDTO = gameWebMapper.toWeb(newGame);
        return ResponseEntity.ok(gameResponseDTO);
    }

    @GetMapping("/game/new")
    public ResponseEntity<GameResponseDTO> createGame(@RequestParam GameTypeDTO opponent, @RequestParam UUID userUuid){
        GameRequestDTO gameRequestDTO = new GameRequestDTO(UUID.randomUUID(), new GridDTO(), opponent);
        Game game = this.gameWebMapper.toDomain(gameRequestDTO);
        game.setUuidPlayer1(userUuid);

        service.newGame(game);
        return ResponseEntity.ok(this.gameWebMapper.toWeb(game));
    }

    @PostMapping("/game/join/{uuidGame}")
    public ResponseEntity<GameResponseDTO> joinGame(@PathVariable UUID uuidGame, @RequestBody UUID uuidPlayer){
        GameRequestDTO gameRequestDTO = new GameRequestDTO(uuidGame, new GridDTO(), null);
        Game game = this.gameWebMapper.toDomain(gameRequestDTO);
        game.setUuidPlayer2(uuidPlayer);
        return ResponseEntity.ok(this.gameWebMapper.toWeb(service.joinGame(game)));
    }

    @GetMapping("/game/waiting")
    public ResponseEntity<List<GameResponseDTO>> getWaitingGames(){
        List<Game> games = service.getWaitingGames();
        return ResponseEntity.ok(games.stream().map(game -> this.gameWebMapper.toWeb(game)).toList());
    }

    @GetMapping("/game/{uuidGame}")
    public ResponseEntity<GameResponseDTO> getGame(@PathVariable UUID uuidGame){
        GameRequestDTO gameRequestDTO = new GameRequestDTO(uuidGame, new GridDTO(), null);
        Game game = this.gameWebMapper.toDomain(gameRequestDTO);
        return ResponseEntity.ok(this.gameWebMapper.toWeb(service.getGameByUuid(game)));
    }
}
