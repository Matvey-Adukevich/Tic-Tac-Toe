package org.example.domain.service;

import org.example.datasource.repository.Repository;
import org.example.domain.model.Game;
import org.example.domain.model.GameType;
import org.example.domain.model.Grid;
import org.example.domain.model.State;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceImpl implements Service {
    private final Repository repository;

    public ServiceImpl(Repository repository) {
        this.repository = repository;
    }

    private int miniMax(Game game, int turn, int depth, int alpha, int beta){
        if(depth > 9){
            return -10;
        }
        int winner = getWinner(game.getGrid());
        if(winner == 1){
            return -10 + depth;
        }
        if(winner == 2){
            return 10 - depth;
        }
        if(isFull(game.getGrid())){
            return 0;
        }
        List<int[]> moves = getAvailableMoves(game.getGrid());
        Game tmpGame = new Game(game);
        if(turn == 2){
            int best = Integer.MIN_VALUE;
            for(int i = 0; i < moves.size(); i++){
                tmpGame.getGrid().setCell(moves.get(i)[0], moves.get(i)[1], 2);
                int res = miniMax(tmpGame, 1, depth + 1, alpha, beta);
                tmpGame.getGrid().setCell(moves.get(i)[0], moves.get(i)[1], 0);
                best = Math.max(best, res);
                alpha = Math.max(alpha, res);
                if(beta <= alpha) break;
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for(int i = 0; i < moves.size(); i++){
                tmpGame.getGrid().setCell(moves.get(i)[0], moves.get(i)[1], 1);
                int res = miniMax(tmpGame, 2, depth + 1, alpha, beta);
                tmpGame.getGrid().setCell(moves.get(i)[0], moves.get(i)[1], 0);
                best = Math.min(best, res);
                beta = Math.min(beta, res);
                if(beta <= alpha) break;
            }
            return best;
        }
    }

    private List<int[]> getAvailableMoves(Grid grid) {
        List<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getTypeCell(i, j) == 0) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }

    private int getWinner(Grid grid) {
        for (int i = 0; i < 3; i++) {
            int row = grid.getTypeCell(i, 0);
            if (row != 0 &&
                    row == grid.getTypeCell(i, 1) &&
                    row == grid.getTypeCell(i, 2)) {
                return row;
            }
            int col = grid.getTypeCell(0, i);
            if (col != 0 &&
                    col == grid.getTypeCell(1, i) &&
                    col == grid.getTypeCell(2, i)) {
                return col;
            }
        }
        int diagonal = grid.getTypeCell(0, 0);
        if (diagonal != 0 &&
                diagonal == grid.getTypeCell(1, 1) &&
                diagonal == grid.getTypeCell(2, 2)) {
            return diagonal;
        }
        int antiDiagonal = grid.getTypeCell(0, 2);
        if (antiDiagonal != 0 &&
                antiDiagonal == grid.getTypeCell(1, 1) &&
                antiDiagonal == grid.getTypeCell(2, 0)) {
            return antiDiagonal;
        }
        return 0;
    }

    private boolean isFull(Grid grid) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getTypeCell(i, j) == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int[] nextTurn(Game game) {
        List<int[]> moves = getAvailableMoves(game.getGrid());
        if(moves.isEmpty()){
            return null;
        }
        Game tmpGame = new Game(game);
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = null;
        for(int i = 0; i < moves.size(); i++){
            tmpGame.getGrid().setCell(moves.get(i)[0], moves.get(i)[1], 2);
            int score = miniMax(tmpGame, 1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            tmpGame.getGrid().setCell(moves.get(i)[0], moves.get(i)[1], 0);
            if(score > bestScore){
                bestScore = score;
                bestMove = moves.get(i);
            }
        }
        return bestMove;
    }

    @Override
    public boolean isCorrectStateGame(Game game) {
        Game oldGame = repository.getGameByUUID(game.getUuid());
        return isCorrectStateGame(oldGame, game);
    }

    private boolean isCorrectStateGame(Game oldGame, Game newGame) {
        if (oldGame.getState() != State.TURN) {
            return false;
        }
        UUID currentPlayer = oldGame.getUuidCurrentPlayer();
        int expectedCell;
        if (currentPlayer != null && currentPlayer.equals(oldGame.getUuidPlayer1())) {
            expectedCell = 1;
        } else if (currentPlayer != null && currentPlayer.equals(oldGame.getUuidPlayer2())) {
            expectedCell = 2;
        } else {
            return false;
        }

        int changedCells = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int oldValue = oldGame.getGrid().getTypeCell(i, j);
                int newValue = newGame.getGrid().getTypeCell(i, j);
                if (oldValue == newValue) {
                    continue;
                }
                if (oldValue != 0) {
                    return false;
                }
                if (newValue != 1 && newValue != 2) {
                    return false;
                }
                if (newValue != expectedCell) {
                    return false;
                }
                changedCells++;
            }
        }
        return changedCells == 1;
    }

    private void copyGrid(Grid source, Grid target) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                target.setCell(i, j, source.getTypeCell(i, j));
            }
        }
    }

    private void win(Game game, int winner){
        game.setState(State.WIN);
        game.setUuidCurrentPlayer(null);
        if(winner == 1){
            game.setUuidWinner(game.getUuidPlayer1());
        } else {
            game.setUuidWinner(game.getUuidPlayer2());
        }
    }

    private void draw(Game game){
        game.setState(State.DRAW);
        game.setUuidCurrentPlayer(null);
        game.setUuidWinner(null);
    }

    @Override
    public boolean isEndGame(Game game) {
        int winner = getWinner(game.getGrid());
        if (winner != 0) {
            win(game, winner);
            return true;
        }
        if (isFull(game.getGrid())) {
            draw(game);
            return true;
        }
        return false;
    }

    @Override
    public void move(Game game, int[] coords){
        game.getGrid().setCell(coords[0], coords[1], 2);
    }

    @Override
    public UUID newGame(Game game){
        if(game.getGameType() == GameType.VS_COMPUTER){
            game.setState(State.TURN);
            game.setUuidCurrentPlayer(game.getUuidPlayer1());
        } else if(game.getGameType() == GameType.VS_PLAYER){
            game.setState(State.WAITING);
        }
        repository.saveGame(game);
        return game.getUuid();
    }

    @Override
    public Game play(Game game) {
        Game storedGame = repository.getGameByUUID(game.getUuid());
        if (storedGame.getGameType() == null) {
            return storedGame;
        }
        if (storedGame.getState() != State.TURN) {
            return storedGame;
        }
        if (game.getUuidCurrentPlayer() == null ||
                !game.getUuidCurrentPlayer().equals(storedGame.getUuidCurrentPlayer())) {
            return storedGame;
        }
        if (!isCorrectStateGame(storedGame, game)) {
            return storedGame;
        }

        copyGrid(game.getGrid(), storedGame.getGrid());

        if (isEndGame(storedGame)) {
            repository.saveGame(storedGame);
            return storedGame;
        }

        if (storedGame.getGameType() == GameType.VS_COMPUTER) {
            int[] computerMove = nextTurn(storedGame);
            if (computerMove != null) {
                move(storedGame, computerMove);
            }
            isEndGame(storedGame);
            repository.saveGame(storedGame);
            return storedGame;
        }

        if (storedGame.getGameType() == GameType.VS_PLAYER) {
            UUID currentPlayer = storedGame.getUuidCurrentPlayer();
            if (currentPlayer != null && currentPlayer.equals(storedGame.getUuidPlayer1())) {
                storedGame.setUuidCurrentPlayer(storedGame.getUuidPlayer2());
            } else if (currentPlayer != null && currentPlayer.equals(storedGame.getUuidPlayer2())) {
                storedGame.setUuidCurrentPlayer(storedGame.getUuidPlayer1());
            }
            repository.saveGame(storedGame);
            return storedGame;
        }

        return storedGame;
    }

    @Override
    public Game joinGame(Game game) {
        Game gameRep = repository.getGameByUUID(game.getUuid());
        if(gameRep.getState() == State.WAITING){
            gameRep.setState(State.TURN);
            gameRep.setUuidCurrentPlayer(gameRep.getUuidPlayer1());
            gameRep.setUuidPlayer2(game.getUuidPlayer2());
            repository.saveGame(gameRep);
        }
        return gameRep;
    }

    @Override
    public List<Game> getWaitingGames() {
        return repository.getGamesByState(State.WAITING);
    }

    @Override
    public Game getGameByUuid(Game game) {
        return repository.getGameByUUID(game.getUuid());
    }
}
