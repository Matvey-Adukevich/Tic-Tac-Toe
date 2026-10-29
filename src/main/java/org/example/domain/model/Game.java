package org.example.domain.model;

import java.util.UUID;

public class Game {
    private UUID uuid;
    private State state;
    private UUID uuidCurrentPlayer;
    private UUID uuidWinner;
    private UUID uuidPlayer1;
    private UUID uuidPlayer2;
    private Grid grid;
    private GameType gameType;


    public Game(UUID uuid, State state, UUID uuidCurrentPlayer, UUID uuidWinner,
                UUID uuidPlayer1, UUID uuidPlayer2, Grid grid, GameType gameType) {
        this.uuid = uuid;
        this.state = state;
        this.uuidCurrentPlayer = uuidCurrentPlayer;
        this.uuidWinner = uuidWinner;
        this.uuidPlayer1 = uuidPlayer1;
        this.uuidPlayer2 = uuidPlayer2;
        this.grid = grid;
        this.gameType = gameType;
    }

    public Game(UUID uuid) {
        this.uuid = uuid;
        this.state = State.WAITING;
        this.uuidCurrentPlayer = null;
        this.uuidWinner = null;
        this.uuidPlayer1 = null;
        this.uuidPlayer2 = null;
        this.grid = new Grid();
        this.gameType = null;
    }

//    public Game(){
//        this.uuid = UUID.randomUUID();
//        this.grid = new Grid();
//    }
//    public Game(UUID uuid){
//        this.uuid = uuid;
//        this.grid = new Grid();
//    }
//    Game(UUID uuid, Grid grid){
//        this.uuid = uuid;
//        this.grid = grid;
//    }
    public Game(Game other){
        this.grid = new Grid();
        this.uuid = other.uuid;
        this.state = other.state;
        this.uuidCurrentPlayer = other.uuidCurrentPlayer;
        this.uuidWinner = other.uuidWinner;
        this.uuidPlayer1 = other.uuidPlayer1;
        this.uuidPlayer2 = other.uuidPlayer2;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                this.grid.setCell(i, j, other.grid.getTypeCell(i, j));
            }
        }
        this.gameType = other.gameType;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public UUID getUuidCurrentPlayer() {
        return uuidCurrentPlayer;
    }

    public void setUuidCurrentPlayer(UUID uuidCurrentPlayer) {
        this.uuidCurrentPlayer = uuidCurrentPlayer;
    }

    public UUID getUuidWinner() {
        return uuidWinner;
    }

    public void setUuidWinner(UUID uuidWinner) {
        this.uuidWinner = uuidWinner;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuidPlayer1() {
        return uuidPlayer1;
    }

    public void setUuidPlayer1(UUID uuidPlayer1) {
        this.uuidPlayer1 = uuidPlayer1;
    }

    public UUID getUuidPlayer2() {
        return uuidPlayer2;
    }

    public void setUuidPlayer2(UUID uuidPlayer2) {
        this.uuidPlayer2 = uuidPlayer2;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
