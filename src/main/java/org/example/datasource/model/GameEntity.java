package org.example.datasource.model;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name="games")
public class GameEntity {
    @Id
    @Column(name="uuid")
    private UUID uuid;

    @Column(name="stateEntity")
    private StateEntity stateEntity;

    @Column(name="uuidCurrentPlayer")
    private UUID uuidCurrentPlayer;

    @Column(name="uuidWinner")
    private UUID uuidWinner;

    @Column(name="uuidPlayer1")
    private UUID uuidPlayer1;

    @Column(name="uuidPlayer2")
    private UUID uuidPlayer2;

    @Embedded
    private GridEntity grid;

    @Column(name="gameTypeEntity")
    private GameTypeEntity gameTypeEntity;

    public GameEntity() {
    }

    public GameEntity(UUID uuid, StateEntity stateEntity, UUID uuidCurrentPlayer, UUID uuidWinner, UUID uuidPlayer1, UUID uuidPlayer2, GridEntity grid, GameTypeEntity gameTypeEntity) {
        this.uuid = uuid;
        this.stateEntity = stateEntity;
        this.uuidCurrentPlayer = uuidCurrentPlayer;
        this.uuidWinner = uuidWinner;
        this.uuidPlayer1 = uuidPlayer1;
        this.uuidPlayer2 = uuidPlayer2;
        this.grid = grid;
        this.gameTypeEntity = gameTypeEntity;
    }

    public void setStateEntity(StateEntity stateEntity) {
        this.stateEntity = stateEntity;
    }

    public void setUuidCurrentPlayer(UUID uuidCurrentPlayer) {
        this.uuidCurrentPlayer = uuidCurrentPlayer;
    }

    public void setUuidWinner(UUID uuidWinner) {
        this.uuidWinner = uuidWinner;
    }

    public GridEntity getGrid() {
        return grid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public StateEntity getStateEntity() {
        return stateEntity;
    }

    public UUID getUuidCurrentPlayer() {
        return uuidCurrentPlayer;
    }

    public UUID getUuidWinner() {
        return uuidWinner;
    }

    public UUID getUuidPlayer1() {
        return uuidPlayer1;
    }

    public UUID getUuidPlayer2() {
        return uuidPlayer2;
    }

    public GameTypeEntity getGameTypeEntity() {
        return gameTypeEntity;
    }
}
